package com.walterjwhite.linux.builder.api.model.configuration;

public class VoidConfiguration implements Configurable {

  @Override
  public boolean isRun() {
    return false;
  }
}
