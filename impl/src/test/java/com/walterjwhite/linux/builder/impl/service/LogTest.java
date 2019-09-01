package com.walterjwhite.linux.builder.impl.service;

import java.lang.reflect.Field;

public class LogTest {

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
