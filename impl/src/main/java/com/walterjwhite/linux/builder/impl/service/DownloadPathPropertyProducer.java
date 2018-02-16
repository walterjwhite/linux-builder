// package com.walterjwhite.linux.builder.impl.service;
//
// import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
// import java.io.File;
// import javax.inject.Inject;
//
// public class DownloadPathPropertyProducer implements Producer<Property<DownloadPath, String>> {
//  protected final String downloadPath;
//
//  @Inject
//  public DownloadPathPropertyProducer(BuildConfiguration buildConfiguration) {
//    downloadPath = buildConfiguration.getBuildDirectory() + File.separator + "downloads";
//  }
//
//  public String getProperty() {
//    return downloadPath;
//  }
// }
