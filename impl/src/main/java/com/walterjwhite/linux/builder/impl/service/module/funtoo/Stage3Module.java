package com.walterjwhite.linux.builder.impl.service.module.funtoo;

import com.walterjwhite.download.api.model.Download;
import com.walterjwhite.download.api.service.DownloadService;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Stage3Configuration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.module.AbstractSingleModule;
import com.walterjwhite.linux.builder.impl.service.util.FuntooStageUriUtil;
import com.walterjwhite.linux.builder.impl.service.util.PortageUtil;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

@ModuleSupports(
  distribution = DistributionConfiguration.Gentoo,
  configurer = YamlConfigurer.class,
  configurationClass = Stage3Configuration.class
)
public class Stage3Module extends AbstractSingleModule<Stage3Configuration> {
  protected String stage3Uri;
  protected String stage3ChecksumUri;

  protected final ShellCommandBuilder shellCommandBuilder;
  protected final DownloadService downloadService;
  protected final ShellExecutionService shellExecutionService;

  @Inject
  public Stage3Module(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      ShellCommandBuilder shellCommandBuilder,
      DownloadService downloadService,
      ShellExecutionService shellExecutionService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.shellCommandBuilder = shellCommandBuilder;
    this.downloadService = downloadService;
    this.shellExecutionService = shellExecutionService;
  }

  public void onSetup() {
    super.onSetup();

    this.stage3Uri = FuntooStageUriUtil.getStageUri(configuration);
    this.stage3ChecksumUri = FuntooStageUriUtil.getStage3ChecksumUri(stage3Uri);
  }

  @Override
  public void document() {
    // super.document();
    // tex.write_table(documentation_directory, 'Stage3Module AbstractConfiguration', ['Key',
    // 'Value'], [.2, .8],
    //                [['Stage3Module URI', self.stage3_uri], ['Stage3Module Checksum URI',
    // self.stage3_checksum_uri], ['Stage3Module Checksum', self.stage3_checksum]])
  }

  @Override
  public void doRun() throws Exception {
    setupRoot();
    configureMakeConf();
    //    configureReposConf();
  }

  protected boolean setupRoot() throws Exception {
    if (isChrootEnvironmentSetup()) return false;

    // ensure we use the latest stage3
    final File checksumFile = downloadService.download(new Download(stage3ChecksumUri, null));
    final File stage3File =
        downloadService.download(new Download(stage3ChecksumUri, null), checksumFile);

    extractStage3File(stage3File);
    removeVarGit();
    removeUsrSrc();

    return true;
  }

  protected boolean isChrootEnvironmentSetup() {
    return new File(buildConfiguration.getRootDirectory() + "/bin/bash").exists();
  }

  protected void extractStage3File(final File stage3File) throws Exception {
    final File root = new File(buildConfiguration.getRootDirectory());
    if (!root.exists()) {
      root.mkdirs();
    }

    shellExecutionService.run(
        shellCommandBuilder
            .build()
            .withCommandLine(
                "tar xpf "
                    + stage3File.getAbsolutePath()
                    + " -C "
                    + buildConfiguration.getRootDirectory()));

    // this is a symlink that is causing problems
    // actually, funtoo is using kits ... we need to initialize it properly
    //    new File(buildConfiguration.getRootDirectory() + "/etc/portage/repos.conf").delete();

    // wipe out stuff that we're sharing
    // /var/git/meta-repo
    final File metaRepoDirectory =
        new File(buildConfiguration.getRootDirectory() + "/var/git/meta-repo");
    shellExecutionService.run(
        shellCommandBuilder
            .build()
            .withCommandLine("rm -rf " + metaRepoDirectory.getAbsolutePath()));
    metaRepoDirectory.mkdirs();

    // wipe out stuff that we're sharing
    // /usr/src
    final File usrSrcDirectory = new File(buildConfiguration.getRootDirectory() + "/src/src");
    shellExecutionService.run(
        shellCommandBuilder.build().withCommandLine("rm -rf " + usrSrcDirectory.getAbsolutePath()));
    usrSrcDirectory.mkdirs();
  }

  protected void configureMakeConf() throws IOException {
    FileUtils.write(getPortageConfigurationFile(), getContents(), "UTF-8", false);
  }

  protected File getPortageConfigurationFile() {
    return (new File(
        buildConfiguration.getRootDirectory() + File.separator + "etc/portage/make.conf"));
  }

  protected String getContents() {
    final StringBuilder buffer = new StringBuilder();
    PortageUtil.append(buffer, "http_proxy", System.getenv("http_proxy"));
    buffer.append("\n");

    PortageUtil.append(buffer, "CHOST", configuration.getSubArchitecture().getChost());
    PortageUtil.append(buffer, "CFLAGS", configuration.getSubArchitecture().getCflags());
    PortageUtil.append(buffer, "CXXFLAGS", configuration.getSubArchitecture().getCflags());
    PortageUtil.append(buffer, "CPU_FLAGS_X86", configuration.getSubArchitecture().getCpuFlags());
    PortageUtil.append(buffer, "MAKEOPTS", getMakeOpts());

    if (configuration.getMirrors() != null)
      buffer.append("GENTOO_MIRRORS=\"" + String.join(" ", configuration.getMirrors()) + "\"\n");

    buffer.append("USE=\"\"\n");
    buffer.append("VIDEO_CARDS=\"\"\n");
    buffer.append("SANE_BACKENDS=\"\"\n");
    buffer.append("LINGUAS=\"\"\n");
    buffer.append("\n");

    return (buffer.toString());
  }

  private static final String getMakeOpts() {
    final int nCpus = Runtime.getRuntime().availableProcessors();

    return "-j" + (nCpus + 1);
  }

  protected void removeVarGit() throws IOException {
    FileUtils.deleteDirectory(new File(buildConfiguration.getRootDirectory() + "/var/git"));
  }

  protected void removeUsrSrc() throws IOException {
    FileUtils.deleteDirectory(new File(buildConfiguration.getRootDirectory() + "/usr/src"));
  }
}
