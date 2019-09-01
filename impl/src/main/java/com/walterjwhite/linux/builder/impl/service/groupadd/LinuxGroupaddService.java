package com.walterjwhite.linux.builder.impl.service.groupadd;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Groupadd;
import com.walterjwhite.linux.builder.api.service.GroupaddService;
import com.walterjwhite.linux.builder.impl.service.groups.LinuxGroupsService;
import com.walterjwhite.linux.builder.impl.service.util.IOUtil;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.IOException;
import java.util.Arrays;
import javax.inject.Inject;

public class LinuxGroupaddService implements GroupaddService {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;
  protected final BuildConfiguration buildConfiguration;

  @Inject
  public LinuxGroupaddService(
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder,
      BuildConfiguration buildConfiguration) {
    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
    this.buildConfiguration = buildConfiguration;
  }

  @Override
  public void addUserToGroup(Groupadd groupadd) {
    try {
      if (!LinuxGroupsService.doesGroupExist(
          buildConfiguration.getRootDirectory(), groupadd.getGroupName())) {
        throw (new IllegalStateException("Group " + groupadd.getGroupName() + " does not exist."));
      }
    } catch (IOException e) {
      throw new RuntimeException("Error checking if group already exists.", e);
    }

    try {
      if (!isUserMemberOfGroup(
          buildConfiguration.getRootDirectory(), groupadd.getUsername(), groupadd.getGroupName())) {
        addUserToGroup(groupadd.getUsername(), groupadd.getGroupName());
      }
    } catch (Exception e) {
      throw new RuntimeException("Error adding user to group", e);
    }
  }

  protected void addUserToGroup(final String username, final String groupname) throws Exception {
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine("usermod -a -G " + groupname + " " + username));
  }

  public static boolean isUserMemberOfGroup(
      final String rootDirectory, final String username, final String groupName)
      throws IOException {
    for (final String groupLine : IOUtil.readLines(rootDirectory + "/etc/group")) {
      if (groupLine.startsWith(groupName + ":")) {
        final String[] members = groupLine.substring(1 + groupLine.lastIndexOf(":")).split(",");
        return (Arrays.binarySearch(members, username) >= 0);
      }
    }

    return (false);
  }
}
