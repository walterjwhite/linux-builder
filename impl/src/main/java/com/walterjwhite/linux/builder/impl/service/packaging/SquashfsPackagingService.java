package com.walterjwhite.linux.builder.impl.service.packaging;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.PackagingService;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import javax.inject.Inject;

/** For the time-being, this is identical to Gentoo ... */
public class SquashfsPackagingService implements PackagingService {
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public SquashfsPackagingService(
      ShellCommandBuilder shellCommandBuilder, ShellExecutionService shellExecutionService) {
    super();
    this.shellCommandBuilder = shellCommandBuilder;
    this.shellExecutionService = shellExecutionService;
  }

  protected String getSquashfsFile(final BuildConfiguration buildConfiguration) {
    final String path =
        buildConfiguration.getBuildDirectory()
            + File.separator
            + buildConfiguration.getVariant()
            + "-"
            + buildConfiguration.getTag()
            + ".squashfs";
    buildConfiguration.setPackagePath(path);

    return (path);
  }

  // currently /systems/base/squashfs.ignore
  protected String getSquashfsIgnoreFile(final BuildConfiguration buildConfiguration) {
    return (buildConfiguration.getLocalWorkspace()
        + File.separator
        + "systems"
        + File.separator
        + "base"
        + File.separator
        + "squashfs.ignore");
  }

  protected final ShellExecutionService shellExecutionService;

  // TODO: configure the compression type, block size, etc.
  @Override
  public void preparePackage(BuildConfiguration buildConfiguration) throws Exception {
    // final Properties properties = new Properties();
    // properties.getProperty("SQUASHFS_COMPRESSION_TYPE")
    final String compressionType = "xz";

    shellExecutionService.run(
        shellCommandBuilder
            .build()
            .withCommandLine(
                "mksquashfs "
                    + buildConfiguration.getRootDirectory()
                    + " "
                    + getSquashfsFile(buildConfiguration)
                    + " -comp "
                    + compressionType
                    + " -noappend -no-progress -ef "
                    + getSquashfsIgnoreFile(buildConfiguration)
                    + " -regex"));
  }
}
