package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public enum PackageFormat implements ConfigurableProperty {
  @DefaultValue
  Squashfs("squash");

  private final String extension;

  PackageFormat(String extension) {
    this.extension = extension;
  }

  public String getExtension() {
    return "." + extension;
  }
}
