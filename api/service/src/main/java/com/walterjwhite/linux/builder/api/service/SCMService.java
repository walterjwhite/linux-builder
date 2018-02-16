package com.walterjwhite.linux.builder.api.service;

import com.walterjwhite.linux.builder.api.model.SCMTag;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import java.io.IOException;

public interface SCMService {
  void checkout(BuildConfiguration buildConfiguration) throws Exception;

  SCMTag getSCMTag(BuildConfiguration buildConfiguration) throws IOException;
}
