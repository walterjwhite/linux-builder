package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.CollectionConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCollectionModule<Type extends Configurable>
    extends AbstractModule<CollectionConfiguration<Type>> {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCollectionModule.class);

  protected AbstractCollectionModule(
      BuildService buildService,
      final BuildConfiguration buildConfiguration,
      final DistributionConfiguration distributionConfiguration) {
    super(buildService, buildConfiguration, distributionConfiguration);
  }

  protected void doRun() {
    for (Type item : configuration.getItems()) {
      try {
        doRun(item);
      } catch (Exception e) {
        LOGGER.warn("error running module:", e);
      }
    }
  }

  protected abstract void doRun(Type item) throws Exception;
}
