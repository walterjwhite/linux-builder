package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Chmod;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = YamlConfigurer.class,
    configurationClass = Chmod.class)
public class ChmodModule extends AbstractCollectionModule<Chmod> {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public ChmodModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
  }

  @Override
  public void document() {}

  protected void doRun(final Chmod chmod) throws Exception {
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine(String.join(" ", getArguments(chmod))));
  }

  protected String[] getArguments(final Chmod chmod) {
    final List<String> arguments = new ArrayList<>();
    arguments.add("chmod");

    if (chmod.isRecursive()) {
      arguments.add("-R");
    }

    arguments.add(chmod.getMode());
    arguments.add(chmod.getPath());

    return (arguments.toArray(new String[arguments.size()]));
  }
}
