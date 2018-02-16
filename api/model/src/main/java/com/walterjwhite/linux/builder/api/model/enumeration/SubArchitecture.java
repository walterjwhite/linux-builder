package com.walterjwhite.linux.builder.api.model.enumeration;

// @see https://www.funtoo.org/Subarches
public enum SubArchitecture {
  // Pure64
  Atom_64(null, null, null, Architecture.X86_64, null),
  Core2_64(null, null, null, Architecture.X86_64, null),
  Intel64_haswell(null, null, null, Architecture.X86_64, null),
  Intel64_ivybridge(null, null, null, Architecture.X86_64, null),
  Intel64_hehalem(null, null, null, Architecture.X86_64, null),
  Intel64_sandybridge(null, null, null, Architecture.X86_64, null),
  Intel64_silvermont(null, null, null, Architecture.X86_64, null),
  Intel64_westmere(null, null, null, Architecture.X86_64, null),
  Generic_64(
      "x86_64-pc-linux-gnu",
      "-mtune=generic -O2 -pipe",
      "mmx mmxext sse sse2",
      Architecture.X86_64,
      new Variant[] {Variant.Standard, Variant.Pure64, Variant.Hardened});

  //     if (architecture.name().toLowerCase().contains("x86-64bit") ||
  // architecture.name().toLowerCase().contains("generic_64")) {
  //        chost = "x86_64-pc-linux-gnu";
  //
  //        if (subArchitecture.name().toLowerCase().contains("core2_64") ||
  // subArchitecture.name().toLowerCase().contains("pure64")) {
  //            cflags = "-march=core2 -O2 -pipe";
  //            cpuFlagsX86 = "mmx mmxext sse sse2 sse3 ssse3";
  //        } else if (subArchitecture.equals("intel64-sandybridge")) {
  //            cflags = "-march=corei7-avx -O2 -pipe";
  //            cpuFlagsX86 = "aes avx mmx mmxext popcnt sse sse2 sse3 sse4_1 sse4_2 ssse3";
  //        }
  //    } else if (architecture.equals("arm-32bit")) {
  //        if (subArchitecture.equals("armv6j_hardfp")) {
  //            chost = "armv6j-hardfloat-linux-gnueabi";
  //            cflags = "-O2 -pipe -march=armv6j -mfloat-abi=hard";
  //            cpuFlagsX86 = "";
  //        } else if (subArchitecture.equals("")) {
  //            chost = "armv6j-hardfloat-linux-gnueabi";
  //            cflags = "-O2 -pipe -march=armv6j -mfpu=vfp -mfloat-abi=hard";
  //            cpuFlagsX86 = "";
  //        }
  //    } else {
  //        throw (new UnsupportedOperationException("CPU configuration is invalid"));
  //    }

  private final String chost;
  private final String cflags;
  private final String cpuFlags;

  private final Architecture architecture;

  private final Variant[] variants;

  SubArchitecture(
      String chost, String cflags, String cpuFlags, Architecture architecture, Variant[] variants) {
    this.chost = chost;
    this.cflags = cflags;
    this.cpuFlags = cpuFlags;
    this.architecture = architecture;
    this.variants = variants;
  }

  public String getChost() {
    return chost;
  }

  public String getCflags() {
    return cflags;
  }

  public String getCpuFlags() {
    return cpuFlags;
  }

  public Architecture getArchitecture() {
    return architecture;
  }

  public String getName() {
    return (name().toLowerCase());
  }
}
