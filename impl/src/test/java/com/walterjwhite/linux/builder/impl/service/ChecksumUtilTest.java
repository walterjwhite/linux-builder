package com.walterjwhite.linux.builder.impl.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChecksumUtilTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(ChecksumUtilTest.class);

  public static void main(final String[] arguments) throws IOException, NoSuchAlgorithmException {
    // TODO: use file checksum service
    //    LOGGER.info("checksum:" + ChecksumUtil.sha256sum("/tmp/test"));
    final String content = "a b";
    LOGGER.debug(content.split(" ")[1]);
  }
}
