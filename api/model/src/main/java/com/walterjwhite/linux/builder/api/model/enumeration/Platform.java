package com.walterjwhite.linux.builder.api.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Platform {
  I686,
  X86_64;

  public String getGccHost() {
    return name().toLowerCase() + "-pc-linux-gnu";
  }
}
