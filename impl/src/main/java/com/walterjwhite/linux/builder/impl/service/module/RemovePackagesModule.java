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
public class RemovePackagesModule extends AbstractSingleModule<StringConfiguration> {
  protected final PackageManagementService packageManagementService;

  @Inject
  public RemovePackagesModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      PackageManagementService packageManagementService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.packageManagementService = packageManagementService;
  }

  //  @Override
  //  public void onSetup() {
  //    packageManagementService =
  //        GuiceHelper.getGuiceApplicationInjector()
  //
  // .getInstance(distributionConfiguration.getImplementingPackageManagementServiceClass());
  //    packageManagementService.setRootDirectory(buildConfiguration.getRootDirectory());
  //  }

  /*

  packageManagementService =
        distributionConfiguration
            .getImplementingPackageManagementServiceClass()
            .getConstructor(String.class)
            .newInstance(buildConfiguration.getRootDirectory());
   */

  //    protected List<String> load() throws IOException {
  //        return IOUtil.readLines(getFilename());
  //    }

  @Override
  public void document() {}

  protected void doRun() throws Exception {
    packageManagementService.uninstall(getPackageNames());
  }

  protected String[] getPackageNames() {
    return configuration.getContent().split("\n");
  }
}
