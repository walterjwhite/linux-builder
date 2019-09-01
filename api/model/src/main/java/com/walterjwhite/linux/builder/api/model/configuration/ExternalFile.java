package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class ExternalFile implements Configurable {
  protected String source;

  protected String target;

  public boolean isRun() {
    return (source != null && !source.isEmpty() && target != null && !target.isEmpty());
  }
}
