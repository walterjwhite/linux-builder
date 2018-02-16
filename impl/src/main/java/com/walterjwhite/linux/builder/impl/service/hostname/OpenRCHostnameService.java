package com.walterjwhite.linux.builder.impl.service.hostname;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.HostnameService;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

public class OpenRCHostnameService implements HostnameService {
  protected final String rootDirectory;

  @Inject
  public OpenRCHostnameService(final BuildConfiguration buildConfiguration) {
    super();

    this.rootDirectory = buildConfiguration.getRootDirectory();
  }

  @Override
  public void set(String hostname) throws IOException {
    FileUtils.write(getHostnameFile(), "HOSTNAME=" + hostname + "\n", "UTF-8", false);
  }

  protected File getHostnameFile() {
    return (new File(rootDirectory + "/etc/conf.d/hostname"));
  }
}
