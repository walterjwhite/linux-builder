package com.walterjwhite.linux.builder.impl.service;

import java.io.IOException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuntimeTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeTest.class);

  @Test
  public void testRuntime() throws IOException, InterruptedException {
    final Process process = Runtime.getRuntime().exec("ls");
    LOGGER.debug("exit code:" + process.waitFor());

    final Process chrootProcess =
        Runtime.getRuntime().exec("/bin/chroot /builds/linux/master/workstation/root /bin/bash");
    LOGGER.debug("exit code:" + chrootProcess.waitFor());
  }
}
