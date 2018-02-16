package com.walterjwhite.linux.builder.impl.service.provider;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.service.DistributionBootstrappingService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import javax.inject.Inject;
import javax.inject.Provider;

public class DistributionBootstrappingServiceProvider
    implements Provider<DistributionBootstrappingService> {
  protected final DistributionConfiguration distributionConfiguration;

  @Inject
  public DistributionBootstrappingServiceProvider(
      final DistributionConfiguration distributionConfiguration) {
    super();

    this.distributionConfiguration = distributionConfiguration;
  }

  @Override
  public DistributionBootstrappingService get() {
    return GuiceHelper.getGuiceInjector()
        .getInstance(distributionConfiguration.getImplementingBootstrappingServiceClass());
  }
}
