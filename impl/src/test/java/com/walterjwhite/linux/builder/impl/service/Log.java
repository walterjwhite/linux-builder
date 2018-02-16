package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.logging.annotation.ContextualLoggable;
import com.walterjwhite.logging.annotation.ContextualLoggableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ContextualLoggable
public class Log {
  private static final Logger LOGGER = LoggerFactory.getLogger(Log.class);

  @ContextualLoggableField protected String data;

  public Log(String data) {
    super();
    this.data = data;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public void doSomething() {
    LOGGER.info("something");
  }
}
