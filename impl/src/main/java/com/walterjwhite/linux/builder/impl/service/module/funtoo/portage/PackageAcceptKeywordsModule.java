package com.walterjwhite.linux.builder.impl.service.module.funtoo.portage;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.enumeration.PortageConfigurationType;
import com.walterjwhite.linux.builder.impl.service.util.configuration.StringConfigurer;
import javax.inject.Inject;

@ModuleSupports(
  distribution = DistributionConfiguration.Gentoo,
  configurer = StringConfigurer.class,
  configurationClass = StringConfiguration.class
)
public class PackageAcceptKeywordsModule extends AbstractPortagePackageModule {
  @Inject
  public PackageAcceptKeywordsModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration) {
    super(
        buildService,
        buildConfiguration,
        distributionConfiguration,
        PortageConfigurationType.Keywords);
  }
}
