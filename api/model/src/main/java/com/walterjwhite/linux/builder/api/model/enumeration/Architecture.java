package com.walterjwhite.linux.builder.api.model.enumeration;

public enum Architecture {
  X86_64;

  public String getName() {
    return (name().toLowerCase().replace("_", "-"));
  }
}
