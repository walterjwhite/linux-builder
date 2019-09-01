package com.walterjwhite.linux.builder.impl.service.packaging;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.PackagingService;
import com.walterjwhite.linux.builder.impl.service.PackageCompressionType;
import com.walterjwhite.property.impl.annotation.Property;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import javax.inject.Inject;

/** For the time-being, this is identical to Gentoo ... */
public class SquashfsPackagingService implements PackagingService {
  protected final ShellCommandBuilder shellCommandBuilder;
  protected final PackageCompressionType packageCompressionType;

  @Inject
  public SquashfsPackagingService(
      @Property(PackageCompressionType.class) PackageCompressionType packageCompressionType,
      ShellCommandBuilder shellCommandBuilder,
      ShellExecutionService shellExecutionService) {
    super();
    this.packageCompressionType = packageCompressionType;
    this.shellCommandBuilder = shellCommandBuilder;
    this.shellExecutionService = shellExecutionService;
  }

  // currently /systems/base/squashfs.ignore
  protected String getSquashfsIgnoreFile(final BuildConfiguration buildConfiguration) {
    return (buildConfiguration.getScmConfiguration().getWorkspacePath()
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
    shellExecutionService.run(
        shellCommandBuilder
            .build()
            .withCommandLine(
                "mksquashfs "
                    + buildConfiguration.getRootDirectory()
                    + " "
                    + buildConfiguration.getPackagePath()
                    + " -comp "
                    + packageCompressionType.getCliArgument()
                    + " -noappend -no-progress -ef "
                    + getSquashfsIgnoreFile(buildConfiguration)
                    + " -regex"));
  }
}
