package com.walterjwhite.linux.builder.impl.service.provider;

import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.linux.builder.api.service.GroupaddService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import javax.inject.Inject;
import javax.inject.Provider;

public class GroupaddServiceProvider implements Provider<GroupaddService> {
  protected final GroupaddService groupaddService;

  @Inject
  public GroupaddServiceProvider(final DistributionConfiguration distributionConfiguration) {
    super();

    groupaddService =
        ApplicationHelper.getApplicationInstance()
            .getInjector()
            .getInstance(distributionConfiguration.getImplementingGroupaddServiceClass());
  }

  @Override
  public GroupaddService get() {
    return groupaddService;
  }
}
