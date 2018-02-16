package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// TODO: not currently implemented
public class Replace implements Configurable {
  protected String filename;
  protected Map<String, Map<Integer, String>> mimeMapping;

  public Replace(String filename, Map<String, Map<Integer, String>> mimeMapping) {
    this();

    this.filename = filename;
    this.mimeMapping = mimeMapping;
  }

  public Replace() {
    super();

    mimeMapping = new HashMap<>();
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public Map<String, Map<Integer, String>> getMimeMapping() {
    return mimeMapping;
  }

  public void setMimeMapping(Map<String, Map<Integer, String>> mimeMapping) {
    this.mimeMapping = mimeMapping;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Replace replace = (Replace) o;
    return Objects.equals(filename, replace.filename)
        && Objects.equals(mimeMapping, replace.mimeMapping);
  }

  @Override
  public int hashCode() {

    return Objects.hash(filename, mimeMapping);
  }

  public boolean isRun() {
    return (filename != null
        && !filename.isEmpty()
        && mimeMapping != null
        && !mimeMapping.isEmpty());
  }
}
