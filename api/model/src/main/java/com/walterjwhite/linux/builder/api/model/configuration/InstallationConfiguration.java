package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.HashSet;
import java.util.Set;

public class InstallationConfiguration implements Configurable {
  protected Set<String> hosts;
  protected String installationPath;
  protected Set<String> devices;

  public InstallationConfiguration(
      Set<String> hosts, String installationPath, Set<String> devices) {
    this();

    this.hosts.addAll(hosts);
    this.installationPath = installationPath;
    this.devices.addAll(devices);
  }

  public InstallationConfiguration() {
    super();
    this.hosts = new HashSet<>();
    this.devices = new HashSet<>();
  }

  public Set<String> getHosts() {
    return hosts;
  }

  public void setHosts(Set<String> hosts) {
    this.hosts.clear();
    this.hosts.addAll(hosts);
  }

  public String getInstallationPath() {
    return installationPath;
  }

  public void setInstallationPath(String installationPath) {
    this.installationPath = installationPath;
  }

  public Set<String> getDevices() {
    return devices;
  }

  public void setDevices(Set<String> devices) {
    this.devices.clear();

    if (devices != null && devices.size() > 0) this.devices.addAll(devices);
  }

  @Override
  public boolean isRun() {
    return (((hosts != null && !hosts.isEmpty())
            && (installationPath != null && !installationPath.isEmpty()))
        || (devices != null && !devices.isEmpty()));
  }
}
