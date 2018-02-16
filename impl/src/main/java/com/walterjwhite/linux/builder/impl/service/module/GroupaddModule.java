package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Groupadd;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.IOUtil;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.IOException;
import java.util.Arrays;
import javax.inject.Inject;

@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = YamlConfigurer.class,
  configurationClass = Groupadd.class
)
public class GroupaddModule extends AbstractCollectionModule<Groupadd> {

  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public GroupaddModule(
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

  protected void doRun(final Groupadd groupadd) throws Exception {
    if (!GroupsModule.doesGroupExist(
        buildConfiguration.getRootDirectory(), groupadd.getGroupname())) {
      throw (new IllegalStateException("Group " + groupadd.getGroupname() + " does not exist."));
    }

    if (!isUserMemberOfGroup(
        buildConfiguration.getRootDirectory(), groupadd.getUsername(), groupadd.getGroupname())) {
      addUserToGroup(groupadd.getUsername(), groupadd.getGroupname());
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
