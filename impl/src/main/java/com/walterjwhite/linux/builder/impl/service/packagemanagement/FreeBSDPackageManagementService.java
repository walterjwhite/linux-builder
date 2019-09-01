package com.walterjwhite.linux.builder.impl.service.packagemanagement;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.PackageManagementService;
import com.walterjwhite.shell.api.model.ShellCommand;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class FreeBSDPackageManagementService implements PackageManagementService {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;
  protected final String rootDirectory;

  @Inject
  public FreeBSDPackageManagementService(
      final ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder,
      final BuildConfiguration buildConfiguration) {
    super();

    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
    this.rootDirectory = buildConfiguration.getRootDirectory();
  }

  @Override
  public void install(String... packageNames) throws Exception {
    final List<String> arguments = new ArrayList<>();
    arguments.add("pkg");
    arguments.add("install");
    arguments.add("-y");

    arguments.addAll(Arrays.asList(packageNames));

    run(String.join(" ", arguments.toArray(new String[arguments.size()])));
  }

  @Override
  public void uninstall(String... packageNames) throws Exception {
    final List<String> arguments = new ArrayList<>();
    arguments.add("pkg");
    arguments.add("delete");
    arguments.add("-y");

    arguments.addAll(Arrays.asList(packageNames));

    run(String.join(" ", arguments.toArray(new String[arguments.size()])));
  }

  @Override
  public void update() throws Exception {
    run("pkg upgrade -y");
    run("freebsd-update --not-running-from-cronfetch");
    run("freebsd-update --not-running-from-cron install");
  }

  protected ShellCommand run(final String commandLineArgument) throws Exception {

    return shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(rootDirectory)
            .withCommandLine(commandLineArgument));
  }

  @Override
  public boolean isInstalled(String packageName) throws Exception {
    final ShellCommand shellCommand = run("pkg info -e " + packageName);
    return shellCommand.getReturnCode() == 0;
  }
}
