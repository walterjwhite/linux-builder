package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.User;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.UseraddService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import javax.inject.Inject;

@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = YamlConfigurer.class,
    configurationClass = User.class)
public class UsersModule extends AbstractCollectionModule<User> {
  protected final UseraddService useraddService;

  @Inject
  public UsersModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      UseraddService useraddService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.useraddService = useraddService;
  }

  @Override
  protected void doRun(User user) throws Exception {
    useraddService.create(user);
  }

  @Override
  public void document() {}
}
