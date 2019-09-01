package com.walterjwhite.linux.builder.impl.service.module.funtoo.portage;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.SystemUseConfiguration;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.module.AbstractSingleModule;
import com.walterjwhite.linux.builder.impl.service.util.PortageUtil;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;

@ModuleSupports(
    distribution = DistributionConfiguration.Gentoo,
    configurer = YamlConfigurer.class,
    configurationClass = SystemUseConfiguration.class)
public class SystemUseModule extends AbstractSingleModule<SystemUseConfiguration> {
  @Inject
  public SystemUseModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration) {
    super(buildService, buildConfiguration, distributionConfiguration);
  }

  @Override
  public void document() {}

  @Override
  public void doRun() throws IOException {
    // overwrite the existing file
    // subsequent patches will append
    FileUtils.write(getPortageConfigurationFile(), getContents(), "UTF-8", true);
  }

  protected File getPortageConfigurationFile() {
    return (new File(
        buildConfiguration.getRootDirectory() + File.separator + "etc/portage/make.conf"));
  }

  protected String getContents() {
    final StringBuilder buffer = new StringBuilder();

    buffer.append("\n# " + patchName + "\n");
    PortageUtil.append(buffer, "USE", PortageUtil.get(configuration.getUse()), true);
    PortageUtil.append(buffer, "VIDEO_CARDS", PortageUtil.get(configuration.getVideoCards()), true);
    PortageUtil.append(
        buffer, "SANE_BACKENDS", PortageUtil.get(configuration.getSaneBackends()), true);
    PortageUtil.append(
        buffer, "INPUT_DEVICES", PortageUtil.get(configuration.getInputDevices()), true);
    PortageUtil.append(buffer, "LINGUAS", PortageUtil.get(configuration.getLinguas()), true);
    PortageUtil.append(buffer, "PYTHON_SINGLE_TARGET", configuration.getPythonSingleTarget(), true);
    PortageUtil.append(
        buffer, "PYTHON_TARGETS", PortageUtil.get(configuration.getPythonTargets()), true);

    return (buffer.toString());
  }
}
