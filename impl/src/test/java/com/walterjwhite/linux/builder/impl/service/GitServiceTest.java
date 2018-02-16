package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class GitServiceTest {
  public static void main(final String[] arguments) throws Exception {
    final BuildConfiguration buildConfiguration =
        new BuildConfiguration(
            Distribution.Funtoo, "/tmp/builds/linux", "master", "router", "/projects/active/linux");

    final File repositoryFile = new File(buildConfiguration.getBuildDirectory());
    FileUtils.deleteDirectory(repositoryFile);

    //    final GitSCMService gitService = new GitSCMService();
    // gitService.setBuildConfiguration(buildConfiguration);
    //    gitService.checkout(buildConfiguration);

    // new
    // DefaultSCMTagPatchWriterService(gitService.getLocalRepositoryPath()).write(gitService.getSCMTag());
  }
}
