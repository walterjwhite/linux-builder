package com.walterjwhite.linux.builder.api.model.configuration;

import com.walterjwhite.linux.builder.api.model.enumeration.SubArchitecture;
import com.walterjwhite.linux.builder.api.model.enumeration.Variant;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class Stage3Configuration implements Configurable {
  protected Variant variant;

  // protected Build build;
  protected String build;

  protected SubArchitecture subArchitecture;

  protected String[] mirrors;
}
