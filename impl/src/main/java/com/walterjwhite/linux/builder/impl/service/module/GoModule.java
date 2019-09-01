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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

@ContextualLoggable
@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = StringConfigurer.class,
    configurationClass = StringConfiguration.class)
public class GoModule extends AbstractSingleModule<StringConfiguration> {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public GoModule(
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

  protected void doRun() throws Exception {
    final String[] goPackages = configuration.getContent().split("\n");

    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine(String.join(" ", getArguments(goPackages))));
  }

  protected String[] getArguments(final String[] goPackages) {
    final List<String> arguments = new ArrayList<>();
    arguments.add("go");
    arguments.add("get");
    arguments.add("-u");

    arguments.addAll(getGoPackages(goPackages));

    return (arguments.toArray(new String[arguments.size()]));
  }

  protected List<String> getGoPackages(final String[] goPackages) {
    return Arrays.stream(goPackages)
        .filter(goPackage -> !isEmpty(goPackage) && !isComment(goPackage))
        .collect(Collectors.toList());
  }

  protected boolean isComment(final String goPackage) {
    return goPackage.startsWith("#");
  }

  protected boolean isEmpty(final String goPackage) {
    return goPackage == null || goPackage.isEmpty();
  }
}
