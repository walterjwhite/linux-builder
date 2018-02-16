package com.walterjwhite.linux.builder.impl.service.scm;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.SCMManagementService;
import com.walterjwhite.linux.builder.api.service.SCMService;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

public class DefaultSCMManagementService implements SCMManagementService {
  protected final SCMService scmService;
  protected final BuildConfiguration buildConfiguration;

  @Inject
  public DefaultSCMManagementService(SCMService scmService, BuildConfiguration buildConfiguration) {
    super();
    this.scmService = scmService;
    this.buildConfiguration = buildConfiguration;
  }

  @Override
  public void prepare() throws Exception {
    prepareLocalWorkspace();
    scmService.checkout(buildConfiguration);
    new DefaultSCMTagPatchWriterService(buildConfiguration.getLocalWorkspace())
        .write(scmService.getSCMTag(buildConfiguration));
  }

  protected void prepareLocalWorkspace() throws IOException {
    buildConfiguration.setLocalWorkspace(
        buildConfiguration.getBuildDirectory()
            + File.separator
            + "linux-"
            + buildConfiguration.getTag()
            + "-"
            + buildConfiguration.getVariant());

    final File localRepositoryFile = new File(buildConfiguration.getLocalWorkspace());
    if (localRepositoryFile.exists()) {
      FileUtils.deleteDirectory(localRepositoryFile);
    }

    localRepositoryFile.mkdirs();
  }
}
