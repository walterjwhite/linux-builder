package com.walterjwhite.linux.builder.impl.service;

public class CPUConfiguration {
  protected final String chost;
  protected final String cflags;
  protected final String cpuFlagsX86;
  protected final String makeOpts;

  public CPUConfiguration(String chost, String cflags, String cpuFlagsX86, String makeOpts) {
    super();
    this.chost = chost;
    this.cflags = cflags;
    this.cpuFlagsX86 = cpuFlagsX86;
    this.makeOpts = makeOpts;
  }

  public String getChost() {
    return chost;
  }

  public String getCflags() {
    return cflags;
  }

  public String getCpuFlagsX86() {
    return cpuFlagsX86;
  }

  public String getMakeOpts() {
    return makeOpts;
  }
}
