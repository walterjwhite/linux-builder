package com.walterjwhite.linux.builder.impl.cli;

import com.walterjwhite.google.guice.cli.property.OperatingMode;
import com.walterjwhite.google.guice.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.google.guice.property.property.DefaultValue;

public enum LinuxBuilderOperatingMode implements OperatingMode {
  @DefaultValue
  Default(LinuxBuilderCommandLineHandler.class);

  private final Class<? extends AbstractCommandLineHandler> initiatorClass;

  LinuxBuilderOperatingMode(Class<? extends AbstractCommandLineHandler> initiatorClass) {
    this.initiatorClass = initiatorClass;
  }

  @Override
  public Class<? extends AbstractCommandLineHandler> getInitiatorClass() {
    return initiatorClass;
  }
}
