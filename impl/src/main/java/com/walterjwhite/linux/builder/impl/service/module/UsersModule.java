package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.User;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = YamlConfigurer.class,
  configurationClass = User.class
)
public class UsersModule extends AbstractCollectionModule<User> {
  private static final Logger LOGGER = LoggerFactory.getLogger(UsersModule.class);

  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public UsersModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      ShellCommandBuilder shellCommandBuilder,
      ShellExecutionService shellExecutionService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.shellCommandBuilder = shellCommandBuilder;
    this.shellExecutionService = shellExecutionService;
  }

  @Override
  public void document() {}

  protected final ShellExecutionService shellExecutionService;

  protected void doRun(final User user) throws Exception {
    final List<String> arguments = new ArrayList<>();

    if (!"root".equals(user.getUsername())) {
      if (isExisting(user.getUsername())) {
        return;
      }

      arguments.add("useradd");
      arguments.add("-m");

      addArgument(arguments, "-g", user.getGid());
      addArgument(arguments, "-u", user.getUid());
      addArgument(arguments, "-s", user.getShell());
      addArgument(arguments, "-G", user.getGroups());

      if (user.isSystem()) {
        arguments.add("-r");
      }

      if (user.getPassword() != null && !user.getPassword().isEmpty()) {
        // the password may contain special characters which may be interpreted by the shell we're
        // using
        addArgument(arguments, "-p", "'" + user.getPassword() + "'");
      }

      arguments.add(user.getUsername());

      shellExecutionService.run(
          shellCommandBuilder
              .buildChroot()
              .withChrootPath(buildConfiguration.getRootDirectory())
              .withCommandLine(String.join(" ", arguments.toArray(new String[arguments.size()]))));
    }
  }

  protected boolean addArgument(
      final List<String> arguments, final String flag, final Collection<String> values) {
    return (addArgument(
        arguments, flag, String.join(",", values.toArray(new String[values.size()]))));
  }

  protected boolean addArgument(final List<String> arguments, final String flag, final int value) {
    if (value > 0) {
      return (addArgument(arguments, flag, Integer.toString(value)));
    }

    return (false);
  }

  protected boolean addArgument(
      final List<String> arguments, final String flag, final String value) {
    if (value != null && !value.isEmpty()) {
      arguments.add(flag);
      arguments.add(value);

      return (true);
    }

    return (false);
  }

  protected boolean isExisting(final String username) throws IOException {
    for (final String line :
        FileUtils.readLines(
            new File(buildConfiguration.getRootDirectory() + "/etc/passwd"), "UTF-8")) {
      if (line.startsWith(username + ":")) return (true);
    }

    return (false);
  }
}
