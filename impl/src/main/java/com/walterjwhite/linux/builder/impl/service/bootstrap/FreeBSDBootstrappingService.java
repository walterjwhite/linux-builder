package com.walterjwhite.linux.builder.impl.service.bootstrap;

import com.walterjwhite.file.api.service.DirectoryCopierService;
import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.PackageManagementService;
import com.walterjwhite.shell.api.model.ShellCommand;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;

/** For the time-being, this is identical to Gentoo ... */
public class FreeBSDBootstrappingService extends AbstractLinuxBootstrappingService {
  protected final ShellExecutionService shellExecutionService;

  protected final PackageManagementService packageManagementService;
  protected final DirectoryCopierService directoryCopierService;

  @Inject
  public FreeBSDBootstrappingService(
      ShellExecutionService shellExecutionService,
      BuildConfiguration buildConfiguration,
      PackageManagementService packageManagementService,
      DirectoryCopierService directoryCopierService) {
    super(buildConfiguration);
    this.shellExecutionService = shellExecutionService;
    this.packageManagementService = packageManagementService;
    this.directoryCopierService = directoryCopierService;
  }

  @Override
  public void doBootstrap() throws Exception {
    super.doBootstrap();

    if (doesJailAlreadyExist()) {
      shellExecutionService.run(
          new ShellCommand()
              .withCommandLine(
                  "ezjail-admin create "
                      + buildConfiguration.getVariant()
                      + " "
                      + getJailNetworkConfiguration()));
    }

    shellExecutionService.run(
        new ShellCommand()
            .withCommandLine("ezjail-admin start " + buildConfiguration.getVariant()));

    //
    // timezone, root password, /etc/resolv.conf, proxy ...

    // time zone, old approach (run script) should work, but look to migrate to this:
    // tzsetup /usr/share/zoneinfo/America/New_York
    // root password

    copyResolvConf();
  }

  protected boolean doesJailAlreadyExist() {
    final File jailPath = new File("/usr/share/jails/" + buildConfiguration.getVariant());
    return jailPath.exists();
  }

  // TODO: generate this appropriately
  protected String getJailNetworkConfiguration() {
    return "lo1|127.0.1.1,em0|192.168.1.50";
  }

  // DNS -> resolv.conf
  protected void copyResolvConf() throws IOException {
    directoryCopierService.copy(
        new File("/etc/resolv.conf").toPath(),
        new File("/usr/jails/" + buildConfiguration.getVariant() + "/etc/resolv.conf").toPath());
  }

  @Override
  public void doPreBuild(BuildPhase buildPhase) throws Exception {
    super.doPreBuild(buildPhase);
  }

  @Override
  public void doPostBuild(BuildPhase buildPhase) throws Exception {
    super.doPostBuild(buildPhase);

    // after setting up profiles, do a rebuild
    if (buildPhase.equals(BuildPhase.Setup)) {}

    // can also do ezjail-admin update -u
    // ezjail-admin archive wwwserver
    packageManagementService.update();
  }

  // finally, stop the jail
  public void doCompletion() throws Exception {
    shellExecutionService.run(
        new ShellCommand().withCommandLine("ezjail-admin stop " + buildConfiguration.getVariant()));
  }
}
