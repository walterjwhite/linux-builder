package com.walterjwhite.linux.builder.impl.service.runlevel;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Runlevel;
import com.walterjwhite.linux.builder.api.service.RunlevelManagementService;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

public class FreeBSDRunlevelManagementService implements RunlevelManagementService {
  public static final String RC_CONFIGURATION_FILE = "/etc/rc.conf";

  protected final ShellExecutionService shellExecutionService;
  protected final ShellCommandBuilder shellCommandBuilder;

  protected final String rootDirectory;

  @Inject
  public FreeBSDRunlevelManagementService(
      final ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder,
      final BuildConfiguration buildConfiguration) {
    super();

    this.shellExecutionService = shellExecutionService;
    this.shellCommandBuilder = shellCommandBuilder;
    this.rootDirectory = buildConfiguration.getRootDirectory();
  }

  @Override
  public void add(Runlevel runlevel) throws Exception {
    final File targetFile = getTargetFile();
    final List<String> lines = FileUtils.readLines(targetFile, Charset.defaultCharset());
    final List<String> newLines = new ArrayList<>();
    final String serviceName = getServiceName(runlevel.getServiceName());
    final String serviceLine = serviceName + "_enable=\"YES\"";

    boolean contains = false;

    for (final String line : lines) {
      if (line.startsWith(serviceLine)) {
        contains = true;
      }

      newLines.add(line);
    }

    if (!contains) {
      newLines.add(serviceLine);
    }

    FileUtils.writeLines(targetFile, newLines);
  }

  protected File getTargetFile() {
    return new File(rootDirectory + File.separator + RC_CONFIGURATION_FILE);
  }

  protected String getServiceName(final String inputServiceName) {
    return inputServiceName.replace("-", "_");
  }

  @Override
  public void remove(Runlevel runlevel) throws Exception {
    final File targetFile = getTargetFile();
    final List<String> lines = FileUtils.readLines(targetFile, Charset.defaultCharset());
    final List<String> newLines = new ArrayList<>();
    final String serviceName = getServiceName(runlevel.getServiceName());
    final String serviceLine = serviceName + "_enable=\"YES\"";

    for (final String line : lines) {
      if (!line.startsWith(serviceLine)) {
        newLines.add(line);
      }
    }

    FileUtils.writeLines(targetFile, newLines);
  }
}
