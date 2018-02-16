package com.walterjwhite.linux.builder.impl.service.scm;

import com.walterjwhite.linux.builder.api.model.SCMTag;
import java.io.IOException;
import java.util.Date;

public class DefaultSCMTagPatchWriterServiceTest {

  public static void main(final String[] arguments) throws IOException {
    DefaultSCMTagPatchWriterService scmTagPatchWriterService =
        new DefaultSCMTagPatchWriterService("/tmp/linux");

    scmTagPatchWriterService.write(
        new SCMTag("master", "router", new Date(), new Date(), "1234", "abcd1234"));
  }
}
