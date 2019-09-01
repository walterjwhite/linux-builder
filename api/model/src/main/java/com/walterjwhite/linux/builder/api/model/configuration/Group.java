package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class Group implements Configurable {
  protected String groupName;

  protected String password;

  protected int gid;

  protected boolean system;

  protected String chroot;

  public boolean isRun() {
    return !(groupName == null || groupName.isEmpty());
  }
}
