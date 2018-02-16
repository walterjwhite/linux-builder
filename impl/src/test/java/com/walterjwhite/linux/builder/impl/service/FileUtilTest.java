package com.walterjwhite.linux.builder.impl.service;

import java.io.File;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtilTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(FileUtilTest.class);

  public static void main(final String[] arguments) {
    final Path source =
        new File(
                "/builds/linux/linux-master/patches/networking/security/block-traffic-to-domains.patch/build/2.files")
            .toPath();
    // final Path source = new
    // File("/mnt/work/linux/linux-repo/patches/networking/security/block-traffic-to-domains.patch/build/2.files").toPath();
    LOGGER.debug("source:" + source);
    final Path target = new File("/builds/linux/master/workstation/root").toPath();
    LOGGER.debug("target:" + target);

    final Path file =
        new File(
                "/builds/linux/linux-master/patches/networking/security/block-traffic-to-domains.patch/build/2.files/usr/sbin/unbound-block-domains-update-network")
            .toPath();
    // final Path file = new
    // File("/mnt/work/linux/linux-repo/patches/networking/security/block-traffic-to-domains.patch/build/2.files/usr/sbin/unbound-block-domains-update-network").toPath();

    LOGGER.debug("target:" + target.resolve(source.relativize(file)));
  }
}
