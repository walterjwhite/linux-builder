package com.walterjwhite.linux.builder.api.model.configuration;

import com.walterjwhite.shell.api.model.MountPoint;

public class MountPointConfiguration extends MountPoint implements Configurable {
  public boolean isRun() {
    return (mountPoint != null
        && !mountPoint.isEmpty()
        && device != null
        && !device.isEmpty()
        && vfsType != null);
  }
}
