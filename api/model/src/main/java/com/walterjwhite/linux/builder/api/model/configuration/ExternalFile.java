package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.Objects;

public class ExternalFile implements Configurable {
  protected String source;
  protected String target;

  public ExternalFile(String source, String target) {
    super();

    this.source = source;
    this.target = target;
  }

  public ExternalFile() {
    super();
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ExternalFile that = (ExternalFile) o;
    return Objects.equals(source, that.source) && Objects.equals(target, that.target);
  }

  @Override
  public int hashCode() {

    return Objects.hash(source, target);
  }

  public boolean isRun() {
    return (source != null && !source.isEmpty() && target != null && !target.isEmpty());
  }
}
