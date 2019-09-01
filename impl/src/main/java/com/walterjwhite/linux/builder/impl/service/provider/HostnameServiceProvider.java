package com.walterjwhite.linux.builder.impl.service.provider;

import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.linux.builder.api.service.HostnameService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import javax.inject.Inject;
import javax.inject.Provider;

public class HostnameServiceProvider implements Provider<HostnameService> {
  protected final HostnameService hostnameService;

  @Inject
  public HostnameServiceProvider(DistributionConfiguration distributionConfiguration) {
    super();

    this.hostnameService =
        ApplicationHelper.getApplicationInstance()
            .getInjector()
            .getInstance(distributionConfiguration.getImplementingHostnameServiceClass());
  }

  @Override
  public HostnameService get() {
    return hostnameService;
  }
}
