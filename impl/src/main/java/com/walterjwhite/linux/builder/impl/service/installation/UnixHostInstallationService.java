package com.walterjwhite.linux.builder.impl.service.installation;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.InstallationConfiguration;
import com.walterjwhite.linux.builder.api.service.InstallationService;
import com.walterjwhite.linux.builder.impl.service.GetCurrentRootDeviceTimeout;
import com.walterjwhite.linux.builder.impl.service.MakeRemoteSystemUpdateDirectoryTimeout;
import com.walterjwhite.property.impl.annotation.Property;
import com.walterjwhite.shell.api.model.ShellCommand;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import com.walterjwhite.ssh.api.SFTPTransferService;
import com.walterjwhite.ssh.api.SSHCommandService;
import com.walterjwhite.ssh.api.model.SSHHost;
import com.walterjwhite.ssh.api.model.SSHUser;
import com.walterjwhite.ssh.api.model.command.SSHCommand;
import com.walterjwhite.ssh.api.model.sftp.SFTPTransfer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

/**
 * 1. uploads image to host 2. installs image onto host (determines new root device) 3. updates boot
 * loader configuration pointing to correct kernel / init 4. archives existing boot loader
 * configuration TODO: 1. configure SSH User, 2. run multiple shell commands in the same ssh shell
 * command, don't get a new session ..., 3. ensure the file transfer works the direction you expect
 */
public class UnixHostInstallationService implements InstallationService {
  protected final ShellCommandBuilder shellCommandBuilder;
  protected final SSHCommandService sshCommandService;
  protected final SFTPTransferService sftpTransferService;
  protected final ShellExecutionService shellExecutionService;
  protected final int getCurrentRootDeviceTimeout;
  protected final int makeRemoteSystemUpdateDirectoryTimeout;

  //  protected final SSHHostRepository sshHostRepository;
  //  protected final SSHUserRepository sshUserRepository;

  // TODO: support also passing the credentials for this user
  protected final String sshUsername;

  @Inject
  public UnixHostInstallationService(
      ShellCommandBuilder shellCommandBuilder,
      SSHCommandService sshCommandService,
      SFTPTransferService sftpTransferService,
      ShellExecutionService shellExecutionService,
      @Property(SSHInstallationUsername.class) String sshUsername,
      @Property(GetCurrentRootDeviceTimeout.class) int getCurrentRootDeviceTimeout,
      @Property(MakeRemoteSystemUpdateDirectoryTimeout.class)
          int makeRemoteSystemUpdateDirectoryTimeout) {
    super();
    this.shellCommandBuilder = shellCommandBuilder;
    this.sshCommandService = sshCommandService;
    this.sftpTransferService = sftpTransferService;
    this.shellExecutionService = shellExecutionService;
    this.sshUsername = sshUsername;
    this.getCurrentRootDeviceTimeout = getCurrentRootDeviceTimeout;
    this.makeRemoteSystemUpdateDirectoryTimeout = makeRemoteSystemUpdateDirectoryTimeout;
  }

  // TODO: move targetHosts && targetPath from Stage3Module configuration to a generic system
  // configuration
  @Override
  public void install(BuildConfiguration buildConfiguration) throws Exception {
    final InstallationConfiguration installationConfiguration =
        getConfiguration(buildConfiguration);

    installOnAllHosts(buildConfiguration, installationConfiguration);
  }

  protected InstallationConfiguration getConfiguration(BuildConfiguration buildConfiguration)
      throws FileNotFoundException {
    return (new Yaml()
        .loadAs(
            new BufferedInputStream(
                new FileInputStream(
                    buildConfiguration.getScmConfiguration().getWorkspacePath()
                        + File.separator
                        + "systems"
                        + File.separator
                        + buildConfiguration.getVariant()
                        + File.separator
                        + "installation")),
            InstallationConfiguration.class));
  }

  /**
   * TODO: multi-thread this.
   *
   * @throws IOException
   */
  protected void installOnAllHosts(
      final BuildConfiguration buildConfiguration,
      final InstallationConfiguration installationConfiguration)
      throws IOException {
    installationConfiguration
        .getHosts()
        .forEach(
            host ->
                installOnHost(
                    buildConfiguration.getPackagePath(),
                    host,
                    buildConfiguration.getRootDirectory()));
  }

  protected void installOnHost(String packagePath, final String host, final String rootDirectory) {
    //    // temporary hack
    //    if (packagePath == null) packagePath = "/builds/linux/router-master.squashfs";
    if (packagePath == null) throw (new IllegalArgumentException("Package path cannot be null."));

    try {
      // determine where to install the image
      final String newRootDevice = getNewRootDevice(host);

      shellExecutionService.run(
          shellCommandBuilder
              .build()
              .withCommandLine(
                  "cat "
                      + packagePath
                      + " | sudo -u "
                      + sshUsername
                      + " ssh "
                      + host
                      + " sudo dd of="
                      + newRootDevice));

      // update the boot loader configuration
      final String kernelVersion = getKernelVersion(host, rootDirectory);
      final File updatedBootloaderConfiguration =
          updateBootloaderConfiguration(
              getGrubBootloaderConfiguration(host), kernelVersion, getIndex(newRootDevice));

      final ShellCommand installShellCommand =
          shellCommandBuilder
              .build()
              .withCommandLine(
                  "sudo mv /boot/grub/grub.cfg /boot/grub/grub.cfg.$(date +\"%Y.%m.%d.%H.%M.%s.%N\") -f");
      final SSHCommand installSshCommand =
          new SSHCommand(new SSHHost(host), new SSHUser(sshUsername), installShellCommand);

      sshCommandService.execute(installSshCommand);

      SFTPTransfer sftpTransfer =
          new SFTPTransfer(
              new SSHHost(host),
              new SSHUser(sshUsername),
              updatedBootloaderConfiguration.getAbsolutePath(),
              "/tmp/system-update/grub.cfg");
      sftpTransferService.transfer(sftpTransfer);

      final ShellCommand updateBootloaderCommand =
          shellCommandBuilder
              .build()
              .withCommandLine("sudo mv /tmp/system-update/grub.cfg /boot/grub");
      final SSHCommand updateBootloaderSshCommand =
          new SSHCommand(new SSHHost(host), new SSHUser(sshUsername), updateBootloaderCommand);

      sshCommandService.execute(updateBootloaderSshCommand);
    } catch (Exception e) {
      throw new RuntimeException("Unable to install on host", e);
    }
  }

  public int getIndex(final String newRootDevice) {
    return (Integer.valueOf(
        newRootDevice.substring(newRootDevice.length() - 1), newRootDevice.length()));
  }

  protected String getNewRootDevice(final String host) throws Exception {
    final String currentRootDevice = getCurrentRootDevice(host);

    if (currentRootDevice.indexOf("2") >= 0) {
      return (currentRootDevice.replace("2", "1"));
    }
    if (currentRootDevice.indexOf("3") >= 0) {
      return (currentRootDevice.replace("3", "1"));
    }

    return (currentRootDevice.replace("1", "2"));
  }

  protected String getCurrentRootDevice(final String host) throws Exception {
    final ShellCommand shellCommand =
        shellCommandBuilder
            .build()
            .withCommandLine("sudo cat /proc/cmdline")
            .withTimeout(getCurrentRootDeviceTimeout);
    final SSHCommand sshCommand =
        new SSHCommand(new SSHHost(host), new SSHUser(sshUsername), shellCommand);

    sshCommandService.execute(sshCommand);

    for (final String line :
        sshCommand.getShellCommand().getOutputs().get(0).getOutput().split("\n")) {
      for (String argument : line.split(" ")) {
        if (argument != null && argument.indexOf("root=") == 0) {
          return (argument.split("root=")[1]);
        }
      }
    }

    throw (new IllegalStateException("Unable to get current root."));
  }

  protected File getGrubBootloaderConfiguration(final String host) throws Exception {
    final File tempFile = File.createTempFile("grub", "cfg");
    SFTPTransfer sftpTransfer =
        new SFTPTransfer(
            new SSHHost(host),
            new SSHUser(sshUsername),
            tempFile.getAbsolutePath(),
            "/boot/grub/grub.cfg");
    sftpTransfer.setUpload(false);
    sftpTransferService.transfer(sftpTransfer);
    return (tempFile);
  }

  public File updateBootloaderConfiguration(
      final File tempFile, final String kernelVersion, final int index) throws IOException {
    List<String> lines =
        IOUtils.readLines(new BufferedInputStream(new FileInputStream(tempFile)), "UTF-8");

    int startOfFirstMenuItem = -1;
    boolean start = false;
    boolean end = false;

    final List<String> output = new ArrayList<>();
    final List<String> edit = new ArrayList<>();
    int i = 0;
    for (final String line : lines) {
      final String cleaned = line.trim();

      if (cleaned.indexOf("menuentry") == 0) {
        if (startOfFirstMenuItem < 0) {
          startOfFirstMenuItem = i;
        }

        if (cleaned.indexOf("funtoo." + index) >= 0) {
          start = true;
          edit.add(cleaned);
          continue;
        }
      }

      if (start) {
        if (!end) {
          edit.add(cleaned);

          if (cleaned.indexOf("}") >= 0) {
            end = true;
          }
        } else {
          output.add(cleaned);
        }
      } else {
        output.add(cleaned);
      }

      i++;
    }

    final File updatedFile = File.createTempFile("grub", "cfg");
    FileUtils.write(
        updatedFile, updateBootloader(kernelVersion, edit, output, startOfFirstMenuItem), "UTF-8");
    return (updatedFile);
  }

  protected String updateBootloader(
      final String kernelVersion,
      final List<String> edit,
      final List<String> output,
      final int start) {
    int i = 0;

    for (String d : edit) {
      final List<String> arguments = new ArrayList<>();

      for (String a : d.split(" ")) {
        if (a.indexOf("/kernel") >= 0) {
          arguments.add("/kernel-" + kernelVersion);
        } else if (a.indexOf("/initramfs") >= 0) {
          arguments.add("/initramfs-" + kernelVersion);
        } else {
          arguments.add(a);
        }
      }

      output.add(start + i, String.join(" ", arguments));
      i++;
    }

    return (String.join("\n", output));
  }

  protected String getKernelVersion(final String host, final String rootDirectory)
      throws Exception {
    final File rootDirectoryFile = new File(rootDirectory + "/boot");
    final File kernel = rootDirectoryFile.listFiles(new KernelFilenameFilter())[0];

    // remove kernel-
    final String kernelVersion = kernel.getName().substring(7).trim();
    final File init =
        new File(
            kernel.getParentFile().getAbsolutePath()
                + File.separator
                + "initramfs-"
                + kernelVersion);
    final File systemMap =
        new File(
            kernel.getParentFile().getAbsolutePath()
                + File.separator
                + "System.map-"
                + kernelVersion);

    sshCommandService.execute(
        new SSHCommand(
            new SSHHost(host),
            new SSHUser(sshUsername),
            // TODO: this needs to be the node from the remote host
            shellCommandBuilder
                .build()
                .withCommandLine("mkdir -p /tmp/system-update")
                .withTimeout(makeRemoteSystemUpdateDirectoryTimeout)));

    sftpTransferService.transfer(
        new SFTPTransfer(
            new SSHHost(host),
            new SSHUser(sshUsername),
            kernel.getAbsolutePath(),
            "/tmp/system-update"));

    sftpTransferService.transfer(
        new SFTPTransfer(
            new SSHHost(host),
            new SSHUser(sshUsername),
            init.getAbsolutePath(),
            "/tmp/system-update"));

    sftpTransferService.transfer(
        new SFTPTransfer(
            new SSHHost(host),
            new SSHUser(sshUsername),
            systemMap.getAbsolutePath(),
            "/tmp/system-update"));

    final ShellCommand shellCommand =
        shellCommandBuilder
            .build()
            .withCommandLine(
                "sudo mv /tmp/system-update/{kernel-"
                    + kernelVersion
                    + ",initramfs-"
                    + kernelVersion
                    + ",System.map-"
                    + kernelVersion
                    + "} /boot")
            .withTimeout(5);
    final SSHCommand sshCommand =
        new SSHCommand(new SSHHost(host), new SSHUser(sshUsername), shellCommand);

    sshCommandService.execute(sshCommand);
    return (kernelVersion);
  }

  class KernelFilenameFilter implements FilenameFilter {

    @Override
    public boolean accept(File file, String s) {
      return s.startsWith("kernel");
    }
  }
}
