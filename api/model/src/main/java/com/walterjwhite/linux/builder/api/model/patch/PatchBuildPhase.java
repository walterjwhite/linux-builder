package com.walterjwhite.linux.builder.api.model.patch;

import com.walterjwhite.linux.builder.api.model.BuildPhase;
import lombok.Data;
import lombok.ToString;

/*
 * Stores all of the modules (and configurations) to run for a given patch and build phase
 */
@Data
@ToString(doNotUseGetters = true)
public class PatchBuildPhase {
  protected Patch patch;

  protected BuildPhase buildPhase;
  // protected List<Module> modules;
}
