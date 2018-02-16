package com.walterjwhite.linux.builder.api.model.configuration;

public class DebootstrapConfiguration implements Configurable {
  protected String debootstrapUri;
  protected String debootstrapChecksum;
  protected String architecture;
  protected String mirrorUri;

  public String getDebootstrapUri() {
    return debootstrapUri;
  }

  public void setDebootstrapUri(String debootstrapUri) {
    this.debootstrapUri = debootstrapUri;
  }

  public String getDebootstrapChecksum() {
    return debootstrapChecksum;
  }

  public void setDebootstrapChecksum(String debootstrapChecksum) {
    this.debootstrapChecksum = debootstrapChecksum;
  }

  public String getArchitecture() {
    return architecture;
  }

  public void setArchitecture(String architecture) {
    this.architecture = architecture;
  }

  public String getMirrorUri() {
    return mirrorUri;
  }

  public void setMirrorUri(String mirrorUri) {
    this.mirrorUri = mirrorUri;
  }

  @Override
  public boolean isRun() {
    return true;
  }
}
