package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Link implements Configurable {
  protected String path;

  protected Set<String> targets;

  public Link(String path, Set<String> targets) {
    this();

    this.path = path;
    this.targets.addAll(targets);
  }

  public Link() {
    super();

    this.targets = new HashSet<>();
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Set<String> getTargets() {
    return targets;
  }

  public void setTargets(Set<String> targets) {
    this.targets = targets;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Link link = (Link) o;
    return Objects.equals(path, link.path) && Objects.equals(targets, link.targets);
  }

  @Override
  public int hashCode() {

    return Objects.hash(path, targets);
  }

  public boolean isRun() {
    return (path != null && !path.isEmpty() && targets != null && targets.size() > 0);
  }
}
