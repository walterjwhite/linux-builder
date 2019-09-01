package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class PipConfiguration {
  protected String indexUrl;

  protected String virtualEnvPath;
}
