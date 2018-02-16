package com.walterjwhite.linux.builder.impl.service.bootstrap;

import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.DistributionBootstrappingService;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public abstract class AbstractLinuxBootstrappingService
    implements DistributionBootstrappingService {
  protected final BuildConfiguration buildConfiguration;

  protected AbstractLinuxBootstrappingService(BuildConfiguration buildConfiguration) {
    super();
    this.buildConfiguration = buildConfiguration;
  }

  @Override
  public void doBootstrap() throws IOException, InterruptedException, NoSuchAlgorithmException {
    // clear out /etc/fstab
    new File(buildConfiguration.getRootDirectory() + File.separator + "etc/fstab").delete();
  }

  @Override
  public void doPreBuild(BuildPhase buildPhase) throws Exception {}

  @Override
  public void doPostBuild(BuildPhase buildPhase) throws Exception {}
}
