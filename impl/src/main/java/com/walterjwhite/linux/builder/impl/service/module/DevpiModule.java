package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.StringConfigurer;
import com.walterjwhite.logging.annotation.ContextualLoggable;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.FileNotFoundException;
import javax.inject.Inject;

@ContextualLoggable
@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = StringConfigurer.class,
  configurationClass = StringConfiguration.class
)
public class DevpiModule extends PipModule {
  @Inject
  public DevpiModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder)
      throws FileNotFoundException {
    super(
        buildService,
        buildConfiguration,
        distributionConfiguration,
        shellExecutionService,
        shellCommandBuilder);
  }

  @Override
  protected String[] getArguments() {
    return (new String[] {
      pipConfiguration.getVirtualEnvPath() + "/bin/pip",
      "install",
      "--upgrade",
      "-q",
      "-i",
      pipConfiguration.getIndexUrl()
    });
  }
}
