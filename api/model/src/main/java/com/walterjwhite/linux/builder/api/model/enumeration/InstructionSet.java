package com.walterjwhite.linux.builder.api.model.enumeration;

public enum InstructionSet {
  AES,
  AVX,
  AVX2,
  FMA3,
  F16C,
  PCLMUL,
  MMX,
  MMXEXT,
  SSE,
  SSE2,
  SSE3,
  SSSE3,
  SSE4_1,
  SSE4_2,
  POPCNT;

  public String getCpuFlag() {
    return name().toLowerCase();
  }

  public static String getCpuFlags(final InstructionSet[] instructionSets) {
    //        final StringBuilder buffer = new StringBuilder();
    final String[] instructionSetStrings = new String[instructionSets.length];

    for (int i = 0; i < instructionSets.length; i++)
      instructionSetStrings[i] = instructionSets[i].getCpuFlag();

    //        Arrays.stream(instructionSets).forEach(instructionSet ->
    // buffer.append(instructionSet.getCpuFlag());
    // return String.join(",", instructionSetStrings);
    return String.join(" ", instructionSetStrings);
  }
}
