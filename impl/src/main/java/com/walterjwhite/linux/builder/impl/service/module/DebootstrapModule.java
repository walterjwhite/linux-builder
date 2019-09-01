package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.download.api.model.Download;
import com.walterjwhite.download.api.service.DownloadService;
import com.walterjwhite.encryption.service.DigestService;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.DebootstrapConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.shell.api.service.MountService;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import javax.inject.Inject;

@ModuleSupports(
    distribution = DistributionConfiguration.Debian,
    configurer = YamlConfigurer.class,
    configurationClass = DebootstrapConfiguration.class)
public class DebootstrapModule extends AbstractSingleModule<DebootstrapConfiguration> {
  protected final ShellCommandBuilder shellCommandBuilder;
  protected final DigestService digestService;
  protected final MountService mountService;
  protected final ShellExecutionService shellExecutionService;
  protected final DownloadService downloadService;

  @Inject
  public DebootstrapModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      ShellCommandBuilder shellCommandBuilder,
      DigestService digestService,
      MountService mountService,
      ShellExecutionService shellExecutionService,
      DownloadService downloadService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.shellCommandBuilder = shellCommandBuilder;
    this.digestService = digestService;
    this.mountService = mountService;
    this.shellExecutionService = shellExecutionService;
    this.downloadService = downloadService;
  }

  @Override
  public void document() {}

  @Override
  public void doRun() throws Exception {
    setupRoot();
  }

  protected void setupRoot() throws Exception {
    Download download =
        new Download(
            configuration.getDebootstrapUri(),
            configuration.getDebootstrapChecksum(),
            "debootstrap.deb");
    downloadService.download(download);
    shellExecutionService.run(
        shellCommandBuilder
            .build()
            .withCommandLine(
                "ar -x /tmp/downloads/debootstrap.deb | tar xv -C "
                    + buildConfiguration.getRootDirectory()));
    shellExecutionService.run(
        shellCommandBuilder
            .build()
            .withCommandLine(
                "debootstrap --arch "
                    + configuration.getArchitecture()
                    + " "
                    + configuration.getMirrorUri()
                    + " LANG=C.UTF-8"));

    // chroot stuff
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine("apt install -y --allow-unauthenticated makedev"));
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine("mount -t proc none /proc"));
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine("cd /dev && MAKEDEV generic"));

    // update /etc/apt/sources.list
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine("apt update apt upgrade -y --allow-unauthenticated"));
    shellExecutionService.run(
        shellCommandBuilder
            .buildChroot()
            .withChrootPath(buildConfiguration.getRootDirectory())
            .withCommandLine("apt install -y --allow-unauthenticated linux-image-amd64"));
    /*
     * <p>apt install locales # uses ncurses #dpkg-reconfigure locales
     *
     * <p># boot loader apt install grub-pc # grub-install # update-grub
     *
     * <p>apt clean to free up downloaded packages
     */
  }
}
