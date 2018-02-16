package com.walterjwhite.linux.builder.impl.service;

import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.walterjwhite.compression.modules.CompressionModule;
import com.walterjwhite.datastore.GoogleGuicePersistModule;
import com.walterjwhite.datastore.criteria.CriteriaBuilderModule;
import com.walterjwhite.encryption.impl.EncryptionModule;
import com.walterjwhite.file.impl.service.DefaultFileStorageModule;
import com.walterjwhite.file.providers.local.service.FileStorageModule;
import com.walterjwhite.google.guice.GuavaEventBusModule;
import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.google.guice.property.test.GuiceTestModule;
import com.walterjwhite.google.guice.property.test.PropertyValuePair;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import com.walterjwhite.linux.builder.api.service.InstallationService;
import com.walterjwhite.linux.builder.impl.service.installation.UnixHostInstallationService;
import com.walterjwhite.shell.api.property.NodeId;
import com.walterjwhite.shell.impl.ShellModule;
import com.walterjwhite.ssh.impl.SSHModule;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnixHostInstallationServiceTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(UnixHostInstallationService.class);

  protected Injector injector;

  @Before
  public void onBefore() throws Exception {
    GuiceHelper.addModules(
        new SSHModule(),
        new ShellModule(),
        new LinuxBuilderModule(),
        new GoogleGuicePersistModule(),
        new EncryptionModule(),
        new DefaultFileStorageModule(),
        new CompressionModule(),
        new CriteriaBuilderModule(),
        new FileStorageModule(),
        new GuavaEventBusModule(),
        new JpaPersistModule("defaultJPAUnit"),
        new GuiceTestModule(new PropertyValuePair(NodeId.class, "unit-test")));
    GuiceHelper.setup();
    injector = GuiceHelper.getGuiceInjector();
  }

  @After
  public void onAfter() {
    GuiceHelper.stop();
  }

  @Test
  public void testInstallation() throws Exception {
    InstallationService installationService = injector.getInstance(InstallationService.class);

    final BuildConfiguration buildConfiguration =
        new BuildConfiguration(
            Distribution.Funtoo, "/builds/linux", "master", "router", "/projects/active/linux");
    buildConfiguration.setLocalWorkspace(buildConfiguration.getBuildDirectory() + "/linux-master");
    buildConfiguration.setPackagePath(
        buildConfiguration.getBuildDirectory()
            + File.separator
            + buildConfiguration.getVariant()
            + ".squashfs");

    installationService.install(buildConfiguration);

    // unixHostInstallationService.updateBootloaderConfiguration(new
    // File("/tmp/grub985597396497661275cfg"), "kernel-genkernel-x86_64-4.9.8-hardened", 2);

    // LOGGER.info("index:" + unixHostInstallationService.getIndex("/dev/160.1/root-2"));
  }
}
