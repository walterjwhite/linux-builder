package com.walterjwhite.linux.builder.impl.cli;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import com.walterjwhite.linux.builder.impl.cli.property.RepositoryUri;
import com.walterjwhite.linux.builder.impl.service.PackageFormat;
import com.walterjwhite.property.impl.annotation.Property;
import com.walterjwhite.ssh.api.model.SCMConfiguration;
import com.walterjwhite.ssh.api.model.SCMTag;
import java.io.File;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class BuildConfigurationProvider implements Provider<BuildConfiguration> {
  protected final BuildConfiguration buildConfiguration;

  @Inject
  public BuildConfigurationProvider(
      @Property(com.walterjwhite.linux.builder.impl.cli.property.Distribution.class)
          Distribution distribution,
      @Property(com.walterjwhite.linux.builder.impl.cli.property.BuildDirectory.class)
          String buildDirectory,
      @Property(com.walterjwhite.linux.builder.impl.cli.property.Tag.class) String tag,
      @Property(com.walterjwhite.linux.builder.impl.cli.property.Variant.class) String variant,
      @Property(RepositoryUri.class) String repositoryUri,
      @Property(PackageFormat.class) PackageFormat packageFormat) {
    super();

    final SCMConfiguration scmConfiguration = new SCMConfiguration();
    scmConfiguration.setWorkspacePath(getLocalWorkspaceDirectory(buildDirectory, tag, variant));
    // this will be replaced later once the project is checked out
    // it is required for the system to initialize
    scmConfiguration.setTag(SCMTag.builder().tag(tag).build());
    scmConfiguration.setRepositoryUri(repositoryUri);

    buildConfiguration =
        BuildConfiguration.builder()
            .distribution(distribution)
            .buildDirectory(buildDirectory)
            .buildDate(LocalDateTime.now())
            .scmConfiguration(scmConfiguration)
            .variant(variant)
            .packageFormat(packageFormat.getExtension())
            .build();
  }

  // buildDirectory/<tag>/<variant>
  protected String getLocalWorkspaceDirectory(String buildDirectory, String tag, String variant) {
    return buildDirectory + File.separator + "linux-" + tag + "-" + variant;
  }

  @Override
  public BuildConfiguration get() {
    return buildConfiguration;
  }
}
