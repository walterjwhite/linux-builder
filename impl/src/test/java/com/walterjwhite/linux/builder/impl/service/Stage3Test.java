package com.walterjwhite.linux.builder.impl.service;

public class Stage3Test {

  public static void main(final String[] arguments) throws Exception {
    System.setProperty("http.proxyHost", "localhost");
    System.setProperty("http.proxyPort", "8118");
    System.setProperty("https.proxyHost", "localhost");
    System.setProperty("https.proxyPort", "8118");

    /*
    BuildConfiguration buildConfiguration = new BuildConfiguration(Distribution.Funtoo, "/tmp/builds/linux",
            "master", "router", "/projects/active/linux");
    final String filename = null;
    final FuntooConfiguration funtooConfiguration = new FuntooConfiguration();
    funtooConfiguration.setArchitecture("pure64");//"x86-64bit");
    funtooConfiguration.setSubArchitecture("core2_64-pure64");
    funtooConfiguration.setBuild("stable-hardened");
    final BuildPhase buildPhase = BuildPhase.Setup;
    //build.funtoo.org/funtoo-stable-hardened/pure64/core2_64-pure64/2017-01-13/stage3-core2_64-pure64-funtoo-stable-hardened-2017-01-13.tar.xz.hash.txt
    final String patchName = null;
    Stage3Module stage3 = new Stage3Module(buildConfiguration, filename, funtooConfiguration, buildPhase, patchName);
    stage3.runInChroot();
    */
  }
}
