package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public interface GoPath extends ConfigurableProperty {
  @DefaultValue String Default = "/usr/local";
  String ENVIRONMENT_KEY = "GOPATH";
}
