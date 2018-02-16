package com.walterjwhite.linux.builder.api.service;

import com.walterjwhite.linux.builder.api.model.configuration.Configurable;

public interface Module<ConfigurationType extends Configurable> {
  boolean isRun();

  void document();

  void run() throws Exception;
}
