package com.walterjwhite.linux.builder.impl.service.hostname;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.HostnameService;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

public class FreeBSDHostnameService implements HostnameService {
  public static final String RC_CONFIGURATION_FILE = "/etc/rc.conf";

  protected final String rootDirectory;

  @Inject
  public FreeBSDHostnameService(final BuildConfiguration buildConfiguration) {
    super();

    this.rootDirectory = buildConfiguration.getRootDirectory();
  }

  @Override
  public void set(String hostname) throws IOException {
    final File targetFile = getTargetFile();
    final List<String> rcContents = FileUtils.readLines(getTargetFile(), Charset.defaultCharset());
    final List<String> newRCContents = new ArrayList<>();

    for (final String rcLine : rcContents) {
      if (rcLine.startsWith("hostname")) {
        newRCContents.add("hostname=" + hostname);
      } else newRCContents.add(rcLine);
    }

    FileUtils.writeLines(targetFile, newRCContents /*, Charset.defaultCharset()*/);
  }

  protected File getTargetFile() {
    return (new File(rootDirectory + RC_CONFIGURATION_FILE));
  }
}
