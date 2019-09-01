package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class Chown implements Configurable {
  protected String path;

  protected boolean recursive;

  protected String owner;

  protected String group;

  public boolean isRun() {
    return (path != null
        && !path.isEmpty()
        && ((owner != null && !owner.isEmpty()) || (group != null && !group.isEmpty())));
  }
}
