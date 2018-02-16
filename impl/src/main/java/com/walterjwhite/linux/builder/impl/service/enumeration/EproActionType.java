package com.walterjwhite.linux.builder.impl.service.enumeration;

public enum EproActionType {
  Arch,
  Build,
  Flavor,
  Mix_in,
  Profile;

  public String getCommandLineArgument() {
    return (name().toLowerCase().replace("_", "-"));
  }
}
