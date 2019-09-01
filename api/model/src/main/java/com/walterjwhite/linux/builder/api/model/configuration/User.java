package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class User implements Configurable {
  protected String username;

  // @Value.Auxiliary
  @EqualsAndHashCode.Exclude protected int uid;

  //  @Value.Auxiliary
  @EqualsAndHashCode.Exclude protected String gid;

  // @Value.Auxiliary
  @EqualsAndHashCode.Exclude protected boolean system;

  //  @Value.Auxiliary
  @EqualsAndHashCode.Exclude protected String shell;

  //  @Value.Auxiliary
  @EqualsAndHashCode.Exclude protected Set<String> groups = new HashSet<>();

  // @Value.Auxiliary
  @EqualsAndHashCode.Exclude protected String password;

  public boolean isRun() {
    return (username != null && !username.isEmpty());
  }
}
