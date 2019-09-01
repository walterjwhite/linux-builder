package com.walterjwhite.linux.builder.api.model.patch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@Data
@ToString(doNotUseGetters = true)
public class PatchEdge {
  protected Patch from;

  protected Patch to;
}
