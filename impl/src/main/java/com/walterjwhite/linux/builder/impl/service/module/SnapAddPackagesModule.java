package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.packagemanagement.SnapPackageManagementService;
import com.walterjwhite.linux.builder.impl.service.util.configuration.StringConfigurer;
import javax.inject.Inject;

@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = StringConfigurer.class,
    configurationClass = StringConfiguration.class)
public class SnapAddPackagesModule extends AbstractSingleModule<StringConfiguration> {
  protected final SnapPackageManagementService snapPackageManagementService;

  @Inject
  public SnapAddPackagesModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      SnapPackageManagementService snapPackageManagementService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.snapPackageManagementService = snapPackageManagementService;
  }

  @Override
  public void document() {}

  @Override
  protected void doRun() throws Exception {
    final String[] packageNames = configuration.getContent().split("\n");
    snapPackageManagementService.install(packageNames);
  }
}
