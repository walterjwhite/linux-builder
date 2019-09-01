package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString(doNotUseGetters = true)
public class FilenameConfiguration implements Configurable {
  protected String filename;

  @Override
  public boolean isRun() {
    return (filename != null && !filename.isEmpty());
  }
}
