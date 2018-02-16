package com.walterjwhite.linux.builder.impl.service.util.module;

import com.walterjwhite.linux.builder.api.model.BuildPhase;
import java.io.File;
import java.io.FilenameFilter;
import org.apache.commons.lang3.ArrayUtils;

public class BuildPhaseFilenameFilter implements FilenameFilter {
  protected final BuildPhase buildPhase;

  public BuildPhaseFilenameFilter(BuildPhase buildPhase) {
    super();
    this.buildPhase = buildPhase;
  }

  public BuildPhaseFilenameFilter() {
    super();
    buildPhase = null;
  }

  @Override
  public boolean accept(File file, String s) {
    if (buildPhase == null) {
      return (ArrayUtils.contains(BuildPhase.values(), s));
    }

    return (s.equals(buildPhase.name()));
  }
}
