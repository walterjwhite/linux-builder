package com.walterjwhite.linux.builder.impl.service.packagemanagement;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.PackageManagementService;
import com.walterjwhite.shell.api.model.CommandOutput;
import com.walterjwhite.shell.api.model.ShellCommand;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class SnapPackageManagementService implements PackageManagementService {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;
  protected final String rootDirectory;

  @Inject
  public SnapPackageManagementService(
      final ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder,
      final BuildConfiguration buildConfiguration) {
    super();

    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
    this.rootDirectory = buildConfiguration.getRootDirectory();
  }

  protected void setup() throws Exception {
    doRun("service snapd start");
  }

  protected void cleanup() throws Exception {
    doRun("service snapd stop");
  }

  @Override
  public void install(String... packageNames) throws Exception {
    final List<String> arguments = new ArrayList<>();
    arguments.add("snap");
    arguments.add("install");
    arguments.addAll(Arrays.asList(packageNames));

    run(String.join(" ", arguments.toArray(new String[arguments.size()])));
  }

  @Override
  public void uninstall(String... packageNames) throws Exception {
    final List<String> arguments = new ArrayList<>();
    arguments.add("snap");
    arguments.add("remove");

    arguments.addAll(Arrays.asList(packageNames));

    run(String.join(" ", arguments.toArray(new String[arguments.size()])));
  }

  @Override
  public void update() throws Exception {
    run("snap refresh");
  }

  protected void run(final String commandLineArgument) throws Exception {
    setup();
    doRun(commandLineArgument);
    cleanup();
  }

  protected ShellCommand doRun(final String commandLineArgument) throws Exception {
    return shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(rootDirectory)
            .withCommandLine(commandLineArgument));
  }

  @Override
  public boolean isInstalled(String packageName) throws Exception {
    final ShellCommand shellCommand = doRun("dpkg -l " + packageName);
    for (CommandOutput commandOutput : shellCommand.getOutputs()) {
      final String outputPackageName = commandOutput.getOutput().split(" ")[1];
      if (packageName.equals(outputPackageName)) return true;
    }

    return false;
  }
}
