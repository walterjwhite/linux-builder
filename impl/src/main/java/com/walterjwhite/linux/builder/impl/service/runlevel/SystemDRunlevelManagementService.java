package com.walterjwhite.linux.builder.impl.service.runlevel;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Runlevel;
import com.walterjwhite.linux.builder.api.service.RunlevelManagementService;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

public class SystemDRunlevelManagementService implements RunlevelManagementService {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;
  protected final String rootDirectory;

  @Inject
  public SystemDRunlevelManagementService(
      final ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder,
      final BuildConfiguration buildConfiguration) {
    super();

    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
    this.rootDirectory = buildConfiguration.getRootDirectory();
  }

  @Override
  public void add(Runlevel runlevel) throws Exception {
    // ensure the runlevel directory exists before attempting to add anything to it
    /// lib/systemd/system/multi-user.target
    final File runlevelFile = getRunlevelTargetFile(runlevel);
    if (!runlevelFile.exists()) {
      writeTarget(runlevel, runlevelFile);
    }

    /*
        shellExecutionService.run(
            shellCommandBuilder
                .buildChroot()
                .withChrootPath(rootDirectory)
                .withCommandLine(
                    "systemctl enable " + runlevel.getServiceName() + " " + runlevel.getRunlevel()));
    */
    // add to wants directory
  }

  protected File getRunlevelTargetFile(Runlevel runlevel) {
    return new File(rootDirectory + "/etc/systemd/system/" + runlevel.getRunlevel() + ".target");
  }

  protected File getRunlevelTargetWantsFile(final File runlevelTargetFile) {
    return new File(runlevelTargetFile.getAbsolutePath() + ".wants");
  }

  protected void writeTarget(Runlevel runlevel, final File targetFile) throws IOException {
    List<String> targetLines =
        Arrays.asList(
            "[Unit]",
            "Description=" + runlevel.getRunlevel() + " target",
            "Requires=multi-user.target",
            "After=multi-user.target",
            "AllowIsolate=yes");

    FileUtils.writeLines(targetFile, targetLines);
  }

  @Override
  public void remove(Runlevel runlevel) throws Exception {
    /*
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(rootDirectory)
            .withCommandLine(
                "systemctl disable " + runlevel.getServiceName() + " " + runlevel.getRunlevel()));
                */
    // remove from runlevel wants directory
  }
}
