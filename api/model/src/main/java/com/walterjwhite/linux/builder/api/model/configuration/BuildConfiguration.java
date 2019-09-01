package com.walterjwhite.linux.builder.api.model.configuration;

import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import com.walterjwhite.scm.api.model.SCMConfiguration;
import java.io.File;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString(doNotUseGetters = true)
public class BuildConfiguration {
  protected Distribution distribution;

  protected String buildDirectory; // /builds/linux

  // protected String tag; // master, 2017-01-01

  protected String variant; // router, workstation, nas

  // protected String repositoryUri; // git@localhost:/projects/active/linux
  protected SCMConfiguration scmConfiguration;

  protected String packageFormat;

  protected LocalDateTime buildDate;

  public String getRootDirectory() {
    if (onLiveHost) return "/";

    return buildDirectory
        + File.separator
        + scmConfiguration.getTag().getTag()
        + File.separator
        + variant
        + File.separator
        + "root";
  }

  // buildDirectory/documentation
  public String getDocumentationPath() {
    return buildDirectory + File.separator + "documentation";
  }

  // buildDirectory/package
  public String getPackagePath() {
    return getBuildDirectory()
        + File.separator
        + getVariant()
        + "-"
        + scmConfiguration.getTag().getTag()
        + getPackageFormat();
  }

  // support installing a single patch / module on a live system

  protected boolean onLiveHost = false;

  protected String singlePatchName;
}
