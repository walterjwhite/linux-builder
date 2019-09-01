package com.walterjwhite.linux.builder.api.model.configuration;

import com.walterjwhite.linux.builder.api.model.enumeration.RunlevelAction;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class Runlevel implements Configurable {
  protected String serviceName;

  protected RunlevelAction runlevelAction;

  protected String runlevel;

  public boolean isRun() {
    return (serviceName != null
        && !serviceName.isEmpty()
        && runlevelAction != null
        && runlevel != null
        && !runlevel.isEmpty());
  }
}
