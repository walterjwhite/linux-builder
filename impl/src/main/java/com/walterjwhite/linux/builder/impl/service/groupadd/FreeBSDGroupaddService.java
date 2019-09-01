package com.walterjwhite.linux.builder.impl.service.groupadd;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import javax.inject.Inject;

public class FreeBSDGroupaddService extends LinuxGroupaddService {

  @Inject
  public FreeBSDGroupaddService(
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder,
      BuildConfiguration buildConfiguration) {
    super(shellExecutionService, shellCommandBuilder, buildConfiguration);
  }

  protected void addUserToGroup(final String username, final String groupname) throws Exception {
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine("pw groupadd -m " + username + " " + groupname));
  }
}
