package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.Module;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.logging.annotation.ContextualLoggable;
import com.walterjwhite.logging.annotation.ContextualLoggableField;

@ContextualLoggable
public abstract class AbstractModule<ConfigurationType extends Configurable>
    implements Module<ConfigurationType> {

  // instance properties
  @ContextualLoggableField protected String patchName;
  protected String filename;
  protected ConfigurationType configuration;
  @ContextualLoggableField protected BuildPhase buildPhase;

  // injectable values
  protected final BuildService buildService;
  protected final BuildConfiguration buildConfiguration;

  protected final DistributionConfiguration distributionConfiguration;

  protected AbstractModule(
      BuildService buildService,
      final BuildConfiguration buildConfiguration,
      final DistributionConfiguration distributionConfiguration) {
    super();
    this.buildService = buildService;
    this.buildConfiguration = buildConfiguration;
    this.distributionConfiguration = distributionConfiguration;
  }

  /** Called after initialization to ensure instance is fully ready to go. */
  public void onSetup() {}

  public void setPatchName(String patchName) {
    this.patchName = patchName;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public void setConfiguration(ConfigurationType configuration) {
    this.configuration = configuration;
  }

  public void setBuildPhase(BuildPhase buildPhase) {
    this.buildPhase = buildPhase;
  }

  public BuildConfiguration getBuildConfiguration() {
    return buildConfiguration;
  }

  public DistributionConfiguration getDistributionConfiguration() {
    return distributionConfiguration;
  }

  public BuildService getBuildService() {
    return buildService;
  }

  public String getFilename() {
    return filename;
  }

  public ConfigurationType getConfiguration() {
    return configuration;
  }

  public BuildPhase getBuildPhase() {
    return buildPhase;
  }

  public String getPatchName() {
    return patchName;
  }

  public boolean isRun() {
    return (configuration != null && configuration.isRun());
  }

  @Override
  public void run() throws Exception {
    try {
      prepare();

      doRun();
    } finally {
      cleanup();
    }
  }

  protected abstract void doRun() throws Exception;

  protected void prepare() throws Exception {}

  protected void cleanup() throws Exception {}
}
