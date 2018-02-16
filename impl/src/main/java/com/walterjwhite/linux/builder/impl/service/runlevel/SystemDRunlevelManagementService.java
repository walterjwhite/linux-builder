package com.walterjwhite.linux.builder.impl.service.runlevel;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Runlevel;
import com.walterjwhite.linux.builder.api.service.RunlevelManagementService;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import javax.inject.Inject;

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
    final File runlevelDirectory =
        new File(rootDirectory + "/etc/runlevels/" + runlevel.getRunlevel());
    if (!runlevelDirectory.exists()) {
      runlevelDirectory.mkdirs();
    }

    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(rootDirectory)
            .withCommandLine(
                "systemctl enable " + runlevel.getServiceName() + " " + runlevel.getRunlevel()));
  }

  @Override
  public void remove(Runlevel runlevel) throws Exception {
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(rootDirectory)
            .withCommandLine(
                "systemctl disable " + runlevel.getServiceName() + " " + runlevel.getRunlevel()));
  }
}
