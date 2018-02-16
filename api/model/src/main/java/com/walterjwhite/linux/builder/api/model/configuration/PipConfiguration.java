package com.walterjwhite.linux.builder.api.model.configuration;

public class PipConfiguration {
  protected String indexUrl;

  protected String virtualEnvPath;

  public PipConfiguration() {
    super();
  }

  public String getIndexUrl() {
    return indexUrl;
  }

  public void setIndexUrl(String indexUrl) {
    this.indexUrl = indexUrl;
  }

  public String getVirtualEnvPath() {
    return virtualEnvPath;
  }

  public void setVirtualEnvPath(String virtualEnvPath) {
    this.virtualEnvPath = virtualEnvPath;
  }
}
