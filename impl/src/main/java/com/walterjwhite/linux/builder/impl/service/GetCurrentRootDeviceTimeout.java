package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public interface GetCurrentRootDeviceTimeout extends ConfigurableProperty {
  @DefaultValue int Default = 5;
}
