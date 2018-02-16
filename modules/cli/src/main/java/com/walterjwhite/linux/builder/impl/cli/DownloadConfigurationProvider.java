package com.walterjwhite.linux.builder.impl.cli;

import com.walterjwhite.download.impl.DownloadConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import javax.inject.Inject;
import javax.inject.Provider;

public class DownloadConfigurationProvider implements Provider<DownloadConfiguration> {
  protected final DownloadConfiguration downloadConfiguration;

  @Inject
  public DownloadConfigurationProvider(BuildConfiguration buildConfiguration) {
    downloadConfiguration = new DownloadConfiguration();
    downloadConfiguration.setDownloadPath(buildConfiguration.getBuildDirectory() + "/downloads");
  }

  @Override
  public DownloadConfiguration get() {
    return downloadConfiguration;
  }
}
