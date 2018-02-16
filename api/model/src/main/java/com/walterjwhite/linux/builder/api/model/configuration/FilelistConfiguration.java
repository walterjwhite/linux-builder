package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.ArrayList;
import java.util.List;

public class FilelistConfiguration implements Configurable {
  protected List<String> filenames;

  public FilelistConfiguration(List<String> filenames) {
    this();
    this.filenames.addAll(filenames);
  }

  public FilelistConfiguration() {
    super();
    filenames = new ArrayList<>();
  }

  public List<String> getFilenames() {
    return filenames;
  }

  public void setFilenames(List<String> filenames) {
    this.filenames = filenames;
  }

  @Override
  public boolean isRun() {
    return (filenames != null && !filenames.isEmpty());
  }
}
