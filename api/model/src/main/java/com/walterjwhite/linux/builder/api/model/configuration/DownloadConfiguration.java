package com.walterjwhite.linux.builder.api.model.configuration;

import com.walterjwhite.download.api.model.Download;

public class DownloadConfiguration extends Download implements Configurable {
  public boolean isRun() {
    return (uri != null && !uri.isEmpty());
  }
}
