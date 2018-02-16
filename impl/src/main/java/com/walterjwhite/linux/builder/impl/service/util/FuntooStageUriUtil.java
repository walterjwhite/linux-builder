package com.walterjwhite.linux.builder.impl.service.util;

import com.walterjwhite.linux.builder.api.model.configuration.Stage3Configuration;
import com.walterjwhite.linux.builder.api.model.enumeration.Variant;

public class FuntooStageUriUtil {
  private FuntooStageUriUtil() {}

  public static String getStage3ChecksumUri(final String stage3Uri) {
    return (stage3Uri + ".hash.txt");
  }

  public static String getStageUri(final Stage3Configuration stage3Configuration) {
    if (Variant.Pure64.equals(stage3Configuration.getVariant())) {
      // http://ftp.osuosl.org/pub/funtoo/funtoo-current/pure64/generic_64-pure64/2017-11-27/stage3-generic_64-pure64-funtoo-current-2017-11-27.tar.xz
      return (String.format(
          //        "http://build.funtoo.org/funtoo-%s/%s/%s/stage3-latest.tar.xz",
          "https://build.funtoo.org/funtoo-%s/%s/%s-%s/stage3-latest.tar.xz",
          stage3Configuration.getBuild().getName(),
          stage3Configuration.getVariant().getName(),
          stage3Configuration.getSubArchitecture().getName(),
          stage3Configuration.getVariant().getName()));
    }
    //    else if(Variant.Pure64.equals(stage3Configuration.getVariant())){
    //
    //    }
    //      return (String.format(
    //              //        "http://build.funtoo.org/funtoo-%s/%s/%s/stage3-latest.tar.xz",
    //              "https://build.funtoo.org/funtoo-%s/%s/%s-%s/stage3-latest.tar.xz",
    //              stage3Configuration.getBuild(),
    //              stage3Configuration.getVariant(),
    //              stage3Configuration.getArchitecture(),
    //              stage3Configuration.getSubArchitecture()));
    throw (new UnsupportedOperationException("Unsupported configuration"));
  }
}
