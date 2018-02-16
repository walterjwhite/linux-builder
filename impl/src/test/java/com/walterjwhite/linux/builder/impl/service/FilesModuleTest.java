package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.FilelistConfiguration;
import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.module.FilesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesModuleTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(FilesModuleTest.class);

  public static void main(final String[] arguments) throws Exception {
    final BuildConfiguration buildConfiguration =
        new BuildConfiguration(
            Distribution.Funtoo, "/tmp/linux", "master", "workstation", "/projects/active/linux");
    final DistributionConfiguration distributionConfiguration = DistributionConfiguration.Funtoo;
    final BuildPhase buildPhase = BuildPhase.Build;

    final String patchName = "NetworkManager.patch";

    // final String filename =
    // "/mnt/work/linux/linux-repo/patches/networking/security/block-traffic-to-domains.patch/build/2.files";
    // final String filename =
    // "/builds/linux/linux-master/patches/system/system-logger.patch/build/3.files";
    final String filename =
        "/mnt/work/linux/linux-repo/patches/networking/general/NetworkManager.patch/variants/workstation/build/1.files";

    final FilelistConfiguration configuration = new FilelistConfiguration();

    final FilesModule filesModule = GuiceHelper.getGuiceInjector().getInstance(FilesModule.class);
    // filesModule.setBuildConfiguration();
    filesModule.run();
  }
}
