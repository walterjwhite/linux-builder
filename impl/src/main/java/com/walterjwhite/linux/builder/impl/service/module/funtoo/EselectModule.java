package com.walterjwhite.linux.builder.impl.service.module.funtoo;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.module.AbstractCollectionModule;
import com.walterjwhite.linux.builder.impl.service.util.configuration.ListConfigurer;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@ModuleSupports(
  distribution = DistributionConfiguration.Gentoo,
  configurer = ListConfigurer.class,
  configurationClass = StringConfiguration.class
)
public class EselectModule extends AbstractCollectionModule<StringConfiguration> {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public EselectModule(
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

  @Override
  public void doRun(StringConfiguration item) throws Exception {
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine(String.join(" ", getArguments(getArguments(item.getContent())))));
  }

  protected static List<String> getArguments(final String line) {
    final String[] arguments = line.split(" ");

    List<String> cmdArguments = new ArrayList<>();
    for (final String argument : arguments) {
      cmdArguments.add(argument);
    }

    // final List<String> l = Lists.asList(new String[]{}, line.split(" "));
    return (cmdArguments);
  }

  protected String[] getArguments(final List<String> arguments) {
    arguments.add(0, "eselect");
    arguments.add(2, "set");

    return (arguments.toArray(new String[arguments.size()]));
  }
}
