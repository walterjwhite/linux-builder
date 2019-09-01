package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.CollectionConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;

public abstract class AbstractCollectionModule<Type extends Configurable>
    extends AbstractModule<CollectionConfiguration<Type>> {
  protected AbstractCollectionModule(
      BuildService buildService,
      final BuildConfiguration buildConfiguration,
      final DistributionConfiguration distributionConfiguration) {
    super(buildService, buildConfiguration, distributionConfiguration);
  }

  protected void doRun() throws Exception {
    configuration
        .getItems()
        .forEach(
            i -> {
              try {
                doRun(i);
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            });
  }

  protected abstract void doRun(Type item) throws Exception;
}
