package com.walterjwhite.linux.builder.impl.service.documentation;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.DocumentationService;

public class TexDocumentationService implements DocumentationService {
  @Override
  public void document(BuildConfiguration buildConfiguration) throws Exception {
    // call the wrapper to generate the documentation
    // and install it
  }
}
