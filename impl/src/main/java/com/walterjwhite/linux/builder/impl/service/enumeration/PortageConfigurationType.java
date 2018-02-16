package com.walterjwhite.linux.builder.impl.service.enumeration;

public enum PortageConfigurationType {
  Keywords,
  License,
  Mask,
  Unmask,
  Use;

  public String getPortageName() {
    return ("package." + name().toLowerCase());
  }
}
