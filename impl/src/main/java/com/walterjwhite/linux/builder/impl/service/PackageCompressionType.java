package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public enum PackageCompressionType implements ConfigurableProperty {
  @DefaultValue
  XZ("xz");

  private final String cliArgument;

  PackageCompressionType(String cliArgument) {
    this.cliArgument = cliArgument;
  }

  public String getCliArgument() {
    return cliArgument;
  }
}
