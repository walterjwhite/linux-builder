package com.walterjwhite.linux.builder.impl.service.module.funtoo.epro;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.enumeration.EproActionType;
import com.walterjwhite.linux.builder.impl.service.module.AbstractCollectionModule;
import com.walterjwhite.logging.annotation.ContextualLoggable;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;

@ContextualLoggable
public abstract class AbstractEproModule extends AbstractCollectionModule<StringConfiguration> {
  protected final EproActionType eproActionType;

  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  protected AbstractEproModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      EproActionType eproActionType,
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.eproActionType = eproActionType;
    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
  }

  @Override
  public void document() {}

  protected void doRun(final StringConfiguration item) throws Exception {
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine(
                "epro " + eproActionType.getCommandLineArgument() + " " + item.getContent()));
  }
}
