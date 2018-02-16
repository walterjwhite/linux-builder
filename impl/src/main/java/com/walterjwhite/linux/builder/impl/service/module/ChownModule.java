package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Chown;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = YamlConfigurer.class,
  configurationClass = Chown.class
)
public class ChownModule extends AbstractCollectionModule<Chown> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ChownModule.class);

  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public ChownModule(
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

  protected void doRun(final Chown chown) throws Exception {
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine(String.join(" ", getArguments(chown))));
  }

  protected String[] getArguments(final Chown chown) {
    final List<String> arguments = new ArrayList<>();
    arguments.add("chown");

    if (chown.isRecursive()) {
      arguments.add("-R");
    }

    final StringBuilder buffer = new StringBuilder();
    if (chown.getOwner() != null && !chown.getOwner().isEmpty()) {
      buffer.append(chown.getOwner());
    }
    buffer.append(":");

    if (chown.getGroup() != null && !chown.getGroup().isEmpty()) {
      buffer.append(chown.getGroup());
    }

    arguments.add(buffer.toString());
    arguments.add(chown.getPath());

    return (arguments.toArray(new String[arguments.size()]));
  }
}
