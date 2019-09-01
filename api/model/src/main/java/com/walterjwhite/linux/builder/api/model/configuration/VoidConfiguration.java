package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class VoidConfiguration implements Configurable {

  @Override
  public boolean isRun() {
    return false;
  }
}
