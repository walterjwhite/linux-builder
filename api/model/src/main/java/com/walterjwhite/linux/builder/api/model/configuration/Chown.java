package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.Objects;

public class Chown implements Configurable {
  protected String path;
  protected boolean recursive;
  protected String owner;
  protected String group;

  public Chown(String path, boolean recursive, String owner, String group) {
    super();

    this.path = path;
    this.recursive = recursive;
    this.owner = owner;
    this.group = group;
  }

  public Chown() {
    super();
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public boolean isRecursive() {
    return recursive;
  }

  public void setRecursive(boolean recursive) {
    this.recursive = recursive;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Chown chown = (Chown) o;
    return recursive == chown.recursive
        && Objects.equals(path, chown.path)
        && Objects.equals(owner, chown.owner)
        && Objects.equals(group, chown.group);
  }

  @Override
  public int hashCode() {

    return Objects.hash(path, recursive, owner, group);
  }

  public boolean isRun() {
    return (path != null
        && !path.isEmpty()
        && ((owner != null && !owner.isEmpty()) || (group != null && !group.isEmpty())));
  }
}
