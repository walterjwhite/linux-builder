package com.walterjwhite.linux.builder.api.model.enumeration;

public enum Variant {
  Standard,
  Pure64,
  Hardened;

  public String getName() {
    return (name().toLowerCase());
  }
}
