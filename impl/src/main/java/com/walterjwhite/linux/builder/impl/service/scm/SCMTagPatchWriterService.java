package com.walterjwhite.linux.builder.impl.service.scm;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.scm.api.model.SCMTag;
import java.io.IOException;

public interface SCMTagPatchWriterService {
  void write(BuildConfiguration buildConfiguration, SCMTag scmTag) throws IOException;
}
