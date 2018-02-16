package com.walterjwhite.linux.builder.api.model.patch;

import java.util.HashSet;
import java.util.Set;

public class Patch {
  // basename -.patch
  protected final String name;
  // populated from filename
  protected final String path;

  // protected List<> audit;
  protected final boolean doesVariantExist;

  // protected List<String> dependencies;

  protected final Set<PatchEdge> inEdges = new HashSet<>();
  protected final Set<PatchEdge> outEdges = new HashSet<>();

  // protected final Map<BuildPhase, Set<>>

  public Patch(String name, String path, final boolean doesVariantExist) {
    super();

    this.name = name;
    this.path = path;
    this.doesVariantExist = doesVariantExist;
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  public boolean isDoesVariantExist() {
    return doesVariantExist;
  }

  public Set<PatchEdge> getInEdges() {
    return inEdges;
  }

  public Set<PatchEdge> getOutEdges() {
    return outEdges;
  }

  public void addDependency(final Patch patch) {
    final PatchEdge patchEdge = new PatchEdge(this, patch);
    outEdges.add(patchEdge);
    patch.getInEdges().add(patchEdge);
  }

  @Override
  public String toString() {
    return "Patch{"
        + "name='"
        + name
        + '\''
        + ", path='"
        + path
        + '\''
        + ", doesVariantExist="
        + doesVariantExist
        + '}';
  }
}
