package com.walterjwhite.linux.builder.impl.service.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryCopier implements FileVisitor<Path> {
  private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryCopier.class);

  private final Path source;
  private final Path target;

  // TODO: pass this in as a parameter
  private boolean validateCopiedFiles = false;

  private Set<Path> existingPaths;

  public DirectoryCopier(Path source, Path target) {
    super();

    this.source = source;
    this.target = target;

    this.existingPaths = new HashSet<>();
  }

  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    Path newdir = getTarget(dir);

    if (newdir.toFile().exists()) {
      LOGGER.info("target directory already exists, continuuing");
      existingPaths.add(newdir);
      return FileVisitResult.CONTINUE;
    }

    try {
      Files.copy(
          dir, newdir, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
    } catch (FileAlreadyExistsException x) {
      LOGGER.debug("file already exists.", x);
      // ignore
    } catch (DirectoryNotEmptyException e) {
      LOGGER.warn("Unable to create:" + newdir, e);
    } catch (IOException e) {
      LOGGER.warn("Unable to create:" + newdir, e);
    }

    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    final Path targetFile = getTarget(file);

    if (Files.isSymbolicLink(file)) {
      copySymlink(file, targetFile);
    } else {
      copyFile(file, targetFile);
    }

    Files.setPosixFilePermissions(targetFile, Files.getPosixFilePermissions(file));

    return FileVisitResult.CONTINUE;
  }

  protected void copySymlink(Path file, Path targetFile) throws IOException {
    // delete the target first
    if (targetFile.toFile().isDirectory()) {
      FileUtils.deleteDirectory(targetFile.toFile());
    } else {
      targetFile.toFile().delete();
    }

    try {
      Files.createLink(targetFile, file);
    } catch (IOException x) {
      LOGGER.error("error creating symlink", x);
    } catch (UnsupportedOperationException x) {
      LOGGER.error("error creating symlink", x);
    }
  }

  protected void copyFile(Path file, Path targetFile) throws IOException {
    Files.copy(
        file, targetFile, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
    // validateCopiedFiles(file, targetFile);
  }

  //  protected void validateCopiedFiles(Path file, Path targetFile) throws IOException {
  //    if (!validateCopiedFiles) return;
  //
  //      final String sourceChecksum = ChecksumUtil.sha256sum(file.toFile());
  //      final String targetChecksum = ChecksumUtil.sha256sum(targetFile.toFile());
  //
  //      if (!sourceChecksum.equals(targetChecksum)) {
  //        throw new IllegalStateException(
  //            "source checksum: " + sourceChecksum + " does not match (target): " +
  // targetChecksum);
  //      }
  //    }
  //  }

  protected Path getTarget(final Path file) {
    return (target.resolve(source.relativize(file)));
  }

  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
    // fix up modification time of directory when done
    Path newdir = getTarget(dir);
    try {
      Files.setLastModifiedTime(newdir, Files.getLastModifiedTime(dir));

      // do NOT overwrite directory permissions
      if (!existingPaths.contains(newdir)) {
        Files.setPosixFilePermissions(newdir, Files.getPosixFilePermissions(dir));
      }
    } catch (IOException x) {
      LOGGER.error("Unable to copy all attributes to: %s: %s%n", newdir, x);
    }

    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    if (exc instanceof FileSystemLoopException) {
      LOGGER.error("cycle detected: " + file);
    } else {
      LOGGER.error("Unable to copy: %s: %s%n", file, exc);
    }
    return FileVisitResult.CONTINUE;
  }
}
