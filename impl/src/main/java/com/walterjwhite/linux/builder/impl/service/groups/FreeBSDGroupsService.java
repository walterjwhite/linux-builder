package com.walterjwhite.linux.builder.impl.service.groups;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Group;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class FreeBSDGroupsService extends LinuxGroupsService {

  @Inject
  public FreeBSDGroupsService(
      BuildConfiguration buildConfiguration,
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder) {
    super(buildConfiguration, shellExecutionService, shellCommandBuilder);
  }

  @Override
  protected void doCreateGroup(Group group) throws Exception {
    final List<String> arguments = new ArrayList<>();
    arguments.add("groupadd");
    //      if (group.isSystem()) arguments.add("-r");
    if (group.getChroot() != null && !group.getChroot().isEmpty()) {
      arguments.add("-R");
      arguments.add(group.getChroot());
    }
    //      if (group.getPassword() != null && !group.getPassword().isEmpty()) {
    //        arguments.add("-p");
    //        arguments.add(group.getPassword());
    //      }
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
}
