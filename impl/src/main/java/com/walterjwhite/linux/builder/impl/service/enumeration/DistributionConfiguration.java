package com.walterjwhite.linux.builder.impl.service.enumeration;

import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import com.walterjwhite.linux.builder.api.service.*;
import com.walterjwhite.linux.builder.impl.service.bootstrap.DebianBootstrappingService;
import com.walterjwhite.linux.builder.impl.service.bootstrap.FreeBSDBootstrappingService;
import com.walterjwhite.linux.builder.impl.service.bootstrap.GentooBootstrappingService;
import com.walterjwhite.linux.builder.impl.service.groupadd.FreeBSDGroupaddService;
import com.walterjwhite.linux.builder.impl.service.groupadd.LinuxGroupaddService;
import com.walterjwhite.linux.builder.impl.service.groups.FreeBSDGroupsService;
import com.walterjwhite.linux.builder.impl.service.groups.LinuxGroupsService;
import com.walterjwhite.linux.builder.impl.service.hostname.DebianHostnameService;
import com.walterjwhite.linux.builder.impl.service.hostname.FreeBSDHostnameService;
import com.walterjwhite.linux.builder.impl.service.hostname.OpenRCHostnameService;
import com.walterjwhite.linux.builder.impl.service.packagemanagement.AptGetPackageManagementService;
import com.walterjwhite.linux.builder.impl.service.packagemanagement.EmergePackageManagementService;
import com.walterjwhite.linux.builder.impl.service.packagemanagement.FreeBSDPackageManagementService;
import com.walterjwhite.linux.builder.impl.service.packagemanagement.FuntooEmergePackageManagementService;
import com.walterjwhite.linux.builder.impl.service.runlevel.FreeBSDRunlevelManagementService;
import com.walterjwhite.linux.builder.impl.service.runlevel.OpenRCRunlevelManagementService;
import com.walterjwhite.linux.builder.impl.service.runlevel.SystemDRunlevelManagementService;
import com.walterjwhite.linux.builder.impl.service.useradd.FreeBSDUseraddService;
import com.walterjwhite.linux.builder.impl.service.useradd.LinuxUseraddService;
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
      null,
      LinuxUseraddService.class,
      LinuxGroupsService.class,
      LinuxGroupaddService.class),
  Gentoo(
      Distribution.Gentoo,
      Linux,
      new MountPoint[] {
        new MountPoint("/var/cache/portage/distfiles", "~/distfiles", VFSType.BIND),
        /*new MountPoint("/usr/src", "~/src", VFSType.BIND)*/
        /*new MountPoint("/var/cache", "none", VFSType.TMPFS)*/ },
      EmergePackageManagementService.class,
      GentooBootstrappingService.class,
      OpenRCRunlevelManagementService.class,
      OpenRCHostnameService.class,
      null,
      null,
      null),
  Funtoo(
      Distribution.Funtoo,
      Gentoo,
      new MountPoint[] {new MountPoint("/var/git", "~/var-git", VFSType.BIND)},
      FuntooEmergePackageManagementService.class,
      null,
      null,
      null,
      null,
      null,
      null),

  Debian(
      Distribution.Debian,
      Linux,
      new MountPoint[] {},
      AptGetPackageManagementService.class,
      DebianBootstrappingService.class,
      SystemDRunlevelManagementService.class,
      DebianHostnameService.class,
      null,
      null,
      null),
  Ubuntu(
      Distribution.Ubuntu, Debian, new MountPoint[] {}, null, null, null, null, null, null, null),
  FreeBSD(
      Distribution.FreeBSD,
      null,
      new MountPoint[] {},
      FreeBSDPackageManagementService.class,
      FreeBSDBootstrappingService.class,
      FreeBSDRunlevelManagementService.class,
      FreeBSDHostnameService.class,
      FreeBSDUseraddService.class,
      FreeBSDGroupsService.class,
      FreeBSDGroupaddService.class);

  private MountPoint[] mountPoints;
  private MountPoint[] umountPoints;

  private Class<? extends PackageManagementService> packageManagementServiceClass;
  private Class<? extends DistributionBootstrappingService> bootstrappingServiceClass;
  private Class<? extends RunlevelManagementService> runlevelManagementService;
  private Class<? extends HostnameService> hostnameServiceClass;
  private Class<? extends UseraddService> useraddServiceClass;
  private Class<? extends GroupsService> groupsServiceClass;
  private Class<? extends GroupaddService> groupaddServiceClass;

  private DistributionConfiguration parent;

  private Distribution distribution;

  DistributionConfiguration(
      Distribution distribution,
      DistributionConfiguration parent,
      MountPoint[] mountPoints,
      Class<? extends PackageManagementService> packageManagementServiceClass,
      Class<? extends DistributionBootstrappingService> bootstrappingServiceClass,
      Class<? extends RunlevelManagementService> runlevelManagementService,
      Class<? extends HostnameService> hostnameServiceClass,
      Class<? extends UseraddService> useraddServiceClass,
      Class<? extends GroupsService> groupsServiceClass,
      Class<? extends GroupaddService> groupaddServiceClass) {
    this.distribution = distribution;

    this.parent = parent;
    this.mountPoints = mountPoints;

    this.umountPoints = ArrayUtils.clone(mountPoints);
    ArrayUtils.reverse(this.umountPoints);

    this.packageManagementServiceClass = packageManagementServiceClass;
    this.bootstrappingServiceClass = bootstrappingServiceClass;

    this.runlevelManagementService = runlevelManagementService;
    this.hostnameServiceClass = hostnameServiceClass;

    this.useraddServiceClass = useraddServiceClass;
    this.groupsServiceClass = groupsServiceClass;
    this.groupaddServiceClass = groupaddServiceClass;
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

  public Class<? extends UseraddService> getUseraddServiceClass() {
    return useraddServiceClass;
  }

  public Class<? extends UseraddService> getImplementingUseraddServiceClass() {
    if (useraddServiceClass != null) {
      return (useraddServiceClass);
    }

    return (getParent().getImplementingUseraddServiceClass());
  }

  public Class<? extends GroupsService> getGroupsServiceClass() {
    return groupsServiceClass;
  }

  public Class<? extends GroupsService> getImplementingGroupsServiceClass() {
    if (groupsServiceClass != null) {
      return (groupsServiceClass);
    }

    return (getParent().getImplementingGroupsServiceClass());
  }

  public Class<? extends GroupaddService> getGroupaddServiceClass() {
    return groupaddServiceClass;
  }

  public Class<? extends GroupaddService> getImplementingGroupaddServiceClass() {
    if (groupaddServiceClass != null) {
      return (groupaddServiceClass);
    }

    return (getParent().getImplementingGroupaddServiceClass());
  }

  public static DistributionConfiguration get(final Distribution distribution) {
    for (final DistributionConfiguration distributionConfiguration : values()) {
      if (distributionConfiguration.getDistribution() != null
          && distributionConfiguration.getDistribution().equals(distribution)) {
        return (distributionConfiguration);
      }
    }

    throw new IllegalStateException("Configurable not found for distribution:" + distribution);
  }
}
