package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class Chmod implements Configurable {
  protected String path;

  protected boolean recursive = false;

  protected String mode;

  public boolean isRun() {
    return (path != null && !path.isEmpty() && mode != null && !mode.isEmpty());
  }
}
