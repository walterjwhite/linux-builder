package com.walterjwhite.linux.builder.impl.service;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupaddTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(GroupaddTest.class);

  public static void main(final String[] arguments) {
    final String groupLine = "cli:x:2007:jeff,fred,thomas";

    final String[] members = groupLine.substring(1 + groupLine.lastIndexOf(":")).split(",");
    LOGGER.info("members.le:" + members.length);
    LOGGER.info("members[0]:" + members[0]);

    LOGGER.info("index:" + Arrays.binarySearch(members, "jeff"));
  }
}
