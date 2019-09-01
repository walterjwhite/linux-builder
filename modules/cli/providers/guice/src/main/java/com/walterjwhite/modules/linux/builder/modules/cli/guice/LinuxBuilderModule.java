package com.walterjwhite.modules.linux.builder.modules.cli.guice;

import com.google.inject.AbstractModule;
import com.walterjwhite.encryption.modules.guice.EncryptionModule;
import com.walterjwhite.linux.builder.api.service.*;
import com.walterjwhite.linux.builder.impl.service.build.DefaultBuildService;
import com.walterjwhite.linux.builder.impl.service.documentation.TexDocumentationService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.installation.UnixHostInstallationService;
import com.walterjwhite.linux.builder.impl.service.packaging.SquashfsPackagingService;
import com.walterjwhite.linux.builder.impl.service.provider.*;
import com.walterjwhite.linux.builder.impl.service.scm.DefaultSCMManagementService;
import com.walterjwhite.linux.builder.impl.service.scm.SCMManagementService;
import com.walterjwhite.modules.compression.xz.guice.XZCompressionModule;
import com.walterjwhite.modules.download.commons.guice.CommonsIODownloadModule;
import com.walterjwhite.modules.file.local.guice.LocalFileStorageModule;
import com.walterjwhite.modules.scm.providers.git.cli.guice.GitCLISCMModule;
import com.walterjwhite.modules.shell.guice.SSHModule;
import com.walterjwhite.modules.shell.guice.ShellModule;
import com.walterjwhite.shell.api.service.MountService;
import com.walterjwhite.shell.impl.service.DefaultMountService;
import javax.inject.Singleton;

public class LinuxBuilderModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(BuildService.class).to(DefaultBuildService.class);

    bind(DocumentationService.class).to(TexDocumentationService.class);
    bind(InstallationService.class).to(UnixHostInstallationService.class);
    bind(PackagingService.class).to(SquashfsPackagingService.class);

    bind(MountService.class).to(DefaultMountService.class);
    bind(SCMManagementService.class).to(DefaultSCMManagementService.class);

    bind(DistributionBootstrappingService.class)
        .toProvider(DistributionBootstrappingServiceProvider.class);
    bind(GroupsService.class).toProvider(GroupsServiceProvider.class);
    bind(GroupaddService.class).toProvider(GroupaddServiceProvider.class);
    bind(UseraddService.class).toProvider(UseraddServiceProvider.class);
    bind(DistributionConfiguration.class)
        .toProvider(DistributionConfigurationProvider.class)
        .in(Singleton.class);
    bind(PackageManagementService.class).toProvider(PackageManagementServiceProvider.class);
    bind(RunlevelManagementService.class).toProvider(RunlevelManagementServiceProvider.class);
    bind(HostnameService.class).toProvider(HostnameServiceProvider.class);

    install(new XZCompressionModule());
    install(new EncryptionModule());
    install(new LocalFileStorageModule());
    install(new ShellModule());
    install(new SSHModule());
    install(new GitCLISCMModule());
    //    install(new GuavaEventBusModule());
    install(new LocalFileStorageModule());
    install(new CommonsIODownloadModule());
  }
}
