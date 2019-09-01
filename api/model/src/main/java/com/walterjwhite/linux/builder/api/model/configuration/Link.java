package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.Set;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class Link implements Configurable {
  protected String path;

  protected Set<String> targets;

  public boolean isRun() {
    return (path != null && !path.isEmpty() && targets != null && !targets.isEmpty());
  }
}
