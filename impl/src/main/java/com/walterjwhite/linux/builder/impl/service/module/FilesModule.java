package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.FilelistConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.CopyUtil;
import com.walterjwhite.linux.builder.impl.service.util.configuration.FileListConfigurer;
import java.io.IOException;
import javax.inject.Inject;

@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = FileListConfigurer.class,
  configurationClass = FilelistConfiguration.class
)
public class FilesModule extends AbstractSingleModule<FilelistConfiguration> {
  @Inject
  public FilesModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration) {
    super(buildService, buildConfiguration, distributionConfiguration);
  }

  protected String[] getColumnNames() {
    return (new String[] {"Path"});
  }

  @Override
  public void document() {}

  @Override
  protected void doRun() throws IOException {
    CopyUtil.copy(buildConfiguration, filename);
  }
}
