package com.walterjwhite.linux.builder.impl.service.packagemanagement;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.PackageManagementService;
import com.walterjwhite.shell.api.model.CommandOutput;
import com.walterjwhite.shell.api.model.ShellCommand;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.util.*;
import javax.inject.Inject;

public class AptGetPackageManagementService implements PackageManagementService {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;
  protected final String rootDirectory;

  @Inject
  public AptGetPackageManagementService(
      final ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder,
      final BuildConfiguration buildConfiguration) {
    super();

    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
    this.rootDirectory = buildConfiguration.getRootDirectory();
  }

  @Override
  public void install(String... packageNames) throws Exception {
    final List<String> arguments = new ArrayList<>();
    arguments.add("DEBIAN_FRONTEND=noninteractive");
    arguments.add("apt-get");
    arguments.add("-o");
    arguments.add("Dpkg::Options::=\"--force-confdef\"");
    arguments.add("-o");
    arguments.add("Dpkg::Options::=\"--force-confold\"");
    arguments.add("install");
    arguments.add("-qq");

    arguments.addAll(Arrays.asList(packageNames));

    run(String.join(" ", arguments.toArray(new String[arguments.size()])));
  }

  @Override
  public void uninstall(String... packageNames) throws Exception {
    final List<String> arguments = new ArrayList<>();
    arguments.add("DEBIAN_FRONTEND=noninteractive");
    arguments.add("apt-get");
    arguments.add("purge");
    arguments.add("-qq");

    arguments.addAll(Arrays.asList(packageNames));

    run(String.join(" ", arguments.toArray(new String[arguments.size()])));
  }

  // http://www.microhowto.info/howto/perform_an_unattended_installation_of_a_debian_package.html
  @Override
  public void update() throws Exception {
    run(
        "DEBIAN_FRONTEND=noninteractive apt-get -o Dpkg::Options::=\"--force-confdef\" -o Dpkg::Options::=\"--force-confold\" install -f -qq --force-confold");
    run(
        "DEBIAN_FRONTEND=noninteractive apt-get -o Dpkg::Options::=\"--force-confdef\" -o Dpkg::Options::=\"--force-confold\" update -qq --force-confold");
    run("DEBIAN_FRONTEND=noninteractive apt-get upgrade -qq");
    run("DEBIAN_FRONTEND=noninteractive apt-get autoremove -qq");
    run("DEBIAN_FRONTEND=noninteractive apt-get autoclean -qq");
  }

  protected ShellCommand run(final String commandLineArgument) throws Exception {

    return shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(rootDirectory)
            .withCommandLine(commandLineArgument));
  }

  @Override
  public boolean isInstalled(String packageName) throws Exception {
    final ShellCommand shellCommand = run("dpkg -l " + packageName);
    for (CommandOutput commandOutput : shellCommand.getOutputs()) {
      final String outputPackageName = commandOutput.getOutput().split(" ")[1];
      if (packageName.equals(outputPackageName)) return true;
    }

    return false;
  }
}
