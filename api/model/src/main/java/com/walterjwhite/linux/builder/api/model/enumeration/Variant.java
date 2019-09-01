package com.walterjwhite.linux.builder.api.model.enumeration;

public enum Variant {
  Standard,
  X86_32bit,
  X86_64bit,
  Pure64,
  Hardened;

  public String getName() {
    return (name().toLowerCase().replace("_", "-"));
  }
}
