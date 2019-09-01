package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.PackageManagementService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.StringConfigurer;
import javax.inject.Inject;

@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = StringConfigurer.class,
    configurationClass = StringConfiguration.class)
public class AddPackagesModule extends AbstractSingleModule<StringConfiguration> {
  protected final PackageManagementService packageManagementService;

  //  public AddPackagesModule(
  //      BuildService buildService,
  //      final BuildConfiguration buildConfiguration,
  //      final DistributionConfiguration distributionConfiguration,
  //      final String filename,
  //      final StringConfiguration configuration,
  //      final BuildPhase buildPhase,
  //      final String patchName)
  //      throws IOException, NoSuchMethodException, IllegalAccessException,
  // InvocationTargetException,
  //          InstantiationException {
  //    super(
  //        buildService,
  //        buildConfiguration,
  //        distributionConfiguration,
  //        filename,
  //        configuration,
  //        buildPhase,
  //        patchName);
  //
  //    packageManagementService =
  //        distributionConfiguration
  //            .getImplementingPackageManagementServiceClass()
  //            .getConstructor(String.class)
  //            .newInstance(buildConfiguration.getRootDirectory());
  //  }

  @Inject
  public AddPackagesModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      PackageManagementService packageManagementService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.packageManagementService = packageManagementService;
  }

  @Override
  public void document() {}

  @Override
  protected void doRun() throws Exception {
    packageManagementService.install(getPackageNames());
  }

  protected String[] getPackageNames() {
    return configuration.getContent().split("\n");
  }
}
