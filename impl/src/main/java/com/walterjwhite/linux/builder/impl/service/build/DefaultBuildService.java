package com.walterjwhite.linux.builder.impl.service.build;

import com.google.common.eventbus.EventBus;
import com.walterjwhite.interruptable.Interruptable;
import com.walterjwhite.interruptable.annotation.InterruptableService;
import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.patch.Patch;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.DistributionBootstrappingService;
import com.walterjwhite.linux.builder.api.service.Module;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.DependencyManagementUtil;
import com.walterjwhite.linux.builder.impl.service.util.module.ModuleFactory;
import com.walterjwhite.shell.api.enumeration.MountAction;
import com.walterjwhite.shell.api.model.MountCommand;
import com.walterjwhite.shell.api.model.MountPoint;
import com.walterjwhite.shell.api.service.MountService;
import java.io.File;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@InterruptableService
public class DefaultBuildService implements BuildService, Interruptable {
  protected final ModuleFactory moduleFactory;

  protected final EventBus eventBus;

  protected final MountService mountService;

  protected final DistributionBootstrappingService distributionBootstrappingService;

  protected final BuildConfiguration buildConfiguration;

  protected final DistributionConfiguration distributionConfiguration;

  protected boolean clean = true;

  @Inject
  public DefaultBuildService(
      ModuleFactory moduleFactory,
      EventBus eventBus,
      MountService mountService,
      DistributionBootstrappingService distributionBootstrappingService,
      final BuildConfiguration buildConfiguration,
      final DistributionConfiguration distributionConfiguration) {
    super();
    this.moduleFactory = moduleFactory;
    this.eventBus = eventBus;
    this.mountService = mountService;
    this.distributionBootstrappingService = distributionBootstrappingService;

    this.buildConfiguration = buildConfiguration;
    this.distributionConfiguration = distributionConfiguration;
  }

  protected void onSetup() throws IllegalAccessException, InstantiationException {
    System.setProperty("~/", buildConfiguration.getBuildDirectory());

    //    DistributionBootstrappingService bootstrappingService =
    // ApplicationHelper.getApplicationInstance().getInjector().getInstance(distributionConfiguration.getImplementingBootstrappingServiceClass());
    //        bootstrappingService.onSetup(buildConfiguration);
  }

  public void onShutdown() throws Exception {
    // TODO: kill all running processes, especially shell execution
    unmount();
  }

  @Override
  public void build() throws Exception {
    onSetup();

    // single patch
    if (buildConfiguration.getSinglePatchName() != null) {
      doSinglePatch();
    } else {
      doAllPatches();
    }
  }

  protected void doSinglePatch() throws Exception {
    if (!buildConfiguration.isOnLiveHost()) {
      try {
        mount();

        doPatch(buildConfiguration.getSinglePatchName());
      } finally {
        onShutdown();
      }
    } else {
      doPatch(buildConfiguration.getSinglePatchName());
    }
  }

  protected void doAllPatches() throws Exception {
    distributionBootstrappingService.doBootstrap();

    doSystem();

    try {
      mount();
      doPatches();
    } finally {
      onShutdown();
    }
  }

  protected void doSystem() throws Exception {
    runPatch(
        new Patch(
            "system",
            buildConfiguration.getScmConfiguration().getWorkspacePath()
                + File.separator
                + "systems"
                + File.separator
                + buildConfiguration.getVariant()
                + File.separator
                + "system.patch",
            false),
        BuildPhase.Setup);
  }

  protected void doPatch(final String patchName) throws Exception {
    Patch patch =
        DependencyManagementUtil.getPatchByName(
            buildConfiguration.getScmConfiguration().getWorkspacePath(),
            buildConfiguration.getVariant(),
            patchName);
    doPatches(patch);
  }

  protected void doPatches() throws Exception {
    final List<Patch> patches =
        DependencyManagementUtil.getOrderedSystemPatches(
            buildConfiguration.getScmConfiguration().getWorkspacePath(),
            buildConfiguration.getVariant());

    doPatches(patches.toArray(new Patch[patches.size()]));
  }

  protected void doPatches(final Patch... patches) throws Exception {
    for (BuildPhase buildPhase : BuildPhase.values()) {
      distributionBootstrappingService.doPreBuild(buildPhase);

      runBuildPhase(buildPhase, patches);

      distributionBootstrappingService.doPostBuild(buildPhase);
    }
  }

  protected void runBuildPhase(final BuildPhase buildPhase, final Patch... patches)
      throws Exception {
    for (final Patch patch : patches) {
      runPatch(patch, buildPhase);
    }
  }

  public void runPatch(final Patch patch, final BuildPhase buildPhase) throws Exception {
    runPatchMain(patch, buildPhase);
    runPatchVariant(patch, buildPhase);
  }

  protected void runPatchMain(final Patch patch, final BuildPhase buildPhase) throws Exception {
    runPatchModules(moduleFactory.produceModules(this, patch, buildPhase));
  }

  protected boolean runPatchVariant(final Patch patch, final BuildPhase buildPhase)
      throws Exception {
    // run variants ...
    if (patch.isDoesVariantExist()) {
      runPatchModules(
          moduleFactory.produceModules(this, patch, buildPhase, buildConfiguration.getVariant()));
      return (true);
    }

    return (false);
  }

  protected void runPatchModules(final List<Module> modules) throws Exception {
    if (modules != null) {
      for (Module module : modules) {
        runModule(module);
      }
    }
  }

  protected void runModule(Module module) throws Exception {
    if (module != null && module.isRun()) {
      module.document();
      module.run();
    }
  }

  protected void mount() throws Exception {
    mount(
        //        applicationEventPublisher,
        buildConfiguration.getRootDirectory(), distributionConfiguration);

    clean = false;
  }

  protected void mount(
      //      EventBus eventBus,
      final String rootDirectory,
      final DistributionConfiguration distributionConfiguration)
      throws Exception {
    for (final MountPoint mountPoint : distributionConfiguration.getMountPoints())
      mountService.execute(
          new MountCommand()
              .withRootPath(rootDirectory)
              .withMountAction(MountAction.Mount)
              .withMountPoint(mountPoint));

    if (distributionConfiguration.getParent() != null) {
      mount(/*applicationEventPublisher,*/ rootDirectory, distributionConfiguration.getParent());
    }
  }

  protected void unmount() throws Exception {
    if (!clean)
      unmount(
          //        applicationEventPublisher,
          buildConfiguration.getRootDirectory(), distributionConfiguration);

    clean = true;
  }

  protected void unmount(
      //      EventBus eventBus,
      final String rootDirectory,
      final DistributionConfiguration distributionConfiguration)
      throws Exception {
    for (final MountPoint mountPoint : distributionConfiguration.getUmountPoints())
      mountService.execute(
          new MountCommand()
              .withRootPath(rootDirectory)
              .withMountAction(MountAction.Unmount)
              .withMountPoint(mountPoint));

    if (distributionConfiguration.getParent() != null) {
      unmount(/*applicationEventPublisher,*/ rootDirectory, distributionConfiguration.getParent());
    }
  }

  @Override
  public void interrupt() {
    try {
      unmount();
    } catch (Exception e) {
      throw new RuntimeException("Error unmounting", e);
    }
  }
}
