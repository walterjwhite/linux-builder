package com.walterjwhite.linux.builder.impl.service.provider;

import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.linux.builder.api.service.UseraddService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import javax.inject.Inject;
import javax.inject.Provider;

public class UseraddServiceProvider implements Provider<UseraddService> {
  protected final DistributionConfiguration distributionConfiguration;

  @Inject
  public UseraddServiceProvider(final DistributionConfiguration distributionConfiguration) {
    super();

    this.distributionConfiguration = distributionConfiguration;
  }

  @Override
  public UseraddService get() {
    return ApplicationHelper.getApplicationInstance()
        .getInjector()
        .getInstance(distributionConfiguration.getImplementingUseraddServiceClass());
  }
}
