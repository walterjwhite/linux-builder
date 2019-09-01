package com.walterjwhite.linux.builder.impl.cli.property;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public interface RepositoryUri extends ConfigurableProperty {
  @DefaultValue String Default = "/projects/active/linux";
}
