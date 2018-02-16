package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Group;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.IOUtil;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = YamlConfigurer.class,
  configurationClass = Group.class
)
public class GroupsModule extends AbstractCollectionModule<Group> {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public GroupsModule(
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

  protected void doRun(final Group group) throws Exception {
    if (!doesGroupExist(buildConfiguration.getRootDirectory(), group.getGroupname())) {
      final List<String> arguments = new ArrayList<>();
      arguments.add("groupadd");
      if (group.isSystem()) arguments.add("-r");
      if (group.getChroot() != null && !group.getChroot().isEmpty()) {
        arguments.add("-R");
        arguments.add(group.getChroot());
      }
      if (group.getPassword() != null && !group.getPassword().isEmpty()) {
        arguments.add("-p");
        arguments.add(group.getPassword());
      }
      if (group.getGid() > 0) {
        arguments.add("-g");
        arguments.add(Integer.toString(group.getGid()));
      }

      arguments.add(group.getGroupname());

      shellExecutionService.run(
          shellCommandBuilder
              .buildChroot()
              .withChrootPath(buildConfiguration.getRootDirectory())
              .withCommandLine(String.join(" ", arguments.toArray(new String[arguments.size()]))));
    }
  }

  public static boolean doesGroupExist(final String rootDirectory, final String groupName)
      throws IOException {
    for (final String groupLine : IOUtil.readLines(rootDirectory + "/etc/group")) {
      if (groupLine.startsWith(groupName + ":")) return (true);
    }

    return (false);
  }
}
