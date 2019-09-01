package com.walterjwhite.linux.builder.api.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

// @see https://www.funtoo.org/Subarches
// TODO: continue adding ARM, AMD, and legacy Intel processors
@Getter
@AllArgsConstructor
public enum SubArchitecture {
  I686(
      Platform.I686,
      "-march=i686 -O2 -pipe -mtune=generic",
      Architecture.X86_32,
      "https://www.funtoo.org/I686",
      new Variant[] {Variant.Standard},
      null),
  // Pure64
  Atom_32(
      Platform.I686,
      "-O2 -fomit-frame-pointer -march=atom -pipe",
      Architecture.X86_32,
      "https://www.funtoo.org/Atom_32",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.MMX,
        InstructionSet.MMXEXT,
        InstructionSet.SSE,
        InstructionSet.SSE2,
        InstructionSet.SSE3,
        InstructionSet.SSSE3
      }),
  Core2_64(
      Platform.X86_64,
      "-march=core2 -O2 -pipe",
      Architecture.X86_64,
      "https://www.funtoo.org/Core2_64",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.MMX,
        InstructionSet.MMXEXT,
        InstructionSet.SSE,
        InstructionSet.SSE2,
        InstructionSet.SSE3,
        InstructionSet.SSSE3
      }),
  Atom_64(
      Platform.X86_64,
      "-O2 -fomit-frame-pointer -march=atom -pipe",
      Architecture.X86_64,
      "https://www.funtoo.org/Atom_64",
      null,
      new InstructionSet[] {
        InstructionSet.MMX, InstructionSet.SSE, InstructionSet.SSE2, InstructionSet.SSE3
      }),
  Intel64_nehalem(
      Platform.X86_64,
      "-march=nehalem -O2 -pipe",
      Architecture.X86_64,
      "https://www.funtoo.org/Intel64-nehalem",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.MMX,
        InstructionSet.MMXEXT,
        InstructionSet.POPCNT,
        InstructionSet.SSE,
        InstructionSet.SSE2,
        InstructionSet.SSE3,
        InstructionSet.SSE4_1,
        InstructionSet.SSE4_2,
        InstructionSet.SSSE3
      }),

  Intel64_westmere(
      Platform.X86_64,
      "-march=westmere -O2 -pipe",
      Architecture.X86_64,
      "https://www.funtoo.org/Intel64-westmere",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.AES,
        InstructionSet.MMX,
        InstructionSet.MMXEXT,
        InstructionSet.POPCNT,
        InstructionSet.SSE,
        InstructionSet.SSE2,
        InstructionSet.SSE3,
        InstructionSet.SSE4_1,
        InstructionSet.SSE4_2,
        InstructionSet.SSSE3
      }),
  Intel64_sandybridge(
      Platform.X86_64,
      "-march=sandybridge -O2 -pipe",
      Architecture.X86_64,
      "https://www.funtoo.org/Intel64-sandybridge",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.AES,
        InstructionSet.AVX,
        InstructionSet.MMX,
        InstructionSet.MMXEXT,
        InstructionSet.POPCNT,
        InstructionSet.SSE,
        InstructionSet.SSE2,
        InstructionSet.SSE3,
        InstructionSet.SSE4_1,
        InstructionSet.SSE4_2,
        InstructionSet.SSSE3
      }),
  Intel64_ivybridge(
      Platform.X86_64,
      "-march=ivybridge -O2 -pipe",
      Architecture.X86_64,
      "https://www.funtoo.org/Intel64-ivybridge",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.AES,
        InstructionSet.AVX,
        InstructionSet.MMX,
        InstructionSet.MMXEXT,
        InstructionSet.POPCNT,
        InstructionSet.SSE,
        InstructionSet.SSE2,
        InstructionSet.SSE3,
        InstructionSet.SSE4_1,
        InstructionSet.SSE4_2,
        InstructionSet.SSSE3
      }),
  Intel64_silvermont(
      Platform.X86_64,
      "-march=silvermont -O2 -pipe",
      Architecture.X86_64,
      "https://www.funtoo.org/Intel64-silvermont",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.AES,
        InstructionSet.MMX,
        InstructionSet.MMXEXT,
        InstructionSet.POPCNT,
        InstructionSet.SSE,
        InstructionSet.SSE2,
        InstructionSet.SSE3,
        InstructionSet.SSE4_1,
        InstructionSet.SSE4_2,
        InstructionSet.SSSE3
      }),
  Intel64_haswell(
      Platform.X86_64,
      "-march=haswell -O2 -pipe",
      Architecture.X86_64,
      "https://www.funtoo.org/Intel64-haswell",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.AES,
        InstructionSet.AVX,
        InstructionSet.AVX2,
        InstructionSet.FMA3,
        InstructionSet.MMX,
        InstructionSet.MMXEXT,
        InstructionSet.POPCNT,
        InstructionSet.SSE,
        InstructionSet.SSE2,
        InstructionSet.SSE3,
        InstructionSet.SSE4_1,
        InstructionSet.SSE4_2,
        InstructionSet.SSSE3
      }),
  Intel64_broadwell(
      Platform.X86_64,
      "-march=broadwell -O2 -pipe",
      Architecture.X86_64,
      "https://www.funtoo.org/Intel64-broadwell",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.AES,
        InstructionSet.AVX,
        InstructionSet.AVX2,
        InstructionSet.FMA3,
        InstructionSet.MMX,
        InstructionSet.MMXEXT,
        InstructionSet.POPCNT,
        InstructionSet.SSE,
        InstructionSet.SSE2,
        InstructionSet.SSE3,
        InstructionSet.SSE4_1,
        InstructionSet.SSE4_2,
        InstructionSet.SSSE3
      }),
  Intel64_skylake(
      Platform.X86_64,
      "-march=broadwell -O2 -pipe",
      Architecture.X86_64,
      "https://www.funtoo.org/Intel64-skylake",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.AES,
        InstructionSet.AVX,
        InstructionSet.AVX2,
        InstructionSet.F16C,
        InstructionSet.FMA3,
        InstructionSet.MMX,
        InstructionSet.MMXEXT,
        InstructionSet.PCLMUL,
        InstructionSet.POPCNT,
        InstructionSet.SSE,
        InstructionSet.SSE2,
        InstructionSet.SSE3,
        InstructionSet.SSE4_1,
        InstructionSet.SSE4_2,
        InstructionSet.SSSE3
      }),
  Generic_64(
      Platform.X86_64,
      "-mtune=generic -O2 -pipe",
      // new InstructionSet[]{InstructionSet.MMX, InstructionSet.MMXEXT, InstructionSet.SSE, sse2",
      Architecture.X86_64,
      "https://www.funtoo.org/Generic_64",
      new Variant[] {Variant.Standard},
      new InstructionSet[] {
        InstructionSet.MMX, InstructionSet.MMXEXT, InstructionSet.SSE, InstructionSet.SSE2
      });

  //  private final String chost;
  private final Platform platform;
  private final String cflags;
  // private final String cpuFlags;

  private final Architecture architecture;
  private final String href;

  private final Variant[] variants;
  private final InstructionSet[] instructionSets;

  public String getName() {
    return (name().toLowerCase());
  }
}
