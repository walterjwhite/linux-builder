package com.walterjwhite.linux.builder.impl.service;

import java.io.File;
import java.nio.file.Path;

public class FileUtilTest {

  public static void main(final String[] arguments) {
    final Path source = getSource();

    // final Path source = new
    // File("/mnt/work/linux/linux-repo/patches/networking/security/block-traffic-to-domains.patch/build/2.files").toPath();
    final Path target = getTarget();

    final Path file =
        new File(
                "/builds/linux/linux-master/patches/networking/security/block-traffic-to-domains.patch/build/2.files/usr/sbin/unbound-block-domains-update-network")
            .toPath();
    // final Path file = new
    // File("/mnt/work/linux/linux-repo/patches/networking/security/block-traffic-to-domains.patch/build/2.files/usr/sbin/unbound-block-domains-update-network").toPath();

    LOGGER.debug("target:" + target.resolve(source.relativize(file)));
  }

  private static Path getSource() {
    return new File(
            "/builds/linux/linux-master/patches/networking/security/block-traffic-to-domains.patch/build/2.files")
        .toPath();
  }

  private static Path getTarget() {
    return new File("/builds/linux/master/workstation/root").toPath();
  }
}
