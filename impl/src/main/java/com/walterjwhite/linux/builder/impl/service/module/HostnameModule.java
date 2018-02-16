package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.HostnameService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.StringConfigurer;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = StringConfigurer.class,
  configurationClass = StringConfiguration.class
)
public class HostnameModule extends AbstractSingleModule<StringConfiguration> {
  private static final Logger LOGGER = LoggerFactory.getLogger(HostnameModule.class);

  protected final HostnameService hostnameService;

  @Inject
  public HostnameModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      HostnameService hostnameService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.hostnameService = hostnameService;
  }

  protected Class<? extends HostnameService> getHostnameServiceClass() {
    Class<? extends HostnameService> hostnameServiceClass =
        distributionConfiguration.getHostnameServiceClass();

    if (hostnameServiceClass != null) {
      return (hostnameServiceClass);
    }

    return (distributionConfiguration.getParent().getHostnameServiceClass());
  }

  @Override
  public void document() {}

  @Override
  protected void doRun() throws Exception {
    hostnameService.set(configuration.getContent());
  }
}
