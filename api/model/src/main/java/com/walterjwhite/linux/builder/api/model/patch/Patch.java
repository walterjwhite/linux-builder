package com.walterjwhite.linux.builder.api.model.patch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@RequiredArgsConstructor
@Data
@ToString(doNotUseGetters = true)
// @Builder
public class Patch {
  // basename -.patch
  @NonNull protected String name;
  // populated from filename
  @NonNull protected String path;

  // protected List<> audit;
  @NonNull @EqualsAndHashCode.Exclude protected boolean doesVariantExist;

  @ToString.Exclude @EqualsAndHashCode.Exclude
  protected List<String> dependencies = new ArrayList<>();

  @ToString.Exclude @EqualsAndHashCode.Exclude protected Set<PatchEdge> inEdges = new HashSet<>();

  @ToString.Exclude @EqualsAndHashCode.Exclude protected Set<PatchEdge> outEdges = new HashSet<>();

  //  @Value.Derived
  public void addDependency(final Patch patch) {
    final PatchEdge patchEdge = new PatchEdge(this, patch);
    outEdges.add(patchEdge);
    patch.getInEdges().add(patchEdge);
  }
}
