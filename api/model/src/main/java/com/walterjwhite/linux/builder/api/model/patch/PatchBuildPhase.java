package com.walterjwhite.linux.builder.api.model.patch;

import com.walterjwhite.linux.builder.api.model.BuildPhase;

/*
 * Stores all of the modules (and configurations) to run for a given patch and build phase
 */
public class PatchBuildPhase {
  protected Patch patch;
  protected BuildPhase buildPhase;
  // protected List<Module> modules;

  public PatchBuildPhase(Patch patch, BuildPhase buildPhase) {
    super();

    this.patch = patch;
    this.buildPhase = buildPhase;

    // this.modules = new ArrayList<>();
  }
}
