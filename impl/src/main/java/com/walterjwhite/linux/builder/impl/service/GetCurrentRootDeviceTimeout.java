package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface GetCurrentRootDeviceTimeout extends GuiceProperty {
  @DefaultValue int Default = 5;
}
