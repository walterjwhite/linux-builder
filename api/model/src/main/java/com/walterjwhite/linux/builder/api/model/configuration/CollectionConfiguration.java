package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.LinkedHashSet;

public class CollectionConfiguration<Type extends Configurable> implements Configurable {
  protected LinkedHashSet<Type> items;

  public CollectionConfiguration(LinkedHashSet<Type> items) {
    this();
    this.items.addAll(items);
  }

  public CollectionConfiguration() {
    super();

    items = new LinkedHashSet<>();
  }

  public LinkedHashSet<Type> getItems() {
    return items;
  }

  public void setItems(LinkedHashSet<Type> items) {
    this.items = items;
  }

  public boolean isRun() {
    if (items == null || items.size() == 0) {
      return (false);
    }

    for (Type value : items) {
      if (value != null && value.isRun()) {
        return (true);
      }
    }

    return (false);
  }
}
