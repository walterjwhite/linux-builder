package com.walterjwhite.linux.builder.impl.service.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

public class IOUtil {
  //    protected transient Yaml yaml = new Yaml(new MessageRepresenter(), new DumperOptions());

  private static final Yaml YAML = new Yaml();

  private IOUtil() {}

  public static List<String> readLines(final String filename) throws IOException {
    final List<String> content = new ArrayList<>();

    for (final String line : FileUtils.readLines(new File(filename), Charset.defaultCharset())) {
      if (line != null && (!line.startsWith("#") && !line.isEmpty() && line.trim().length() > 0)) {
        content.add(line.trim());
      }
    }

    return (content);
  }

  public static Object read(final String filename /*, final Class clazz*/)
      throws FileNotFoundException {
    return YAML.load(new BufferedInputStream(new FileInputStream(filename)));
  }

  public static String write(final Object object) {
    return YAML.dump(object);
  }
}
