package com.walterjwhite.linux.builder.impl.service.provider;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.service.RunlevelManagementService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import javax.inject.Inject;
import javax.inject.Provider;

public class RunlevelManagementServiceProvider implements Provider<RunlevelManagementService> {
  protected final RunlevelManagementService runlevelManagementService;

  @Inject
  public RunlevelManagementServiceProvider(DistributionConfiguration distributionConfiguration) {
    super();

    this.runlevelManagementService =
        GuiceHelper.getGuiceInjector()
            .getInstance(distributionConfiguration.getImplementingRunlevelManagementServiceClass());
  }

  @Override
  public RunlevelManagementService get() {
    return runlevelManagementService;
  }
}
