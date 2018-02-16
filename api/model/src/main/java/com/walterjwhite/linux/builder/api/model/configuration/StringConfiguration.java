package com.walterjwhite.linux.builder.api.model.configuration;

public class StringConfiguration implements Configurable {
  protected String content;

  public StringConfiguration(String content) {
    super();
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean isRun() {
    return (content != null && !content.isEmpty());
  }
}
