package com.walterjwhite.linux.builder.impl.service.util.configuration;

import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.module.AbstractCollectionModule;
import java.io.File;

/**
 * Creates a configuration object from a module and its configuration. Automatically determines the
 * appropriate utility to use to load the configuration.
 */
public class ConfigurationUtil {
  private ConfigurationUtil() {
    super();
  }

  public static Configurable read(final File moduleConfigurationFile, final Class moduleClass)
      throws Exception {
    ModuleSupports moduleSupports =
        (ModuleSupports) moduleClass.getAnnotation(ModuleSupports.class);
    return (moduleSupports
        .configurer()
        .newInstance()
        .read(
            moduleConfigurationFile,
            moduleSupports.configurationClass(),
            isCollection(moduleClass)));
  }

  protected static boolean isCollection(final Class moduleClass) {
    return AbstractCollectionModule.class.isAssignableFrom(moduleClass);
  }
}
