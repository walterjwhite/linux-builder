package com.walterjwhite.linux.builder.impl.service.util;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.commons.io.IOUtils;

public class CopyUtil {
  private CopyUtil() {}

  public static void copy(BuildConfiguration buildConfiguration, final String filename)
      throws IOException {
    final Path source = new File(filename).toPath();
    final Path target = new File(buildConfiguration.getRootDirectory()).toPath();
    copy(source, target);
  }

  public static void copy(final Path source, final Path target) throws IOException {
    copyUsingDirectoryCopier(source, target);
    // copyUsingTarPiped(source, target);
  }

  private static void copyUsingDirectoryCopier(final Path source, final Path target)
      throws IOException {

    DirectoryCopier directoryCopier = new DirectoryCopier(source, target);
    java.nio.file.Files.walkFileTree(source, directoryCopier);
  }

  private static void copyUsingFileUtils() {
    // FileUtils.copyDirectory(new File(filename), new File(buildConfiguration.getRootDirectory()));
  }

  private static void copyUsingTar() {
    // this does NOT work (because of the pipe)
    // ExecutionUtil.run("tar", "cp", "-C", filename, ".", "|", "tar", "xp", "--no-overwrite-dir",
    // "-C", buildConfiguration.getRootDirectory());

    // ExecutionUtil.run("/bin/sh", "-c", "tar -c -p -C " +  filename + " . | tar -x -p
    // --no-overwrite-dir -C " + buildConfiguration.getRootDirectory());
    // final String sshCommand = "/bin/sh -c \"tar cp -C " +  filename + " . | tar xp
    // --no-overwrite-dir -C " + buildConfiguration.getRootDirectory() + "\"";
    // final String sshCommand = "/bin/sh -c \"tar cp -C " +  filename + " . | tar xp
    // --no-overwrite-dir -C " + buildConfiguration.getRootDirectory() + "\"";
    // final String sshCommand = "/bin/sh -c tar -c -p -C " +  filename + " . | tar -x -p
    // --no-overwrite-dir -C " + buildConfiguration.getRootDirectory();
  }

  private static void copyUsingTarPiped(
      /*BuildConfiguration buildConfiguration, final String filename*/
      final Path source,
      final Path target)
      throws IOException {
    final Process tarCp =
        Runtime.getRuntime().exec(new String[] {"tar", "cp", "-C", source.toString(), "."});
    final Process tarXp =
        Runtime.getRuntime()
            .exec(new String[] {"tar", "xp", "--no-overwrite-dir", "-C", target.toString()});

    IOUtils.copy(tarCp.getInputStream(), tarXp.getOutputStream());
    final int returnCode;
    try {
      returnCode = tarCp.waitFor();
      if (returnCode > 0) {
        throw (new IllegalStateException(
            "Error copying files, non-zero return-code:" + returnCode));
      }
    } catch (InterruptedException e) {
      throw (new RuntimeException("Error waiting for result"));
    }

    // tarXp.waitFor();
    tarXp.getOutputStream().flush();
    tarXp.getOutputStream().close();

    tarCp.getInputStream().close();
  }
}
