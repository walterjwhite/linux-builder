package com.walterjwhite.linux.builder.api.service;

import com.walterjwhite.linux.builder.api.model.SCMTag;
import java.io.IOException;

public interface SCMTagPatchWriterService {
  void write(SCMTag scmTag) throws IOException;
}
