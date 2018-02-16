package com.walterjwhite.linux.builder.impl.service;

import com.google.inject.AbstractModule;
import com.walterjwhite.compression.modules.CompressionModule;
import com.walterjwhite.download.providers.commons.io.CommonsIODownloadModule;
import com.walterjwhite.encryption.impl.EncryptionModule;
import com.walterjwhite.file.impl.service.DefaultFileStorageModule;
import com.walterjwhite.file.providers.local.service.FileStorageModule;
import com.walterjwhite.google.guice.GuavaEventBusModule;
import com.walterjwhite.linux.builder.api.service.*;
import com.walterjwhite.linux.builder.impl.service.build.DefaultBuildService;
import com.walterjwhite.linux.builder.impl.service.documentation.TexDocumentationService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.installation.UnixHostInstallationService;
import com.walterjwhite.linux.builder.impl.service.packaging.SquashfsPackagingService;
import com.walterjwhite.linux.builder.impl.service.provider.*;
import com.walterjwhite.linux.builder.impl.service.scm.DefaultSCMManagementService;
import com.walterjwhite.linux.builder.impl.service.scm.GitSCMService;
import com.walterjwhite.shell.api.service.MountService;
import com.walterjwhite.shell.impl.ShellModule;
import com.walterjwhite.shell.impl.service.DefaultMountService;
import com.walterjwhite.ssh.impl.SSHModule;
import javax.inject.Singleton;

public class LinuxBuilderModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(BuildService.class).to(DefaultBuildService.class);

    bind(DocumentationService.class).to(TexDocumentationService.class);
    bind(InstallationService.class).to(UnixHostInstallationService.class);
    bind(PackagingService.class).to(SquashfsPackagingService.class);
    bind(SCMManagementService.class).to(DefaultSCMManagementService.class);
    bind(SCMService.class).to(GitSCMService.class);
    bind(MountService.class).to(DefaultMountService.class);

    bind(DistributionBootstrappingService.class)
        .toProvider(DistributionBootstrappingServiceProvider.class);
    bind(DistributionConfiguration.class)
        .toProvider(DistributionConfigurationProvider.class)
        .in(Singleton.class);
    bind(PackageManagementService.class).toProvider(PackageManagementServiceProvider.class);
    bind(RunlevelManagementService.class).toProvider(RunlevelManagementServiceProvider.class);
    bind(HostnameService.class).toProvider(HostnameServiceProvider.class);

    // new PropertyImpl(DownloadPath.class, "/downloads");
    //    new PropertyValuePair(DownloadPath.class, "/downloads");

    install(new CompressionModule());
    install(new EncryptionModule());
    install(new DefaultFileStorageModule());
    install(new ShellModule());
    install(new SSHModule());
    install(new GuavaEventBusModule());
    install(new FileStorageModule());
    install(new CommonsIODownloadModule());
  }
}
