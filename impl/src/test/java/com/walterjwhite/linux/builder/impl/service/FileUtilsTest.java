package com.walterjwhite.linux.builder.impl.service;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FileUtilsTest {
  public static void main(final String[] arguments) throws IOException {
    FileUtils.write(new File("/tmp/test"), "line1\n", "UTF-8", true);
    FileUtils.write(new File("/tmp/test"), "line2\n", "UTF-8", true);
    FileUtils.write(new File("/tmp/test"), "line3\n", "UTF-8", true);
    FileUtils.write(new File("/tmp/test"), "line4\n", "UTF-8", true);
  }
}
