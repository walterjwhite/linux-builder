package com.walterjwhite.linux.builder.impl.service;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class ChildLog extends Log {
  protected String data2;

  public ChildLog(String data, String data2) {
    super(data);
    this.data2 = data2;
  }
}
