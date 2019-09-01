package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString(doNotUseGetters = true)
public class StringConfiguration implements Configurable {
  protected String content;

  @Override
  public boolean isRun() {
    return (content != null && !content.isEmpty());
  }
}
