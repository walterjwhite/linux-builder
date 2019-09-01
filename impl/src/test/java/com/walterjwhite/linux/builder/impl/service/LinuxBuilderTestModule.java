package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.compression.modules.CompressionModule;
import com.walterjwhite.datastore.GoogleGuicePersistModule;
import com.walterjwhite.datastore.api.repository.CriteriaBuilderModule;
import com.walterjwhite.encryption.impl.EncryptionModule;
import com.walterjwhite.file.impl.service.DefaultFileStorageModule;
import com.walterjwhite.file.providers.local.service.FileStorageModule;
import com.walterjwhite.google.guice.GuavaEventBusModule;
import com.walterjwhite.google.guice.property.test.GuiceTestModule;
import com.walterjwhite.google.guice.property.test.PropertyValuePair;
import com.walterjwhite.scm.providers.jgit.SSHModule;
import com.walterjwhite.shell.impl.ShellModule;
import org.reflections.Reflections;

public class LinuxBuilderTestModule extends GuiceTestModule {
  public LinuxBuilderTestModule(Class testClass, PropertyValuePair... propertyValuePairs) {
    super(testClass, propertyValuePairs);
  }

  public LinuxBuilderTestModule(
      Class testClass, Reflections reflections, PropertyValuePair... propertyValuePairs) {
    super(testClass, reflections, propertyValuePairs);
  }

  @Override
  protected void configure() {
    super.configure();

    install(new FileStorageModule());
    install(new GuiceTestModule(getClass()));
    install(new CriteriaBuilderModule());
    install(new GoogleGuicePersistModule(/*propertyManager, reflections*/ ));

    install(new CompressionModule());
    install(new EncryptionModule());

    install(new LinuxBuilderModule());
    install(new SSHModule());
    install(new ShellModule());

    install(new DefaultFileStorageModule());
    install(new GuavaEventBusModule());
  }
}
