package com.walterjwhite.linux.builder.impl.service.util.configuration;

import com.walterjwhite.linux.builder.api.model.configuration.CollectionConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import java.io.*;
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
    final CollectionConfiguration collectionConfiguration = new CollectionConfiguration();

    for (final File child : path.listFiles()) {
      loadItem(collectionConfiguration, child, type);
    }

    return (collectionConfiguration);
  }

  protected Configurable loadItem(
      final CollectionConfiguration collectionConfiguration,
      final File child,
      Class<? extends Configurable> type)
      throws FileNotFoundException {
    final Configurable childElement = load(child, type);
    if (childElement != null) {
      collectionConfiguration.getItems().add(childElement);
      return (childElement);
    }

    return (null);
  }

  protected Configurable load(final File path, Class<? extends Configurable> type)
      throws FileNotFoundException {
    return (yaml.loadAs(new BufferedInputStream(new FileInputStream(path)), type));
  }
}
