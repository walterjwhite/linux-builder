// package com.walterjwhite.linux.builder.impl.service.util.module;
//
// import com.walterjwhite.linux.builder.api.model.BuildPhase;
// import org.apache.commons.lang3.ArrayUtils;
//
// public class ModuleUtil {
//  private ModuleUtil() {}
//
//  public static String getFilenameExtension(final String filename) {
//    final String[] filenameParts = filename.split("\\.");
//    return (filenameParts[filenameParts.length - 1]);
//  }
//
//  // determine the build phase from a given configuration file
//  public static BuildPhase getBuildPhase(final String filename) {
//    final String[] parts = filename.split("/");
//
//    for (final BuildPhase buildPhase : BuildPhase.values()) {
//      if (ArrayUtils.indexOf(parts, buildPhase.name().toLowerCase()) >= 0) {
//        return (buildPhase);
//      }
//    }
//
//    throw new IllegalStateException("Unable to determine the build phase for:" + filename));
//  }
// }
