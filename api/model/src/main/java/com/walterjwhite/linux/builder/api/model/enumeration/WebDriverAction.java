package com.walterjwhite.linux.builder.api.model.enumeration;

public enum WebDriverAction {
  Get(String.class),
  FindByXPath(String.class),
  Click(null),
  SaveAttachment(null);

  private final Class argumentClass;

  WebDriverAction(Class argumentClass) {
    this.argumentClass = argumentClass;
  }

  public Class getArgumentClass() {
    return argumentClass;
  }
}
