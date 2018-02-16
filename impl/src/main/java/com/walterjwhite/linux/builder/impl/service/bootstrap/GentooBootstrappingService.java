package com.walterjwhite.linux.builder.impl.service.bootstrap;

import com.google.inject.Inject;
import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.PackageManagementService;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.FileUtils;

/** For the time-being, this is identical to Gentoo ... */
public class GentooBootstrappingService extends AbstractLinuxBootstrappingService {
  protected final ShellExecutionService shellExecutionService;

  protected final PackageManagementService packageManagementService;

  @Inject
  public GentooBootstrappingService(
      ShellExecutionService shellExecutionService,
      BuildConfiguration buildConfiguration,
      PackageManagementService packageManagementService) {
    super(buildConfiguration);
    this.shellExecutionService = shellExecutionService;
    this.packageManagementService = packageManagementService;
  }

  @Override
  public void doBootstrap() throws IOException, InterruptedException, NoSuchAlgorithmException {
    super.doBootstrap();

    new File(buildConfiguration.getRootDirectory() + File.separator + "etc/portage/make.conf")
        .delete();
    FileUtils.deleteDirectory(
        new File(
            buildConfiguration.getRootDirectory()
                + File.separator
                + "etc/portage/package.keywords"));
    FileUtils.deleteDirectory(
        new File(
            buildConfiguration.getRootDirectory()
                + File.separator
                + "etc/portage/package.license"));
    FileUtils.deleteDirectory(
        new File(
            buildConfiguration.getRootDirectory() + File.separator + "etc/portage/package.mask"));
    FileUtils.deleteDirectory(
        new File(
            buildConfiguration.getRootDirectory() + File.separator + "etc/portage/package.unmask"));
    FileUtils.deleteDirectory(
        new File(
            buildConfiguration.getRootDirectory() + File.separator + "etc/portage/package.use"));

    //    FileUtils.deleteDirectory(
    //        new File(
    //            buildConfiguration.getRootDirectory() + File.separator +
    // "etc/portage/repos.conf"));

    // ensure we don't start any services whilst chrooted
    new File(buildConfiguration.getRootDirectory() + File.separator + "lib64/rc/init.d").mkdirs();
    new File(buildConfiguration.getRootDirectory() + File.separator + "lib64/rc/init.d/softlevel")
        .createNewFile();
  }

  @Override
  public void doPreBuild(BuildPhase buildPhase) throws Exception {
    super.doPreBuild(buildPhase);

    //    if (BuildPhase.Setup.equals(buildPhase)) {}
    //
    //    packageManagementService.update();
  }

  @Override
  public void doPostBuild(BuildPhase buildPhase) throws Exception {
    super.doPostBuild(buildPhase);

    // after setting up profiles, do a rebuild
    if (buildPhase.equals(BuildPhase.Setup)) {}

    packageManagementService.update();

    // remove temporary chroot block
    FileUtils.deleteDirectory(
        new File(buildConfiguration.getRootDirectory() + File.separator + "lib64/rc/init.d"));
  }
}
