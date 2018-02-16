package com.walterjwhite.linux.builder.api.model.patch;

import java.util.Objects;

public class PatchEdge {
  protected final Patch from;
  protected final Patch to;

  public PatchEdge(Patch from, Patch to) {
    super();

    this.from = from;
    this.to = to;
  }

  public Patch getFrom() {
    return from;
  }

  public Patch getTo() {
    return to;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PatchEdge patchEdge = (PatchEdge) o;
    return Objects.equals(from, patchEdge.from) && Objects.equals(to, patchEdge.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to);
  }

  @Override
  public String toString() {
    return "PatchEdge{" + "from=" + from.getName() + ", to=" + to.getName() + '}';
  }
}
