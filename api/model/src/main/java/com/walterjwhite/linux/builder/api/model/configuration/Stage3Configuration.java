package com.walterjwhite.linux.builder.api.model.configuration;

import com.walterjwhite.linux.builder.api.model.enumeration.Build;
import com.walterjwhite.linux.builder.api.model.enumeration.SubArchitecture;
import com.walterjwhite.linux.builder.api.model.enumeration.Variant;

public class Stage3Configuration implements Configurable {
  protected Variant variant;

  protected Build build;

  protected SubArchitecture subArchitecture;

  protected String[] mirrors;

  public Stage3Configuration() {
    super();
  }

  public Variant getVariant() {
    return variant;
  }

  public void setVariant(Variant variant) {
    this.variant = variant;
  }

  public Build getBuild() {
    return build;
  }

  public void setBuild(Build build) {
    this.build = build;
  }

  public SubArchitecture getSubArchitecture() {
    return subArchitecture;
  }

  public void setSubArchitecture(SubArchitecture subArchitecture) {
    this.subArchitecture = subArchitecture;
  }

  public String[] getMirrors() {
    return mirrors;
  }

  public void setMirrors(String[] mirrors) {
    this.mirrors = mirrors;
  }

  @Override
  public boolean isRun() {
    return (true);
  }
}
