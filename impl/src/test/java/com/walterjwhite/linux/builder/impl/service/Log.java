package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.logging.annotation.ContextualLoggable;
import com.walterjwhite.logging.annotation.ContextualLoggableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
@AllArgsConstructor
@ContextualLoggable
public class Log {

  @ContextualLoggableField protected String data;

  public void doSomething() {
    LOGGER.info("something");
  }
}
