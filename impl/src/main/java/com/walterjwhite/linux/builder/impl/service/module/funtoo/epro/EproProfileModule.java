package com.walterjwhite.linux.builder.impl.service.module.funtoo.epro;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.enumeration.EproActionType;
import com.walterjwhite.linux.builder.impl.service.util.configuration.ListConfigurer;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import javax.inject.Inject;

@ModuleSupports(
  distribution = DistributionConfiguration.Gentoo,
  configurer = ListConfigurer.class,
  configurationClass = StringConfiguration.class
)
public class EproProfileModule extends AbstractEproModule {
  @Inject
  public EproProfileModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder) {
    super(
        buildService,
        buildConfiguration,
        distributionConfiguration,
        EproActionType.Profile,
        shellExecutionService,
        shellCommandBuilder);
  }
}
