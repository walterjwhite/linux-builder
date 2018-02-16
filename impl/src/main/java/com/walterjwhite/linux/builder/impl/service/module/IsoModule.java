package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.download.api.model.Download;
import com.walterjwhite.download.api.service.DownloadService;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.ISOConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.CopyUtil;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.shell.api.enumeration.MountAction;
import com.walterjwhite.shell.api.enumeration.VFSType;
import com.walterjwhite.shell.api.model.MountCommand;
import com.walterjwhite.shell.api.model.MountPoint;
import com.walterjwhite.shell.api.service.MountService;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import java.io.File;
import javax.inject.Inject;

/**
 * This module works for any distribution that provides an ISO image. 1. download ISO image 2.
 * extract root FS from ISO image
 */
@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = YamlConfigurer.class,
  configurationClass = ISOConfiguration.class
)
public class IsoModule extends AbstractSingleModule<ISOConfiguration> {
  protected final DownloadService downloadService;
  protected final MountService mountService;

  @Inject
  public IsoModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      DownloadService downloadService,
      MountService mountService,
      ShellExecutionService shellExecutionService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.downloadService = downloadService;
    this.mountService = mountService;
    this.shellExecutionService = shellExecutionService;
  }

  @Override
  public void document() {
    // super.document();
    // tex.write_table(documentation_directory, 'Stage3Module AbstractConfiguration', ['Key',
    // 'Value'], [.2, .8],
    //                [['Stage3Module URI', self.stage3_uri], ['Stage3Module Checksum URI',
    // self.stage3_checksum_uri], ['Stage3Module Checksum', self.stage3_checksum]])
  }

  @Override
  public void doRun() throws Exception {
    setupRoot();
  }

  protected boolean setupRoot() throws Exception {
    // no need to re-runInChroot stage3 setup, instead, runInChroot the other configuration
    // TODO: move that into its own module?
    final File bash = new File(buildConfiguration.getRootDirectory() + "/bin/bash");
    if (bash.exists()) {
      return false;
    }

    extractRootFS(downloadISO());
    return true;
  }

  protected File downloadISO() throws Exception {
    return downloadService.download(
        new Download(configuration.getDownloadURI(), configuration.getDownloadChecksum()));
  }

  protected final ShellExecutionService shellExecutionService;

  protected void extractRootFS(final File isoFile) throws Exception {
    final File root = new File(buildConfiguration.getRootDirectory());
    if (!root.exists()) {
      root.mkdirs();
    }

    final MountPoint isoFileMountPoint =
        new MountPoint("/ISO", isoFile.getAbsolutePath(), VFSType.AUTO);
    final MountPoint targetFileMountPoint =
        new MountPoint(
            "/ISO-root-contents",
            buildConfiguration.getBuildDirectory()
                + File.separator
                + "ISO"
                + configuration.getRootFSPath(),
            VFSType.AUTO);
    try {
      doMount(isoFileMountPoint);
      doMount(targetFileMountPoint);

      CopyUtil.copy(
          new File(buildConfiguration.getBuildDirectory() + File.separator + "ISO-root-contents")
              .toPath(),
          new File(buildConfiguration.getRootDirectory()).toPath());
    } finally {
      doUnmount(targetFileMountPoint);
      doUnmount(isoFileMountPoint);
    }
  }

  protected void doMount(final MountPoint mountPoint) throws Exception {
    mountService.execute(
        new MountCommand()
            .withMountAction(MountAction.Mount)
            .withMountPoint(mountPoint)
            .withRootPath(buildConfiguration.getBuildDirectory()));
  }

  protected void doUnmount(final MountPoint mountPoint) throws Exception {
    mountService.execute(
        new MountCommand()
            .withMountAction(MountAction.Unmount)
            .withMountPoint(mountPoint)
            .withRootPath(buildConfiguration.getBuildDirectory()));
  }
}
