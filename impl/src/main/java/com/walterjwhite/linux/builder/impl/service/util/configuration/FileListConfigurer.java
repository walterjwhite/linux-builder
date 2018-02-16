package com.walterjwhite.linux.builder.impl.service.util.configuration;

import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.api.model.configuration.FilelistConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

public class FileListConfigurer implements Configurer {
  /**
   * ie. read files to process for copying this really is only for documentation purposes as Apache
   * Commons IO can copy a directory
   */
  public FilelistConfiguration read(
      final File path, final Class<? extends Configurable> type, final boolean isCollection)
      throws IOException {
    final FilelistConfiguration fileListConfiguration = new FilelistConfiguration();

    // no file filter, no dir filter
    final Collection<File> files =
        FileUtils.listFiles(path, new FileListFilter(), new FileListFilter());
    for (File file : files) {
      fileListConfiguration.getFilenames().add(file.getAbsolutePath());
    }

    return (fileListConfiguration);
  }

  public class FileListFilter implements IOFileFilter {
    @Override
    public boolean accept(File file) {
      return true;
    }

    @Override
    public boolean accept(File dir, String name) {
      return true;
    }
  }
}
