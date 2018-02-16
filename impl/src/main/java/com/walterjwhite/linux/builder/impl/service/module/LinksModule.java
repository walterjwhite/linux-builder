package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Link;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import javax.inject.Inject;

@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = YamlConfigurer.class,
  configurationClass = Link.class
)
public class LinksModule extends AbstractCollectionModule<Link> {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public LinksModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
  }

  public void document() {
    // tex.link_file(documentation_directory, 'RunModule Script', self.chroot_file, prefix=prefix)
  }

  /**
   * Download all of the files. TODO: runInChroot each download in a separate thread and wait for
   * all to finish
   */
  protected void doRun(final Link link) throws Exception {
    for (final String target : link.getTargets()) {
      // this will NOT work because it is not relative to the chrooted environment
      // java.nio.file.FilesModule.createSymbolicLink(source.toPath(), new
      // File(buildConfiguration.getRootDirectory() + target).toPath());
      final File targetFile =
          new File(buildConfiguration.getRootDirectory() + File.separator + target);
      final File parent = targetFile.getParentFile();
      if (!parent.exists()) {
        parent.mkdirs();
      }

      shellExecutionService.run(
          shellCommandBuilder
              .buildChroot()
              .withChrootPath(buildConfiguration.getRootDirectory())
              .withCommandLine("ln  -sf " + link.getPath() + " " + target));
    }
  }
}
