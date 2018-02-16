package com.walterjwhite.linux.builder.api.service;

public interface PackageManagementService {
  /** Install the packages. */
  void install(String... packageNames) throws Exception;

  /** Uninstall the packages. */
  void uninstall(String... packageNames) throws Exception;

  /** Perform system update. */
  void update() throws Exception;

  boolean isInstalled(String packageName) throws Exception;
}
