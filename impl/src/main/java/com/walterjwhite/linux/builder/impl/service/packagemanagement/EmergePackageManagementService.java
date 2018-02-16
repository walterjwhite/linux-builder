package com.walterjwhite.linux.builder.impl.service.packagemanagement;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.PackageManagementService;
import com.walterjwhite.shell.api.model.ShellCommand;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.util.*;
import javax.inject.Inject;

public class EmergePackageManagementService implements PackageManagementService {
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;
  protected final String rootDirectory;

  @Inject
  public EmergePackageManagementService(
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
    doInstall("--keep-going -n", getPackagesToInstall(packageNames));
  }

  @Override
  public void uninstall(String... packageNames) throws Exception {
    doInstall("--depclean", getPackagesToUninstall(packageNames));
  }

  protected void doInstall(final String action, final Set<String> packages) throws Exception {
    if (packages == null || packages.isEmpty()) return;

    // TODO: automatically remove packages that are NOT installed
    run(
        "emerge --quiet --quiet-build y --nospinner -v --color n "
            + action
            + " "
            + String.join(" ", packages.toArray(new String[packages.size()])));
    doEnvUpdate();
  }

  protected void doEnvUpdate() throws Exception {
    run("env-update");
  }

  @Override
  public void update() throws Exception {
    run(
        "ego sync",
        "emerge --newuse -uD --quiet --quiet-build y --nospinner --color n @world",
        "emerge --quiet --quiet-build y --nospinner --color n --update --newuse --deep --with-bdeps=y @world",
        "emerge --quiet --quiet-build y --nospinner --color n --depclean",
        "emerge --quiet --quiet-build y --nospinner --color n @preserved-rebuild"
        /*"eclean-dist --deep"*/ );
    // run eclean-dist outside and combine both workstation and router world files
  }

  protected void run(final String... commandLines) throws Exception {
    for (final String commandLine : commandLines) run(commandLine);
  }

  protected ShellCommand run(final String commandLine) throws Exception {
    return shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(rootDirectory)
            .withCommandLine(commandLine));
  }

  public boolean isInstalled(String packageName) throws Exception {
    return run("equery l " + packageName).getReturnCode() == 0;
  }

  protected Set<String> getPackagesToUninstall(final String[] packageNames) throws Exception {
    return (getPackages(packageNames, true));
  }

  protected Set<String> getPackagesToInstall(final String[] packageNames) throws Exception {
    return (getPackages(packageNames, false));
  }

  protected Set<String> getPackages(final String[] packageNames, final boolean isInstalled)
      throws Exception {
    final Set<String> packages = new HashSet<>();
    for (final String packageName : packageNames) {
      if (isInstalled(packageName) == isInstalled) {
        packages.add(packageName);
      }
    }

    return (packages);
  }
}
