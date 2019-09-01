package com.walterjwhite.linux.builder.impl.service.module;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.ExternalFile;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import com.walterjwhite.logging.annotation.ContextualLoggable;
import java.io.File;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

@ContextualLoggable
@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = YamlConfigurer.class,
    configurationClass = ExternalFile.class)
public class CopyExternalFilesModule extends AbstractCollectionModule<ExternalFile> {
  @Inject
  public CopyExternalFilesModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration) {
    super(buildService, buildConfiguration, distributionConfiguration);
  }

  @Override
  public void document() {}

  @Override
  protected void doRun(ExternalFile item) throws Exception {
    // copy source to the target
    final File source = getSource(item);
    final File target = getTarget(item);

    if (source.isDirectory()) {
      FileUtils.copyDirectory(source, target);
    } else {
      FileUtils.copyFile(source, target);
    }
  }

  protected File getSource(ExternalFile item) {
    return (new File(
        buildConfiguration.getBuildDirectory()
            + File.separator
            + "external-files"
            + File.separator
            + item.getSource()));
  }

  protected File getTarget(ExternalFile item) {
    final File target = new File(buildConfiguration.getRootDirectory() + item.getTarget());
    target.getParentFile().mkdirs();

    return (target);
  }
}
