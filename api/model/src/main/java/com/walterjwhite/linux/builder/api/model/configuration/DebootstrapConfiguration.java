package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class DebootstrapConfiguration implements Configurable {
  protected String debootstrapUri;

  protected String debootstrapChecksum;

  protected String architecture;

  protected String mirrorUri;
}
