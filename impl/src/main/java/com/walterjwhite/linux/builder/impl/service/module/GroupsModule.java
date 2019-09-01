package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Group;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.GroupsService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import javax.inject.Inject;

@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = YamlConfigurer.class,
    configurationClass = Group.class)
public class GroupsModule extends AbstractCollectionModule<Group> {
  protected final GroupsService groupsService;

  @Inject
  public GroupsModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      GroupsService groupsService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.groupsService = groupsService;
  }

  @Override
  public void document() {}

  @Override
  protected void doRun(Group group) throws Exception {
    groupsService.create(group);
  }
}
