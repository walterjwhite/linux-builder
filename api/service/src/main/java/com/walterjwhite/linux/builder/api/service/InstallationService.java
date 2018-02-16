package com.walterjwhite.linux.builder.api.service;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;

public interface InstallationService {
  void install(BuildConfiguration buildConfiguration) throws Exception;
}
