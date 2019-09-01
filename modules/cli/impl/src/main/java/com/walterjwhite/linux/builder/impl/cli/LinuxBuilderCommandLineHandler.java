package com.walterjwhite.linux.builder.impl.cli;

import com.walterjwhite.inject.cli.property.CommandLineHandlerShutdownTimeout;
import com.walterjwhite.inject.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.DocumentationService;
import com.walterjwhite.linux.builder.api.service.InstallationService;
import com.walterjwhite.linux.builder.api.service.PackagingService;
import com.walterjwhite.linux.builder.impl.service.scm.SCMManagementService;
import com.walterjwhite.property.impl.annotation.Property;
import javax.inject.Inject;

public class LinuxBuilderCommandLineHandler extends AbstractCommandLineHandler {
  protected final SCMManagementService scmManagementService;

  protected final BuildService buildService;

  protected final DocumentationService documentationService;

  protected final PackagingService packagingService;

  protected final InstallationService installationService;

  protected final BuildConfiguration buildConfiguration;

  @Inject
  public LinuxBuilderCommandLineHandler(
      @Property(CommandLineHandlerShutdownTimeout.class) int shutdownTimeoutInSeconds,
      SCMManagementService scmManagementService,
      BuildService buildService,
      DocumentationService documentationService,
      PackagingService packagingService,
      InstallationService installationService,
      BuildConfiguration buildConfiguration) {
    super(shutdownTimeoutInSeconds);
    this.scmManagementService = scmManagementService;
    this.buildService = buildService;
    this.documentationService = documentationService;
    this.packagingService = packagingService;
    this.installationService = installationService;
    this.buildConfiguration = buildConfiguration;
  }

  @Override
  protected void doRun(String... arguments) throws Exception {
    scmManagementService.prepare();
    buildService.build();

    if (buildConfiguration.getSinglePatchName() == null) {
      documentationService.document(buildConfiguration);
      packagingService.preparePackage(buildConfiguration);
      installationService.install(buildConfiguration);
    }
  }

  //  @Override
  //  protected void onShutdown() throws Exception {
  //    // should already be shutdown at this point
  //    //buildService.onShutdown();
  //  }
}
