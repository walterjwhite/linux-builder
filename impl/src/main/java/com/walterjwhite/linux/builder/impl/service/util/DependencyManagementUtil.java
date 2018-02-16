package com.walterjwhite.linux.builder.impl.service.util;

import com.walterjwhite.linux.builder.api.model.patch.Patch;
import com.walterjwhite.linux.builder.api.model.patch.PatchEdge;
import java.io.File;
import java.io.IOException;
import java.util.*;

/** Finds all of the patches and determines the order to runInChroot them. */
public class DependencyManagementUtil {
  private DependencyManagementUtil() {}

  public static Patch getPatchByName(
      final String repositoryPath, final String variant, final String patchName)
      throws IOException {
    final List<Patch> patches = getOrderedSystemPatches(repositoryPath, variant);

    for (Patch patch : patches) {
      if (patch.getName().equals(patchName)) {
        return (patch);
      }
    }

    throw (new IllegalStateException("Patch : " + patchName + " not found."));
  }

  private static Set<String> getSystemPatches(final String repositoryPath, final String variant)
      throws IOException {
    // read system and get a list of patches
    final File patchFile =
        new File(
            repositoryPath
                + File.separator
                + "systems"
                + File.separator
                + variant
                + File.separator
                + "patches");

    final List<String> systemPatches = IOUtil.readLines(patchFile.getAbsolutePath());

    final Set<String> systemPatchset = new HashSet<>();
    for (final String systemPatch : systemPatches) {
      if (!systemPatch.endsWith(".patch")) {
        systemPatchset.addAll(
            getPatches(
                new File(
                    repositoryPath + File.separator + "patches" + File.separator + systemPatch)));
      } else {
        systemPatchset.add(
            new File(repositoryPath + File.separator + "patches" + File.separator + systemPatch)
                .getAbsolutePath());
      }
    }

    return (systemPatchset);
  }

  private static Set<Patch> getSystemPatchInstances(
      final String repositoryPath, final String system, final String variant) throws IOException {
    final Set<Patch> systemPatches = new HashSet<>();
    for (final String systemPatchPath : getSystemPatches(repositoryPath, system)) {
      systemPatches.add(
          new Patch(
              getPatchName(systemPatchPath),
              systemPatchPath,
              doesVariantExist(systemPatchPath, variant)));
    }

    return (systemPatches);
  }

  public static List<Patch> getOrderedSystemPatches(
      final String repositoryPath, final String variant) throws IOException {
    final Set<Patch> systemPatches = getSystemPatchInstances(repositoryPath, "base", variant);
    systemPatches.addAll(getSystemPatchInstances(repositoryPath, variant, variant));
    setupDependencies(systemPatches);

    final List<Patch> sortedPatches = sortPatches(prepareFirstLevel(systemPatches));

    checkForCycle(systemPatches);

    Collections.reverse(sortedPatches);
    return (sortedPatches);
  }

  private static void setupDependencies(final Set<Patch> systemPatches) throws IOException {
    // setup dependencies
    for (final Patch systemPatch : systemPatches) {
      final File dependencyFile = new File(systemPatch.getPath() + File.separator + "dependencies");
      if (dependencyFile.exists()) {
        final List<String> dependencies = IOUtil.readLines(dependencyFile.getAbsolutePath());

        for (final String dependency : dependencies) {
          systemPatch.addDependency(getPatch(systemPatches, dependency));
        }
      }
    }
  }

  private static Set<Patch> prepareFirstLevel(final Set<Patch> systemPatches) {
    final Set<Patch> firstLevel = new HashSet<>();
    for (Patch patch : systemPatches) {
      if (patch.getInEdges().size() == 0) {
        firstLevel.add(patch);
      }
    }

    return (firstLevel);
  }

  private static List<Patch> sortPatches(final Set<Patch> firstLevel) {
    final List<Patch> sortedPatches = new ArrayList<>();
    while (!firstLevel.isEmpty()) {
      Patch patch = firstLevel.iterator().next();
      firstLevel.remove(patch);

      sortedPatches.add(patch);

      for (Iterator<PatchEdge> patchEdgeIterator = patch.getOutEdges().iterator();
          patchEdgeIterator.hasNext(); ) {
        PatchEdge patchEdge = patchEdgeIterator.next();
        Patch edgePatch = patchEdge.getTo();
        patchEdgeIterator.remove();
        edgePatch.getInEdges().remove(patchEdge);

        if (edgePatch.getInEdges().isEmpty()) {
          firstLevel.add(edgePatch);
        }
      }
    }

    return (sortedPatches);
  }

  private static void checkForCycle(Set<Patch> patches) {
    for (Patch patch : patches) {
      if (!patch.getInEdges().isEmpty()) {
        throw (new IllegalStateException("Cycle detected, blew up."));
      }
    }
  }

  private static Patch getPatch(final Set<Patch> systemPatches, final String systemPatchName) {
    for (final Patch systemPatch : systemPatches) {
      if (systemPatch.getName().equals(systemPatchName)) {
        return (systemPatch);
      }
    }

    throw (new IllegalStateException("Unable to resolve dependency for:" + systemPatchName));
  }

  private static boolean doesVariantExist(final String filename, final String variant) {
    return (new File(filename + File.separator + "variants" + File.separator + variant).exists());
  }

  private static final String getPatchName(final String filename) {
    final String[] filenameParts = filename.split("/");

    final String baseName = filenameParts[filenameParts.length - 1];
    return (baseName.replace(".patch", ""));
  }

  private static Set<String> getPatches(final File parent) {
    final Set<String> patchFiles = new HashSet<>();

    for (final File child : parent.listFiles()) {
      if (child.isDirectory()) {
        if (child.getAbsolutePath().endsWith(".patch")) {
          patchFiles.add(child.getAbsolutePath());
        } else {
          patchFiles.addAll(getPatches(child));
        }
      }
    }

    return (patchFiles);
  }
}
