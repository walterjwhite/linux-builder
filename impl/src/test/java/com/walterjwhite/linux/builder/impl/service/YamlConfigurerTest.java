package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import java.io.IOException;

public class YamlConfigurerTest {

  public static void main(final String[] arguments) throws IOException {

    YamlConfigurer yamlConfigurer = new YamlConfigurer();

    /*
    final DownloadConfiguration downloadConfiguration = (DownloadConfiguration) yamlConfigurer.read(new File("/mnt/work/linux/linux-repo/patches/development/intellij-idea.patch/setup/downloads"),
           DownloadConfiguration.class);

    //final DownloadConfiguration downloadConfiguration = new Yaml().loadAs(new FileInputStream(new File("/mnt/work/linux/linux-repo/patches/development/intellij-idea.patch/setup/downloads")), DownloadConfiguration.class);
    LOGGER.info("download conf:" + downloadConfiguration.getItems().iterator().next().getSignature());
    */
  }
}
