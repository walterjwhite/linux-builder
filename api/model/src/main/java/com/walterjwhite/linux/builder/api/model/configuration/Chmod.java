package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.Objects;

public class Chmod implements Configurable {
  protected String path;
  protected boolean recursive;
  protected String mode;

  public Chmod(String path, boolean recursive, String mode) {
    super();

    this.path = path;
    this.recursive = recursive;
    this.mode = mode;
  }

  public Chmod() {
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

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Chmod chmod = (Chmod) o;
    return recursive == chmod.recursive
        && Objects.equals(path, chmod.path)
        && Objects.equals(mode, chmod.mode);
  }

  @Override
  public int hashCode() {

    return Objects.hash(path, recursive, mode);
  }

  public boolean isRun() {
    return (path != null && !path.isEmpty() && mode != null && !mode.isEmpty());
  }
}
