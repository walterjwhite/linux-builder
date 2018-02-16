package com.walterjwhite.linux.builder.impl.service.module.funtoo.portage;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.StringConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.enumeration.PortageConfigurationType;
import com.walterjwhite.linux.builder.impl.service.module.AbstractSingleModule;
import com.walterjwhite.logging.annotation.ContextualLoggable;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.FileUtils;

@ContextualLoggable
public abstract class AbstractPortagePackageModule
    extends AbstractSingleModule<StringConfiguration> {
  protected final PortageConfigurationType portageConfigurationType;
  protected File portageConfigurationFile;

  protected AbstractPortagePackageModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      PortageConfigurationType portageConfigurationType) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.portageConfigurationType = portageConfigurationType;
  }

  public void onSetup() {
    this.portageConfigurationFile =
        new File(
            buildConfiguration.getRootDirectory()
                + File.separator
                + "etc/portage/"
                + portageConfigurationType.getPortageName()
                + File.separator
                + getPatchName());
  }

  @Override
  public void document() {}

  @Override
  public void doRun()
      throws IOException, InterruptedException, NoSuchAlgorithmException, IllegalAccessException,
          InstantiationException {
    FileUtils.write(portageConfigurationFile, configuration.getContent(), "UTF-8");
  }

  protected void prepare() {
    final File packageUseParentDirectory = portageConfigurationFile.getParentFile();

    if (!packageUseParentDirectory.exists()) {
      packageUseParentDirectory.mkdirs();
    }
  }
}
