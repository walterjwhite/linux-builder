package com.walterjwhite.linux.builder.api.model.configuration;

import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import java.io.File;

public class BuildConfiguration {
  protected Distribution distribution;
  protected String buildDirectory; // /builds/linux
  protected String tag; // master, 2017-01-01
  protected String variant; // router, workstation, nas
  protected String repositoryUri; // git@localhost:/projects/active/linux
  protected String rootDirectory;

  protected String localWorkspace; // buildDirectory/<tag>/<variant>
  protected String documentationPath; // buildDirectory/documentation
  protected String packagePath; // buildDirectory/package

  // support installing a single patch / module on a live system
  protected boolean onLiveHost;

  protected String singlePatchName;

  public BuildConfiguration(
      Distribution distribution,
      String buildDirectory,
      String tag,
      String variant,
      String repositoryUri) {
    this(distribution, buildDirectory, tag, variant, repositoryUri, false, null);
  }

  public BuildConfiguration(
      Distribution distribution,
      String buildDirectory,
      String tag,
      String variant,
      String repositoryUri,
      final boolean onLiveHost,
      final String singlePatchName) {
    super();

    this.distribution = distribution;
    this.buildDirectory = buildDirectory;
    this.tag = tag;
    this.variant = variant;
    this.repositoryUri = repositoryUri;

    if (onLiveHost) this.rootDirectory = "/";
    else {
      this.rootDirectory =
          this.buildDirectory
              + File.separator
              + tag
              + File.separator
              + variant
              + File.separator
              + "root";
    }

    this.onLiveHost = onLiveHost;
    this.singlePatchName = singlePatchName;
  }

  /*
  public BuildConfiguration() {
      super();
  }
  */

  public Distribution getDistribution() {
    return distribution;
  }

  public void setDistribution(Distribution distribution) {
    this.distribution = distribution;
  }

  public String getBuildDirectory() {
    return buildDirectory;
  }

  public void setBuildDirectory(String buildDirectory) {
    this.buildDirectory = buildDirectory;
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

  public String getRepositoryUri() {
    return repositoryUri;
  }

  public void setRepositoryUri(String repositoryUri) {
    this.repositoryUri = repositoryUri;
  }

  public String getRootDirectory() {
    return rootDirectory;
  }

  public void setRootDirectory(String rootDirectory) {
    this.rootDirectory = rootDirectory;
  }

  public String getLocalWorkspace() {
    return localWorkspace;
  }

  public void setLocalWorkspace(String localWorkspace) {
    this.localWorkspace = localWorkspace;
  }

  public String getDocumentationPath() {
    return documentationPath;
  }

  public void setDocumentationPath(String documentationPath) {
    this.documentationPath = documentationPath;
  }

  public String getPackagePath() {
    return packagePath;
  }

  public void setPackagePath(String packagePath) {
    this.packagePath = packagePath;
  }

  public boolean isOnLiveHost() {

    return onLiveHost;
  }

  public void setOnLiveHost(boolean onLiveHost) {
    this.onLiveHost = onLiveHost;
  }

  public String getSinglePatchName() {
    return singlePatchName;
  }

  public void setSinglePatchName(String singlePatchName) {
    this.singlePatchName = singlePatchName;
  }

  @Override
  public String toString() {
    return "BuildConfiguration{"
        + "distribution="
        + distribution
        + ", buildDirectory='"
        + buildDirectory
        + '\''
        + ", tag='"
        + tag
        + '\''
        + ", variant='"
        + variant
        + '\''
        + ", repositoryUri='"
        + repositoryUri
        + '\''
        + ", rootDirectory='"
        + rootDirectory
        + '\''
        + ", localWorkspace='"
        + localWorkspace
        + '\''
        + ", documentationPath='"
        + documentationPath
        + '\''
        + ", packagePath='"
        + packagePath
        + '\''
        + ", onLiveHost="
        + onLiveHost
        + ", singlePatchName='"
        + singlePatchName
        + '\''
        + '}';
  }
}
