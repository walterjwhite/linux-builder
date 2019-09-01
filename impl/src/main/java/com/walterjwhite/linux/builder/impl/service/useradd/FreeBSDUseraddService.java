package com.walterjwhite.linux.builder.impl.service.useradd;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.User;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

public class FreeBSDUseraddService extends LinuxUseraddService {
  @Inject
  public FreeBSDUseraddService(
      BuildConfiguration buildConfiguration,
      ShellCommandBuilder shellCommandBuilder,
      ShellExecutionService shellExecutionService) {
    super(buildConfiguration, shellCommandBuilder, shellExecutionService);
  }

  public void create(final User user) {
    final List<String> arguments = new ArrayList<>();

    if (!"root".equals(user.getUsername())) {
      try {
        if (isExisting(user.getUsername())) {
          return;
        }
      } catch (IOException e) {
        throw new RuntimeException("Error adding user", e);
      }

      arguments.add("pw");
      arguments.add("useradd");
      arguments.add("-m");

      addArgument(arguments, "-g", user.getGid());

      // FreeBSD does this a bit differently than linux
      // this merely says to use UIDs from this value up
      addArgument(arguments, "-u", user.getUid());
      addArgument(arguments, "-s", user.getShell());
      addArgument(arguments, "-G", user.getGroups());

      //      if (user.isSystem()) {
      //        arguments.add("-r");
      //      }

      File passwordFile = null;
      try {
        passwordFile = writeTemporaryPasswordFile(user);

        // FreeBSD takes a plaintext password, this value needs to be decrypted
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
          // the password may contain special characters which may be interpreted by the shell we're
          // using
          addArgument(arguments, "-H", passwordFile.getAbsolutePath());
        }

        arguments.add(user.getUsername());

        try {
          shellExecutionService.run(
              shellCommandBuilder
                  .buildChroot()
                  .withChrootPath(buildConfiguration.getRootDirectory())
                  .withCommandLine(
                      String.join(" ", arguments.toArray(new String[arguments.size()]))));
        } catch (Exception e) {
          throw new RuntimeException("error adding user", e);
        }
      } catch (IOException e) {
        throw new RuntimeException("Error adding user", e);
      } finally {
        passwordFile.delete();
      }
    }
  }

  protected File writeTemporaryPasswordFile(final User user) throws IOException {
    final File tempFile = File.createTempFile(user.getUsername(), "password");
    FileUtils.writeStringToFile(tempFile, user.getPassword(), Charset.defaultCharset());

    return tempFile;
  }
}
