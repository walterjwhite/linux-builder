package com.walterjwhite.linux.builder.impl.cli.property;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public interface Distribution extends ConfigurableProperty {
  @DefaultValue
  com.walterjwhite.linux.builder.api.model.enumeration.Distribution Default =
      com.walterjwhite.linux.builder.api.model.enumeration.Distribution.Funtoo;
}
