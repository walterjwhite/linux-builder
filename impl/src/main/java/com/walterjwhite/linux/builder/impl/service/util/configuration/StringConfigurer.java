package com.walterjwhite.linux.builder.impl.service.util.configuration;

import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.IOUtil;
import java.io.File;
import java.io.IOException;

public class StringConfigurer implements Configurer {
  /** ie. read add.packages */
  public StringConfiguration read(
      final File path, final Class<? extends Configurable> type, final boolean isCollection)
      throws IOException {
    final StringBuilder buffer = new StringBuilder();
    for (final String line : IOUtil.readLines(path.getAbsolutePath())) {
      buffer.append(line + "\n");
    }

    return (new StringConfiguration(buffer.toString()));
  }
}
