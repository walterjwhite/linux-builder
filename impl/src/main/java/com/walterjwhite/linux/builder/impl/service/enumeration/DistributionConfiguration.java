package com.walterjwhite.linux.builder.impl.service.enumeration;

import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import com.walterjwhite.linux.builder.api.service.DistributionBootstrappingService;
import com.walterjwhite.linux.builder.api.service.HostnameService;
import com.walterjwhite.linux.builder.api.service.PackageManagementService;
import com.walterjwhite.linux.builder.api.service.RunlevelManagementService;
import com.walterjwhite.linux.builder.impl.service.bootstrap.DebianBootstrappingService;
import com.walterjwhite.linux.builder.impl.service.bootstrap.GentooBootstrappingService;
import com.walterjwhite.linux.builder.impl.service.hostname.DebianHostnameService;
import com.walterjwhite.linux.builder.impl.service.hostname.OpenRCHostnameService;
import com.walterjwhite.linux.builder.impl.service.packagemanagement.AptGetPackageManagementService;
import com.walterjwhite.linux.builder.impl.service.packagemanagement.EmergePackageManagementService;
import com.walterjwhite.linux.builder.impl.service.runlevel.OpenRCRunlevelManagementService;
import com.walterjwhite.linux.builder.impl.service.runlevel.SystemDRunlevelManagementService;
import com.walterjwhite.shell.api.enumeration.VFSType;
import com.walterjwhite.shell.api.model.MountPoint;
import org.apache.commons.lang3.ArrayUtils;

public enum DistributionConfiguration {
  Linux(
      null,
      null,
      new MountPoint[] {
        new MountPoint("/proc", "none", VFSType.PROC),
        new MountPoint("/dev", "/dev", VFSType.RBIND),
        new MountPoint("/sys", "/sys", VFSType.RBIND),
        new MountPoint("/projects", "/projects", VFSType.BIND),
        new MountPoint("/run", "none", VFSType.TMPFS),

        // noexec breaks the scripts that are copied to /tmp/runInChroot
        new MountPoint("/tmp", "none", VFSType.TMPFS /*, "noexec,nosuid"*/),

        // added this to resolve hardened build issues
        new MountPoint("/var/tmp", "none", VFSType.TMPFS /*, "noexec,nosuid"*/),
        new MountPoint("/tmp/downloads", "~/downloads", VFSType.BIND),

        // python binaries (personal python libraries)
        new MountPoint("/tmp/python", "~/python", VFSType.BIND),
      },
      null,
      null,
      null,
      null),
  Gentoo(
      Distribution.Gentoo,
      Linux,
      new MountPoint[] {
        new MountPoint("/var/cache/portage/distfiles", "~/distfiles", VFSType.BIND),
        new MountPoint("/var/git", "~/var-git", VFSType.BIND),
        new MountPoint("/usr/src", "~/src", VFSType.BIND)
        /*new MountPoint("/var/cache", "none", VFSType.TMPFS)*/ },
      EmergePackageManagementService.class,
      GentooBootstrappingService.class,
      OpenRCRunlevelManagementService.class,
      OpenRCHostnameService.class),
  Funtoo(Distribution.Funtoo, Gentoo, new MountPoint[] {}, null, null, null, null),

  Debian(
      Distribution.Debian,
      Linux,
      new MountPoint[] {},
      AptGetPackageManagementService.class,
      DebianBootstrappingService.class,
      SystemDRunlevelManagementService.class,
      DebianHostnameService.class),
  Ubuntu(Distribution.Ubuntu, Debian, new MountPoint[] {}, null, null, null, null);

  private MountPoint[] mountPoints;
  private MountPoint[] umountPoints;

  private Class<? extends PackageManagementService> packageManagementServiceClass;
  private Class<? extends DistributionBootstrappingService> bootstrappingServiceClass;
  private Class<? extends RunlevelManagementService> runlevelManagementService;
  private Class<? extends HostnameService> hostnameServiceClass;

  private DistributionConfiguration parent;

  private Distribution distribution;

  DistributionConfiguration(
      Distribution distribution,
      DistributionConfiguration parent,
      MountPoint[] mountPoints,
      Class<? extends PackageManagementService> packageManagementServiceClass,
      Class<? extends DistributionBootstrappingService> bootstrappingServiceClass,
      Class<? extends RunlevelManagementService> runlevelManagementService,
      Class<? extends HostnameService> hostnameServiceClass) {
    this.distribution = distribution;

    this.parent = parent;
    this.mountPoints = mountPoints;

    this.umountPoints = ArrayUtils.clone(mountPoints);
    ArrayUtils.reverse(this.umountPoints);

    this.packageManagementServiceClass = packageManagementServiceClass;
    this.bootstrappingServiceClass = bootstrappingServiceClass;

    this.runlevelManagementService = runlevelManagementService;
    this.hostnameServiceClass = hostnameServiceClass;
  }

  public MountPoint[] getMountPoints() {
    return mountPoints;
  }

  public MountPoint[] getUmountPoints() {
    return umountPoints;
  }

  public Class<? extends PackageManagementService> getPackageManagementServiceClass() {
    return packageManagementServiceClass;
  }

  public Class<? extends PackageManagementService> getImplementingPackageManagementServiceClass() {
    if (packageManagementServiceClass != null) {
      return (packageManagementServiceClass);
    }

    return (getParent().getImplementingPackageManagementServiceClass());
  }

  public Class<? extends DistributionBootstrappingService> getBootstrappingServiceClass() {
    return bootstrappingServiceClass;
  }

  public Class<? extends DistributionBootstrappingService>
      getImplementingBootstrappingServiceClass() {
    if (bootstrappingServiceClass != null) {
      return (bootstrappingServiceClass);
    }

    return (getParent().getImplementingBootstrappingServiceClass());
  }

  public DistributionConfiguration getParent() {
    return parent;
  }

  public Class<? extends RunlevelManagementService> getRunlevelManagementService() {
    return runlevelManagementService;
  }

  public Class<? extends RunlevelManagementService>
      getImplementingRunlevelManagementServiceClass() {
    if (runlevelManagementService != null) {
      return (runlevelManagementService);
    }

    return (getParent().getImplementingRunlevelManagementServiceClass());
  }

  public Distribution getDistribution() {
    return distribution;
  }

  public Class<? extends HostnameService> getHostnameServiceClass() {
    return hostnameServiceClass;
  }

  public Class<? extends HostnameService> getImplementingHostnameServiceClass() {
    if (hostnameServiceClass != null) {
      return (hostnameServiceClass);
    }

    return (getParent().getImplementingHostnameServiceClass());
  }

  public static DistributionConfiguration get(final Distribution distribution) {
    for (final DistributionConfiguration distributionConfiguration : values()) {
      if (distributionConfiguration.getDistribution() != null
          && distributionConfiguration.getDistribution().equals(distribution)) {
        return (distributionConfiguration);
      }
    }

    throw (new IllegalStateException("Configurable not found for distribution:" + distribution));
  }
}
