package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.encryption.service.DigestService;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.packagemanagement.AptGetPackageManagementService;
import com.walterjwhite.linux.builder.impl.service.util.configuration.StringConfigurer;
import com.walterjwhite.logging.annotation.ContextualLoggable;
import com.walterjwhite.shell.api.service.MountService;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import javax.inject.Inject;

/**
 * This module works for any distribution that provides an ISO image. 1. download ISO image 2.
 * extract root FS from ISO image
 */
@ContextualLoggable
@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = StringConfigurer.class,
    configurationClass = StringConfiguration.class)
public class PpaModule extends AbstractSingleModule<StringConfiguration> {
  protected final ShellCommandBuilder shellCommandBuilder;
  protected final DigestService digestService;
  protected final MountService mountService;
  protected final AptGetPackageManagementService aptGetPackageManagementService;
  protected final ShellExecutionService shellExecutionService;

  @Inject
  public PpaModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      ShellCommandBuilder shellCommandBuilder,
      DigestService digestService,
      MountService mountService,
      ShellExecutionService shellExecutionService,
      AptGetPackageManagementService aptGetPackageManagementService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.shellCommandBuilder = shellCommandBuilder;
    this.digestService = digestService;
    this.mountService = mountService;
    this.aptGetPackageManagementService = aptGetPackageManagementService;
    this.shellExecutionService = shellExecutionService;
  }

  @Override
  public void document() {}

  @Override
  public void doRun() throws Exception {
    addRepository();
    aptGetPackageManagementService.update();
  }

  protected void addRepository() throws Exception {
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine(
                "add-apt-repository " + String.join(" ", configuration.getContent().split("\n"))));
  }
}
