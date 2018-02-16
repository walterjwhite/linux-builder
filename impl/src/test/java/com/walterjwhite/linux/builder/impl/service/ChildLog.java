package com.walterjwhite.linux.builder.impl.service;

public class ChildLog extends Log {
  protected String data2;

  public ChildLog(String data, String data2) {
    super(data);
    this.data2 = data2;
  }

  public String getData2() {
    return data2;
  }

  public void setData2(String data2) {
    this.data2 = data2;
  }
}
