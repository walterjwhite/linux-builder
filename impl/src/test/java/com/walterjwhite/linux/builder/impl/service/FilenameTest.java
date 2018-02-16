package com.walterjwhite.linux.builder.impl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilenameTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(FilenameTest.class);

  public static void main(final String[] arguments) {
    String filename = "/root/3.hardware";
    String[] parts = filename.split("\\.");

    // LOGGER.info(parts);
    LOGGER.info("length:" + parts.length);
    LOGGER.info(parts[1]);
  }
}
