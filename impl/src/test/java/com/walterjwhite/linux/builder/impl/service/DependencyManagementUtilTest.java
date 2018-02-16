package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.linux.builder.api.model.patch.Patch;
import com.walterjwhite.linux.builder.impl.service.util.DependencyManagementUtil;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependencyManagementUtilTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(DependencyManagementUtilTest.class);

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
      LOGGER.info("system patch:" + patch.getName());
    }
  }
}
