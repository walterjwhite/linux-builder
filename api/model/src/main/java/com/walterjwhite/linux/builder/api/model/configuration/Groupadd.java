package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class Groupadd implements Configurable {
  protected String groupName;

  protected String username;

  public boolean isRun() {
    return (groupName != null && !groupName.isEmpty() && username != null && !username.isEmpty());
  }
}
