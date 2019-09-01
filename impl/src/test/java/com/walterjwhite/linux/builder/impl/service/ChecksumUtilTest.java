package com.walterjwhite.linux.builder.impl.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ChecksumUtilTest {
  public static void main(final String[] arguments) throws IOException, NoSuchAlgorithmException {
    // TODO: use file checksum service
    //    LOGGER.info("checksum:" + ChecksumUtil.sha256sum("/tmp/test"));
    final String content = "a b";
    log(content.split(" ")[1]);
  }

  private static void log(final String text) {}
}
