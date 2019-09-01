package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.Set;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class SystemUseConfiguration implements Configurable {
  protected Set<String> videoCards;

  protected Set<String> inputDevices;

  protected Set<String> saneBackends;

  protected Set<String> linguas;

  protected Set<String> use;

  protected String pythonSingleTarget;

  protected Set<String> pythonTargets;

  @Override
  public boolean isRun() {
    return ((use != null && !use.isEmpty())
        || (pythonTargets != null && !pythonTargets.isEmpty())
        || (videoCards != null && !videoCards.isEmpty())
        || (inputDevices != null && !inputDevices.isEmpty())
        || (saneBackends != null && !saneBackends.isEmpty())
        || (linguas != null && !linguas.isEmpty()
            || (pythonSingleTarget != null && !pythonSingleTarget.isEmpty())));
  }
}
