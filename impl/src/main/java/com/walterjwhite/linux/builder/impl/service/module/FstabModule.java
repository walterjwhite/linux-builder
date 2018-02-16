package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.MountPointConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.shell.api.model.MountPoint;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

/** Writes the fstab */
@ModuleSupports(
  distribution = DistributionConfiguration.Linux,
  configurer = YamlConfigurer.class,
  configurationClass = MountPointConfiguration.class
)
public class FstabModule extends AbstractCollectionModule<MountPointConfiguration> {
  public static final String FSTAB_TEMPLATE = "%s %s %s %s 0 0 # %s\n";

  @Inject
  public FstabModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration) {
    super(buildService, buildConfiguration, distributionConfiguration);
  }

  public void document() {
    // tex.link_file(documentation_directory, 'RunModule Script', self.chroot_file, prefix=prefix)
  }

  protected File getFstabFile() {
    return (new File(buildConfiguration.getRootDirectory() + File.separator + "etc/fstab"));
  }

  /** Simply executes the script. TODO: do NOT do one at a time */
  @Override
  public void doRun(MountPointConfiguration mountPoint) throws IOException, InterruptedException {
    FileUtils.write(getFstabFile(), getFstabLine(mountPoint), "UTF-8", true);

    // ensure the doMount point exists
    new File(buildConfiguration.getRootDirectory() + mountPoint.getMountPoint()).mkdirs();
  }

  public String getFstabLine(final MountPointConfiguration mountPoint) {
    return (String.format(
        FSTAB_TEMPLATE,
        mountPoint.getDevice(),
        mountPoint.getMountPoint(),
        mountPoint.getVfsType().getType(),
        getOptions(mountPoint),
        patchName));
  }

  public static String getOptions(final MountPoint mountPoint) {
    if (mountPoint.getOptions() != null && !mountPoint.getOptions().isEmpty()) {
      return (mountPoint.getOptions() + ",defaults");
    }

    return ("defaults");
  }
}
