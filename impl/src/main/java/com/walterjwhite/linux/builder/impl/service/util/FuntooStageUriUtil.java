package com.walterjwhite.linux.builder.impl.service.util;

import com.walterjwhite.linux.builder.api.model.configuration.Stage3Configuration;

public class FuntooStageUriUtil {
  private FuntooStageUriUtil() {}

  public static String getStage3ChecksumUri(final String stage3Uri) {
    return (stage3Uri + ".hash.txt");
  }

  public static String getStageUri(final Stage3Configuration stage3Configuration) {
    return (String.format(
        "https://build.funtoo.org/%s/%s/%s/stage3-latest.tar.xz",
        stage3Configuration.getBuild(),
        stage3Configuration.getSubArchitecture().getArchitecture().getName(),
        stage3Configuration.getSubArchitecture().getName()));

    // https://build.funtoo.org/1.3-release-std/x86-64bit/generic_64/
  }
}
