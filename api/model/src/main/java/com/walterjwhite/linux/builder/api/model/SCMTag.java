package com.walterjwhite.linux.builder.api.model;

import java.util.Date;

public class SCMTag {
  protected String tag;
  protected String variant;
  protected String scmVersionId;
  protected String commitMessage;
  protected Date commitDate;
  protected Date buildDate;

  public SCMTag(
      String tag,
      String variant,
      Date commitDate,
      Date buildDate,
      String scmVersionId,
      String commitMessage) {
    super();

    this.tag = tag;
    this.variant = variant;
    this.commitDate = commitDate;
    this.buildDate = buildDate;
    this.scmVersionId = scmVersionId;

    this.commitMessage = commitMessage;
  }

  public SCMTag() {
    super();
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getVariant() {
    return variant;
  }

  public void setVariant(String variant) {
    this.variant = variant;
  }

  public Date getCommitDate() {
    return commitDate;
  }

  public void setCommitDate(Date commitDate) {
    this.commitDate = commitDate;
  }

  public Date getBuildDate() {
    return buildDate;
  }

  public void setBuildDate(Date buildDate) {
    this.buildDate = buildDate;
  }

  public String getScmVersionId() {
    return scmVersionId;
  }

  public void setScmVersionId(String scmVersionId) {
    this.scmVersionId = scmVersionId;
  }

  public String getCommitMessage() {
    return commitMessage;
  }

  public void setCommitMessage(String commitMessage) {
    this.commitMessage = commitMessage;
  }
}
