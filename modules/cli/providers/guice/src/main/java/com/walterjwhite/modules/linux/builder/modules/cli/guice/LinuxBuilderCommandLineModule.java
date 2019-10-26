package com.walterjwhite.modules.linux.builder.modules.cli.guice;

import com.google.inject.AbstractModule;
import com.walterjwhite.datastore.api.repository.CriteriaBuilderModule;
import com.walterjwhite.download.impl.DownloadConfiguration;
import com.walterjwhite.file.modules.tar.TarDirectoryCopierModule;
import com.walterjwhite.infrastructure.inject.providers.guice.GuiceApplicationModule;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.impl.cli.BuildConfigurationProvider;
import com.walterjwhite.linux.builder.impl.cli.DownloadConfigurationProvider;

public class LinuxBuilderCommandLineModule extends AbstractModule
    implements GuiceApplicationModule {
  @Override
  protected void configure() {
    //    bind(AbstractCommandLineHandler.class).to(LinuxBuilderCommandLineHandler.class);
    bind(BuildConfiguration.class).toProvider(BuildConfigurationProvider.class);
    bind(DownloadConfiguration.class).toProvider(DownloadConfigurationProvider.class);

    install(new TarDirectoryCopierModule());
    install(new LinuxBuilderModule());
    install(new CriteriaBuilderModule());
  }
}
