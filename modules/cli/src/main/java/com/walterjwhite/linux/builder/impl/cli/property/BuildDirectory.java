package com.walterjwhite.linux.builder.impl.cli.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface BuildDirectory extends GuiceProperty {
  @DefaultValue String Default = "/builds/linux";
}
