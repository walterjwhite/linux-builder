package com.walterjwhite.linux.builder.impl.service.util.configuration;

import com.walterjwhite.linux.builder.api.model.configuration.CollectionConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.yaml.snakeyaml.Yaml;

public class YamlConfigurer implements Configurer {
  protected final Yaml yaml = new Yaml();

  /**
   * ie. read users directory: users files: walterjwhite, jingyi directory: groups files: ssh,
   * remote
   */
  public Configurable read(
      final File path, Class<? extends Configurable> type, final boolean isCollection)
      throws IOException {
    if (isCollection) {
      return (loadAll(path, type));
    }

    return (load(path, type));
  }

  protected CollectionConfiguration loadAll(final File path, Class<? extends Configurable> type)
      throws FileNotFoundException {
    final CollectionConfiguration collection = new CollectionConfiguration();

    for (final File child : path.listFiles()) {
      loadItem(collection, child, type);
    }

    return (collection);
  }

  protected Configurable loadItem(
      final CollectionConfiguration collection,
      final File child,
      Class<? extends Configurable> type)
      throws FileNotFoundException {
    final Configurable childElement = load(child, type);
    if (childElement != null) {
      collection.getItems().add(childElement);
      return (childElement);
    }

    return (null);
  }

  protected Configurable load(final File path, Class<? extends Configurable> type)
      throws FileNotFoundException {
    return (yaml.loadAs(new FileInputStream(path), type));
  }
}
