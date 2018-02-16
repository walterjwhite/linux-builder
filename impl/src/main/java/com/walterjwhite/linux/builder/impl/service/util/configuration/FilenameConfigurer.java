package com.walterjwhite.linux.builder.impl.service.util.configuration;

import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.api.model.configuration.FilenameConfiguration;
import java.io.File;
import java.io.IOException;

public class FilenameConfigurer implements Configurer {
  /**
   * ie. read files to process for copying this really is only for documentation purposes as Apache
   * Commons IO can copy a directory
   */
  public FilenameConfiguration read(
      final File path, final Class<? extends Configurable> type, final boolean isCollection)
      throws IOException {
    return (new FilenameConfiguration(path.getAbsolutePath()));
  }
}
