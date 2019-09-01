package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.linux.builder.api.model.patch.Patch;
import com.walterjwhite.linux.builder.impl.service.util.DependencyManagementUtil;
import java.io.IOException;
import java.util.List;

public class DependencyManagementUtilTest {
  public static final void main(final String[] arguments) throws IOException {
    /*
    final Set<String> systemPatches = DependencyManagementUtil.getSystemPatches("/tmp/linux/", "router");

    for (final String systemPatch : systemPatches) {
        LOGGER.info("system patch:" + systemPatch);
    }
    */

    final List<Patch> orderedSystemPatches =
        DependencyManagementUtil.getOrderedSystemPatches("/tmp/linux/", "router");
    for (Patch patch : orderedSystemPatches) {
      processPatch(patch);
    }
  }

  private static void processPatch(Patch patch) {}
}
