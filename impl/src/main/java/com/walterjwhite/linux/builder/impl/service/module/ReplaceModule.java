package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Replace;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import javax.inject.Inject;

@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = YamlConfigurer.class,
  configurationClass = Replace.class
)
public class ReplaceModule extends AbstractCollectionModule<Replace> {
  @Inject
  public ReplaceModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration) {
    super(buildService, buildConfiguration, distributionConfiguration);
  }

  @Override
  public void document() {}

  protected void doRun(final Replace replace) {
    // TODO: implement
  }
}
