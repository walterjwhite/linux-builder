package com.walterjwhite.linux.builder.api.service;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;

public interface PackagingService {
  void preparePackage(BuildConfiguration buildConfiguration) throws Exception;
}
