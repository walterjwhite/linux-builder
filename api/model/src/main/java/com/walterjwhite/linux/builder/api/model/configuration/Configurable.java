package com.walterjwhite.linux.builder.api.model.configuration;

public interface Configurable {
  default boolean isRun() {
    return true;
  }
}
