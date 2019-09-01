package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.PipConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.model.patch.Patch;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.StringConfigurer;
import com.walterjwhite.logging.annotation.ContextualLoggable;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.inject.Inject;
import org.apache.commons.lang3.ArrayUtils;
import org.yaml.snakeyaml.Yaml;

@ContextualLoggable
@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = StringConfigurer.class,
    configurationClass = StringConfiguration.class)
public class PipModule extends AbstractSingleModule<StringConfiguration> {
  protected final PipConfiguration pipConfiguration;
  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public PipModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder)
      throws FileNotFoundException {
    super(buildService, buildConfiguration, distributionConfiguration);

    this.shellExecutionService = shellExecutionService;

    pipConfiguration =
        new Yaml()
            .loadAs(
                new BufferedInputStream(
                    new FileInputStream(
                        buildConfiguration.getScmConfiguration().getWorkspacePath()
                            + File.separator
                            + "systems/base/pip.patch/configuration.yaml")),
                PipConfiguration.class);
    this.shellCommandBuilder = shellCommandBuilder;
  }

  @Override
  public void document() {}

  protected void doRun() throws Exception {
    if (!isSetup()) {
      setupPip();
    }

    final String[] pipPackageNames = configuration.getContent().split("\n");
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine(String.join(" ", ArrayUtils.addAll(getArguments(), pipPackageNames))));
  }

  protected String[] getArguments() {
    return (new String[] {
      pipConfiguration.getVirtualEnvPath() + "/bin/pip", "install", "--upgrade", "-q"
    });
  }

  protected boolean isSetup() {
    final File pipExecutable =
        new File(
            buildConfiguration.getRootDirectory()
                + File.separator
                + pipConfiguration.getVirtualEnvPath()
                + "/bin/pip");
    return (pipExecutable.exists());
  }

  protected void setupPip() throws Exception {
    buildService.runPatch(
        new Patch(
            "system-pip",
            buildConfiguration.getScmConfiguration().getWorkspacePath()
                + File.separator
                + "systems/base/pip.patch",
            false),
        BuildPhase.Setup);
  }

  /*

  protected boolean isInstalled(final String pipPackageName) throws IOException, InterruptedException {
      final CommandOutput commandError = ExecutionUtil.runInChroot(buildConfiguration.getRootDirectory(), new String[]{"pip", "show", pipPackageName, "-q"});
      return (commandError.getExitStatus() <= 1);
  }
  */
}
