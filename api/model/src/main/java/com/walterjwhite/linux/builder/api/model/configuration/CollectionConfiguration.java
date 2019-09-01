package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class CollectionConfiguration<Type extends Configurable> implements Configurable {
  protected Set<Type> items = new HashSet<>();

  public boolean isRun() {
    if (items == null || items.isEmpty()) {
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
