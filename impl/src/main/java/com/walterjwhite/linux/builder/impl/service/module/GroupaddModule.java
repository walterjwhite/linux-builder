package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Groupadd;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.GroupaddService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import javax.inject.Inject;

@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = YamlConfigurer.class,
    configurationClass = Groupadd.class)
public class GroupaddModule extends AbstractCollectionModule<Groupadd> {
  protected final GroupaddService groupaddService;

  @Inject
  public GroupaddModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      GroupaddService groupaddService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.groupaddService = groupaddService;
  }

  @Override
  public void document() {}

  protected void doRun(final Groupadd groupadd) throws Exception {
    groupaddService.addUserToGroup(groupadd);
  }
}
