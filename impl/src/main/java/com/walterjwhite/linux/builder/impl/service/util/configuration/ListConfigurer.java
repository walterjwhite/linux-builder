package com.walterjwhite.linux.builder.impl.service.util.configuration;

import com.walterjwhite.linux.builder.api.model.configuration.CollectionConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.IOUtil;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ListConfigurer implements Configurer {
  /** ie. read add.packages */
  @Override
  public CollectionConfiguration<StringConfiguration> read(
      final File path, final Class<? extends Configurable> type, final boolean isCollection)
      throws IOException {
    final File[] files = path.listFiles();
    if (files == null || files.length == 0) return (null);

    Arrays.sort(files);

    final CollectionConfiguration<StringConfiguration> listConfiguration =
        new CollectionConfiguration<>();

    for (final File file : files) {
      final List<String> lines = IOUtil.readLines(file.getAbsolutePath());

      if (lines != null && lines.size() > 0) {
        for (final String line : lines) {
          listConfiguration.getItems().add(new StringConfiguration(line));
        }
      }
    }

    return (listConfiguration);
  }
}
