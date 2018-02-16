package com.walterjwhite.linux.builder.api.model.configuration;

public class ISOConfiguration implements Configurable {
  // http://cdimage.ubuntu.com/ubuntu-gnome/releases/17.04/release/ubuntu-gnome-17.04-desktop-amd64.iso
  protected String downloadURI;
  // 2526926971cf0133d03e316bde8f882d /
  // 5224e9f5db84e8f0f09c8abbb3917d6ab0ee528ca34aecf742b67d608a17422e
  protected String downloadChecksum;

  // Ubuntu uses zlib compression, I'm using lzma / xz compression
  protected String kernelPath;
  protected String initPath;
  protected String rootFSPath;

  public ISOConfiguration(
      String downloadURI,
      String downloadChecksum,
      String kernelPath,
      String initPath,
      String rootFSPath) {
    super();
    this.downloadURI = downloadURI;
    this.downloadChecksum = downloadChecksum;
    this.kernelPath = kernelPath;
    this.initPath = initPath;
    this.rootFSPath = rootFSPath;
  }

  public ISOConfiguration() {
    super();
  }

  public String getDownloadURI() {
    return downloadURI;
  }

  public void setDownloadURI(String downloadURI) {
    this.downloadURI = downloadURI;
  }

  public String getDownloadChecksum() {
    return downloadChecksum;
  }

  public void setDownloadChecksum(String downloadChecksum) {
    this.downloadChecksum = downloadChecksum;
  }

  public String getKernelPath() {
    return kernelPath;
  }

  public void setKernelPath(String kernelPath) {
    this.kernelPath = kernelPath;
  }

  public String getInitPath() {
    return initPath;
  }

  public void setInitPath(String initPath) {
    this.initPath = initPath;
  }

  public String getRootFSPath() {
    return rootFSPath;
  }

  public void setRootFSPath(String rootFSPath) {
    this.rootFSPath = rootFSPath;
  }

  @Override
  public boolean isRun() {
    return true;
  }
}
