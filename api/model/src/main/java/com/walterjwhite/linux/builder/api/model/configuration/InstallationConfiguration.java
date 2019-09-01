package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.Set;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class InstallationConfiguration implements Configurable {
  protected Set<String> hosts;

  protected String installationPath;

  protected Set<String> devices;

  @Override
  public boolean isRun() {
    return (((hosts != null && !hosts.isEmpty())
            && (installationPath != null && !installationPath.isEmpty()))
        || (devices != null && !devices.isEmpty()));
  }
}
