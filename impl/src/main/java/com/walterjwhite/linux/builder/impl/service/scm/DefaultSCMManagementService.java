package com.walterjwhite.linux.builder.impl.service.scm;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.ssh.api.SCMService;
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
    scmService.checkout(buildConfiguration.getScmConfiguration());

    new DefaultSCMTagPatchWriterService(buildConfiguration.getScmConfiguration().getWorkspacePath())
        .write(
            buildConfiguration,
            buildConfiguration
                .getScmConfiguration()
                .getTag() /*scmService.getSCMTag(buildConfiguration.getScmConfiguration())*/);
  }

  protected void prepareLocalWorkspace() throws IOException {
    // TODO: set in build configuration provider
    //    buildConfiguration.setLocalWorkspace(
    //        buildConfiguration.getBuildDirectory()
    //            + File.separator
    //            + "linux-"
    //            + buildConfiguration.getTag()
    //            + "-"
    //            + buildConfiguration.getVariant());

    final File localRepositoryFile =
        new File(buildConfiguration.getScmConfiguration().getWorkspacePath());
    if (localRepositoryFile.exists()) {
      FileUtils.deleteDirectory(localRepositoryFile);
    }

    localRepositoryFile.mkdirs();
  }
}
