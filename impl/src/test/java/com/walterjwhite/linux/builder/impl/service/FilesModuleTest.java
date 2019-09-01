package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.FilelistConfiguration;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.module.FilesModule;

public class FilesModuleTest {

  public static void main(final String[] arguments) throws Exception {
    final BuildConfiguration buildConfiguration = null;
    //        new BuildConfiguration(
    //            Distribution.Funtoo, "/tmp/linux", "master", "workstation",
    // "/projects/active/linux");
    final DistributionConfiguration distributionConfiguration = DistributionConfiguration.Funtoo;
    final BuildPhase buildPhase = BuildPhase.Build;

    final String patchName = "NetworkManager.patch";

    // final String filename =
    // "/mnt/work/linux/linux-repo/patches/networking/security/block-traffic-to-domains.patch/build/2.files";
    // final String filename =
    // "/builds/linux/linux-master/patches/system/system-logger.patch/build/3.files";;

    final FilelistConfiguration configuration = new FilelistConfiguration();
    configuration
        .getFilenames()
        .add(
            "/mnt/work/linux/linux-repo/patches/networking/general/NetworkManager.patch/variants/workstation/build/1.files");

    final FilesModule filesModule =
        GuiceHelper.getGuiceApplicationInjector().getInstance(FilesModule.class);
    // filesModule.setBuildConfiguration();
    filesModule.run();
  }
}
