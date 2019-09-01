package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.FilenameConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.FilenameConfigurer;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = FilenameConfigurer.class,
    configurationClass = FilenameConfiguration.class)
public class RunModule extends AbstractSingleModule<FilenameConfiguration> {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;
  protected File runFile;

  @Inject
  public RunModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
  }

  public void onSetup() {
    this.runFile = new File(buildConfiguration.getRootDirectory() + "/tmp/runInChroot");
  }

  public void document() {
    // tex.link_file(documentation_directory, 'RunModule Script', self.chroot_file, prefix=prefix)
  }

  /** Simply executes the script. */
  protected void doRun() throws Exception {
    // ensure the file is executable
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine("chmod +x /tmp/runInChroot"));

    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine("/tmp/runInChroot"));
  }

  @Override
  protected void prepare() throws Exception {
    FileUtils.copyFile(new File(configuration.getFilename()), runFile);
  }

  @Override
  protected void cleanup() throws Exception {
    FileUtils.forceDelete(runFile);
  }
}
