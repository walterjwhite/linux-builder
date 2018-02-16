package com.walterjwhite.linux.builder.api.model.configuration;

public class FilenameConfiguration implements Configurable {
  protected String filename;

  public FilenameConfiguration(String filename) {
    this();

    this.filename = filename;
  }

  public FilenameConfiguration() {
    super();
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  @Override
  public boolean isRun() {
    return (filename != null && !filename.isEmpty());
  }
}
