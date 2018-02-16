package com.walterjwhite.linux.builder.impl.cli;

import com.walterjwhite.google.guice.property.property.Property;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
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
      @Property(com.walterjwhite.linux.builder.impl.cli.property.Source.class) String source) {
    super();
    buildConfiguration = new BuildConfiguration(distribution, buildDirectory, tag, variant, source);
  }

  @Override
  public BuildConfiguration get() {
    return buildConfiguration;
  }
}
