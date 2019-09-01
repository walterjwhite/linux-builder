package com.walterjwhite.linux.builder.impl.service.scm;

import com.walterjwhite.linux.builder.api.model.SCMTag;
import java.io.IOException;
import java.util.Date;

public class DefaultSCMTagPatchWriterServiceTest {

  public static void main(final String[] arguments) throws IOException {
    DefaultSCMTagPatchWriterService scmTagPatchWriterService =
        new DefaultSCMTagPatchWriterService("/tmp/linux");

    scmTagPatchWriterService.write(
        SCMTag.builder()
            .tag("master")
            .variant("router")
            .commitDate(new Date())
            .buildDate(new Date())
            .scmVersionId("1234")
            .commitMessage("abcd1234")
            .build());
  }
}
