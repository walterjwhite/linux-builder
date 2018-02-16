package com.walterjwhite.linux.builder.impl.service.util;

import java.util.Collection;

public class PortageUtil {
  private PortageUtil() {
    super();
  }

  public static void append(final StringBuilder buffer, final String key, final String value) {
    append(buffer, key, value, false);
  }

  public static void append(
      final StringBuilder buffer, final String key, final String value, final boolean isAppend) {
    if (value != null && !value.isEmpty()) {
      buffer.append(key + "=\"");
      if (isAppend) {
        buffer.append("${" + key + "} ");
      }

      buffer.append(value + "\"\n");
    }
  }

  public static String get(final Collection<String> values) {
    return (String.join(" ", values.toArray(new String[values.size()])));
  }
}
