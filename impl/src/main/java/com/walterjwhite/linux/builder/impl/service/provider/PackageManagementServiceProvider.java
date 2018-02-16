package com.walterjwhite.linux.builder.impl.service.provider;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.service.PackageManagementService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import javax.inject.Inject;
import javax.inject.Provider;

public class PackageManagementServiceProvider implements Provider<PackageManagementService> {
  protected final PackageManagementService packageManagementService;

  @Inject
  public PackageManagementServiceProvider(DistributionConfiguration distributionConfiguration) {
    super();
    this.packageManagementService =
        GuiceHelper.getGuiceInjector()
            .getInstance(distributionConfiguration.getImplementingPackageManagementServiceClass());
  }

  @Override
  public PackageManagementService get() {
    return packageManagementService;
  }
}
