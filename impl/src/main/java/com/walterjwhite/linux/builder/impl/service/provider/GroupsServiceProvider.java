package com.walterjwhite.linux.builder.impl.service.provider;

import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.linux.builder.api.service.GroupsService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import javax.inject.Inject;
import javax.inject.Provider;

public class GroupsServiceProvider implements Provider<GroupsService> {
  protected final GroupsService groupsService;

  @Inject
  public GroupsServiceProvider(final DistributionConfiguration distributionConfiguration) {
    super();

    this.groupsService =
        ApplicationHelper.getApplicationInstance()
            .getInjector()
            .getInstance(distributionConfiguration.getImplementingGroupsServiceClass());
  }

  @Override
  public GroupsService get() {
    return groupsService;
  }
}
