package com.walterjwhite.linux.builder.impl.service.groups;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Group;
import com.walterjwhite.linux.builder.api.service.GroupsService;
import com.walterjwhite.linux.builder.impl.service.util.IOUtil;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class LinuxGroupsService implements GroupsService {
  protected final BuildConfiguration buildConfiguration;
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public LinuxGroupsService(
      BuildConfiguration buildConfiguration,
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder) {
    this.buildConfiguration = buildConfiguration;
    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
  }

  @Override
  public void create(Group group) {
    try {
      doRun(group);
    } catch (Exception e) {
      throw new RuntimeException("Error creating group", e);
    }
  }

  protected void doRun(final Group group) throws Exception {
    if (!doesGroupExist(buildConfiguration.getRootDirectory(), group.getGroupName())) {
      doCreateGroup(group);
    }
  }

  protected void doCreateGroup(final Group group) throws Exception {
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

    arguments.add(group.getGroupName());

    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine(String.join(" ", arguments.toArray(new String[arguments.size()]))));
  }

  public static boolean doesGroupExist(final String rootDirectory, final String groupName)
      throws IOException {
    for (final String groupLine : IOUtil.readLines(rootDirectory + "/etc/group")) {
      if (groupLine.startsWith(groupName + ":")) return (true);
    }

    return (false);
  }
}
