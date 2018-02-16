package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.download.api.service.DownloadService;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.DownloadConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import javax.inject.Inject;

@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = YamlConfigurer.class,
  configurationClass = DownloadConfiguration.class
)
public class DownloadsModule extends AbstractCollectionModule<DownloadConfiguration> {
  protected final DownloadService downloadService;

  @Inject
  public DownloadsModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      DownloadService downloadService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.downloadService = downloadService;
  }

  public void document() {
    // tex.link_file(documentation_directory, 'RunModule Script', self.chroot_file, prefix=prefix)
  }

  /**
   * Download all of the files. TODO: runInChroot each download in a separate thread and wait for
   * all to finish
   */
  protected void doRun(final DownloadConfiguration downloadConfiguration) throws Exception {
    downloadService.download(downloadConfiguration);
  }
}
