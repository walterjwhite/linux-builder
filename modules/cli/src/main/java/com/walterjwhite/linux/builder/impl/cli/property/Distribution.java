package com.walterjwhite.linux.builder.impl.cli.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface Distribution extends GuiceProperty {
  @DefaultValue
  com.walterjwhite.linux.builder.api.model.enumeration.Distribution Default =
      com.walterjwhite.linux.builder.api.model.enumeration.Distribution.Funtoo;
}
