package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Runlevel;
import com.walterjwhite.linux.builder.api.model.enumeration.RunlevelAction;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.RunlevelManagementService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import javax.inject.Inject;

@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = YamlConfigurer.class,
  configurationClass = Runlevel.class
)
public class RunlevelModule extends AbstractCollectionModule<Runlevel> {
  protected final RunlevelManagementService runlevelManagementService;

  @Inject
  public RunlevelModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      RunlevelManagementService runlevelManagementService) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.runlevelManagementService = runlevelManagementService;
  }

  //  public void onSetup() {
  //    runlevelManagementService =
  //        GuiceHelper.getGuiceInjector().getInstance(get(distributionConfiguration));
  //    runlevelManagementService.setRootDirectory(buildConfiguration.getRootDirectory());
  //  }

  @Override
  public void document() {}

  // TODO: refactor this without if/else if
  protected void doRun(final Runlevel runlevel) throws Exception {
    if (RunlevelAction.Add.equals(runlevel.getRunlevelAction())) {
      runlevelManagementService.add(runlevel);
    } else if (RunlevelAction.Remove.equals(runlevel.getRunlevelAction())) {
      runlevelManagementService.remove(runlevel);
    }
  }

  //  protected RunlevelManagementService get(
  //      DistributionConfiguration distributionConfiguration, final String rootDirectory)
  //      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
  //          InstantiationException {
  //    return
  // (get(distributionConfiguration).getConstructor(String.class).newInstance(rootDirectory));
  //  }

  protected Class<? extends RunlevelManagementService> get(
      DistributionConfiguration distributionConfiguration) {
    if (distributionConfiguration.getRunlevelManagementService() != null) {
      return (distributionConfiguration.getRunlevelManagementService());
    }

    return (get(distributionConfiguration.getParent()));
  }
}
