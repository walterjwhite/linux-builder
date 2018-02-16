package com.walterjwhite.linux.builder.impl.service.util.configuration;

import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import java.io.File;

public interface Configurer {
  Configurable read(
      final File moduleConfigurationFile,
      final Class<? extends Configurable> type,
      final boolean isCollection)
      throws Exception;
}
