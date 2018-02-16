package com.walterjwhite.linux.builder.impl.service.util.module;

import com.google.inject.Inject;
import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.api.model.patch.Patch;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.api.service.Module;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.module.AbstractModule;
import com.walterjwhite.linux.builder.impl.service.util.configuration.ConfigurationUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.reflections.Reflections;

public class ModuleFactory {
  protected final BuildConfiguration buildConfiguration;
  protected final DistributionConfiguration distributionConfiguration;
  protected final Set<Class<? extends Module>> allClasses;
  protected final Map<String, Class<? extends Module>> extensionToClassMapping;

  @Inject
  public ModuleFactory(
      BuildConfiguration buildConfiguration, DistributionConfiguration distributionConfiguration) {
    super();
    this.buildConfiguration = buildConfiguration;
    this.distributionConfiguration = distributionConfiguration;
    allClasses = new HashSet<>();
    extensionToClassMapping = new HashMap<>();
    onSetup();
    setupMapping();
  }

  @PostConstruct
  protected void onSetup() {
    Reflections reflections = new Reflections("com.walterjwhite.linux.builder.impl.service.module");
    allClasses.addAll(reflections.getSubTypesOf(Module.class));
  }

  protected void setupMapping() {
    for (final Class<? extends Module> moduleClass : allClasses) {
      extensionToClassMapping.put(getExtension(moduleClass), moduleClass);
    }
  }

  private static List<String> splitByCamelCase(final String className) {
    return (Arrays.asList(className.split("(?<=[a-z0-9])(?=[A-Z])")));
  }

  private static String getExtension(final Class<? extends Module> moduleClass) {
    final List<String> parts = new ArrayList<>();
    for (final String element : splitByCamelCase(moduleClass.getSimpleName())) {
      parts.add(element.toLowerCase());
    }

    parts.remove(parts.size() - 1); // remove "Module"
    return (String.join("-", parts)); // Stage3, Chown
  }

  // Patches are structured: /patch-name.patch/<build-phase>/<step-order>.module
  public List<Module> produceModules(BuildService buildService, Patch patch, BuildPhase buildPhase)
      throws Exception {
    final File buildPhaseFile = getBuildPhaseFile(patch, buildPhase);
    return (produceModules(buildService, patch, buildPhase, buildPhaseFile));
  }

  protected List<Module> produceModules(
      BuildService buildService, Patch patch, BuildPhase buildPhase, final File buildPhaseFile)
      throws Exception {
    if (buildPhaseFile != null && buildPhaseFile.exists()) {
      final List<Module> modules = new ArrayList<>();

      final File[] files = buildPhaseFile.listFiles();
      Arrays.sort(files);

      for (final File moduleConfigurationFile : files) {
        modules.add(getModuleInstance(moduleConfigurationFile, buildPhase, patch.getName()));
      }

      return (modules);
    }

    // throw (new IllegalArgumentException("Unsupported build phase."));
    return (null);
  }

  public List<Module> produceModules(
      BuildService buildService, Patch patch, BuildPhase buildPhase, final String variant)
      throws Exception {
    final File buildPhaseFile = getBuildPhaseFileForVariant(patch, buildPhase, variant);
    return (produceModules(buildService, patch, buildPhase, buildPhaseFile));
  }

  protected File getBuildPhaseFileForVariant(
      final Patch patch, final BuildPhase buildPhase, final String variant) {
    return (new File(
        patch.getPath()
            + File.separator
            + "variants"
            + File.separator
            + variant
            + File.separator
            + buildPhase.name().toLowerCase()));
  }

  protected File getBuildPhaseFile(final Patch patch, final BuildPhase buildPhase) {
    return (new File(patch.getPath() + File.separator + buildPhase.name().toLowerCase()));
  }

  // Creates the module from the specified configuration file and build phase.
  protected Module getModuleInstance(
      final File moduleConfigurationFile, final BuildPhase buildPhase, final String patchName)
      throws Exception {
    final Class<Module> beanClass = getModuleClass(moduleConfigurationFile);
    final Configurable configuration = ConfigurationUtil.read(moduleConfigurationFile, beanClass);

    final AbstractModule instance =
        (AbstractModule) GuiceHelper.getGuiceInjector().getInstance(beanClass);
    instance.setPatchName(patchName);
    instance.setFilename(moduleConfigurationFile.getAbsolutePath());
    instance.setConfiguration(configuration);
    instance.setBuildPhase(buildPhase);

    instance.onSetup();

    return (instance);
  }

  // Determines the Module Class from the configuration file and extension.
  private Class getModuleClass(final File moduleConfigurationFile) throws ClassNotFoundException {
    return (extensionToClassMapping.get(getFileExtension(moduleConfigurationFile)));

    // legacy code below
    /*
    final String moduleExtension = getModuleExtension(moduleConfigurationFile) + "Module";

    for (final Class<? extends Module> moduleClass : allClasses) {
      if (moduleClass.getSimpleName().equalsIgnoreCase(moduleExtension)) {
        return (moduleClass);
      }
    }

    throw (new ClassNotFoundException(
        "Unsupported file extension:" + moduleExtension + " no module mapped to that extension."));
    */
  }

  // Gets the extension for the given configuration file.
  private static String getModuleExtension(final File moduleConfigurationFile) {
    final String[] patchFilenameParts = moduleConfigurationFile.getName().split("\\.");
    return (patchFilenameParts[patchFilenameParts.length - 1].replace("-", ""));
  }

  private static String getFileExtension(final File moduleConfigurationFile) {
    final String[] patchFilenameParts = moduleConfigurationFile.getName().split("\\.");
    return (patchFilenameParts[patchFilenameParts.length - 1]);
  }
}
