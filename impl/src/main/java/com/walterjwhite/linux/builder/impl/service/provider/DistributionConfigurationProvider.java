package com.walterjwhite.linux.builder.impl.service.provider;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import javax.inject.Inject;
import javax.inject.Provider;

public class DistributionConfigurationProvider implements Provider<DistributionConfiguration> {
  protected final DistributionConfiguration distributionConfiguration;

  @Inject
  public DistributionConfigurationProvider(BuildConfiguration buildConfiguration) {
    super();
    distributionConfiguration = DistributionConfiguration.get(buildConfiguration.getDistribution());
  }

  @Override
  public DistributionConfiguration get() {
    return distributionConfiguration;
  }
}
