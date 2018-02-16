package com.walterjwhite.linux.builder.impl.service.bootstrap;

import com.google.inject.Inject;
import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.PackageManagementService;
import com.walterjwhite.shell.api.service.ShellExecutionService;

public class DebianBootstrappingService extends AbstractLinuxBootstrappingService {
  protected final ShellExecutionService shellExecutionService;

  protected final PackageManagementService packageManagementService;

  @Inject
  public DebianBootstrappingService(
      ShellExecutionService shellExecutionService,
      BuildConfiguration buildConfiguration,
      PackageManagementService packageManagementService) {
    super(buildConfiguration);
    this.shellExecutionService = shellExecutionService;
    this.packageManagementService = packageManagementService;
  }

  @Override
  public void doPostBuild(BuildPhase buildPhase) throws Exception {
    super.doPostBuild(buildPhase);
    packageManagementService.update();
  }
}
