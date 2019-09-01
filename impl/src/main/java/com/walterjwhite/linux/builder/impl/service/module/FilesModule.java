package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.file.api.service.DirectoryCopierService;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.FilelistConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.FileListConfigurer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.inject.Inject;

@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = FileListConfigurer.class,
    configurationClass = FilelistConfiguration.class)
public class FilesModule extends AbstractSingleModule<FilelistConfiguration> {
  protected final DirectoryCopierService directoryCopierService;

  @Inject
  public FilesModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      DirectoryCopierService directoryCopierService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.directoryCopierService = directoryCopierService;
  }

  protected String[] getColumnNames() {
    return (new String[] {"Path"});
  }

  @Override
  public void document() {}

  @Override
  protected void doRun() throws IOException {
    directoryCopierService.copy(getSource(), getTarget());
  }

  protected Path getSource() {
    return new File(filename).toPath();
  }

  protected Path getTarget() {
    return new File(buildConfiguration.getRootDirectory()).toPath();
  }
}
