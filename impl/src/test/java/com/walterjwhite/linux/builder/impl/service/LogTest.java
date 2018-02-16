package com.walterjwhite.linux.builder.impl.service;

import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(LogTest.class);

  public static void main(final String[] arguments) throws NoSuchFieldException {
    final Log log = new Log("some value");
    log.doSomething();

    final Field f = Log.class.getDeclaredField("data");
    LOGGER.info("f:" + f);

    final ChildLog log2 = new ChildLog("a", "b");
    final Field f2 = ChildLog.class.getSuperclass().getDeclaredField("data");
    LOGGER.info("f2:" + f);
  }
}
