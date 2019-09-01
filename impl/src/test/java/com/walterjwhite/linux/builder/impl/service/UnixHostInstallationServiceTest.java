package com.walterjwhite.linux.builder.impl.service;

import com.google.inject.Injector;
import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.InstallationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnixHostInstallationServiceTest {

  protected Injector injector;

  @Before
  public void onBefore() throws Exception {
    GuiceHelper.addModules(new LinuxBuilderTestModule(getClass()));

    GuiceHelper.setup();
    injector = GuiceHelper.getGuiceApplicationInjector();
  }

  @After
  public void onAfter() {
    GuiceHelper.stop();
  }

  @Test
  public void testInstallation() throws Exception {
    InstallationService installationService = injector.getInstance(InstallationService.class);

    // TODO: use builder
    final BuildConfiguration buildConfiguration = null;
    //        new BuildConfiguration(
    //            Distribution.Funtoo, "/builds/linux", "master", "router",
    // "/projects/active/linux");
    //    buildConfiguration.setLocalWorkspace(buildConfiguration.getBuildDirectory() +
    // "/linux-master");
    //    buildConfiguration.setPackagePath(
    //        buildConfiguration.getBuildDirectory()
    //            + File.separator
    //            + buildConfiguration.getVariant()
    //            + ".squashfs");

    installationService.install(buildConfiguration);

    // unixHostInstallationService.updateBootloaderConfiguration(new
    // File("/tmp/grub985597396497661275cfg"), "kernel-genkernel-x86_64-4.9.8-hardened", 2);

    // LOGGER.info("index:" + unixHostInstallationService.getIndex("/dev/160.1/root-2"));
  }
}
