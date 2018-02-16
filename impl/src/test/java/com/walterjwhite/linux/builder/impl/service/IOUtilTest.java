package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.linux.builder.impl.service.util.configuration.StringConfigurer;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtilTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(IOUtilTest.class);

  public static void main(final String[] arguments) throws IOException {
    StringConfigurer s = new StringConfigurer();
    LOGGER.info(
        s.read(
                new File(
                    "/mnt/work/linux/linux-repo/patches/X11/browsers/google-chrome.patch/setup/2.package-mask"),
                null,
                false)
            .getContent());
  }
}
