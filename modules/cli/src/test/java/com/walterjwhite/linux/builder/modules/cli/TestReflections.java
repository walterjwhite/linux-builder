package com.walterjwhite.linux.builder.modules.cli;

import com.walterjwhite.google.guice.cli.AbstractCommandLineModule;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

public class TestReflections {

  @Test
  public void testReflections() {
    Reflections reflections =
        new Reflections(
            "com.walterjwhite",
            TypeAnnotationsScanner.class,
            SubTypesScanner.class,
            FieldAnnotationsScanner.class);

    for (final Class type : reflections.getSubTypesOf(AbstractCommandLineModule.class)) {
      System.out.println("subtypes:" + type);
    }
  }
}
