package com.walterjwhite.linux.builder.api.model.enumeration;

public enum Build {
  Current;

  public String getName() {
    return (name().toLowerCase());
  }
}
