package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.Module;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.logging.annotation.ContextualLoggable;
import com.walterjwhite.logging.annotation.ContextualLoggableField;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
@RequiredArgsConstructor
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

  /** Called after initialization to ensure instance is fully ready to go. */
  public void onSetup() {}

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
