package com.walterjwhite.linux.builder.impl.service.runlevel;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Runlevel;
import com.walterjwhite.linux.builder.api.service.RunlevelManagementService;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import javax.inject.Inject;

public class OpenRCRunlevelManagementService implements RunlevelManagementService {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  protected final String rootDirectory;

  @Inject
  public OpenRCRunlevelManagementService(
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
    final File runlevelDirectory = getRunlevelDirectory(runlevel);
    if (!runlevelDirectory.exists()) {
      runlevelDirectory.mkdirs();
    }

    if (!isEnabled(runlevel)) {
      shellExecutionService.run(
          shellCommandBuilder
              .buildChroot()
              .withChrootPath(rootDirectory)
              .withCommandLine(
                  "rc-update add " + runlevel.getServiceName() + " " + runlevel.getRunlevel()));
    }
  }

  @Override
  public void remove(Runlevel runlevel) throws Exception {
    if (isEnabled(runlevel))
      shellExecutionService.run(
          shellCommandBuilder
              .buildChroot()
              .withChrootPath(rootDirectory)
              .withCommandLine(
                  "rc-update delete " + runlevel.getServiceName() + " " + runlevel.getRunlevel()));
  }

  protected boolean isEnabled(Runlevel runlevel) {
    return getRunlevelFile(runlevel).exists();
  }

  protected File getRunlevelFile(Runlevel runlevel) {
    return new File(
        rootDirectory
            + "/etc/runlevels/"
            + runlevel.getRunlevel()
            + "/"
            + runlevel.getServiceName());
  }

  protected File getRunlevelDirectory(Runlevel runlevel) {
    return new File(rootDirectory + "/etc/runlevels/" + runlevel.getRunlevel());
  }
}
