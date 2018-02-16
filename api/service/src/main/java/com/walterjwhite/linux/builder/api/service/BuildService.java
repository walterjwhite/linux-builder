package com.walterjwhite.linux.builder.api.service;

import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.patch.Patch;

public interface BuildService {

  /**
   * Performs the actual build.
   *
   * @throws Exception
   */
  void build() throws Exception;

  void runPatch(final Patch patch, final BuildPhase buildPhase) throws Exception;

  void onShutdown() throws Exception;
}
