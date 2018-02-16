package com.walterjwhite.linux.builder.api.model.patch;

import java.util.HashSet;
import java.util.Set;

public class PatchLevel {
  protected Set<Patch> patches = new HashSet<>();

  public Set<Patch> getPatches() {
    return patches;
  }

  public void setPatches(Set<Patch> patches) {
    this.patches = patches;
  }
}
