package com.walterjwhite.linux.builder.api.service;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;

public interface DocumentationService {
  void document(BuildConfiguration buildConfiguration) throws Exception;
}
