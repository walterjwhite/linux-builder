package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.CollectionConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.WebDriverRequest;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.module.WebDriverRequestModule;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import org.yaml.snakeyaml.Yaml;

public class WebDriverRequestModuleTest {

  public static void main(final String[] arguments) throws Exception {
    final BuildConfiguration buildConfiguration = null;
    //        new BuildConfiguration(
    //            Distribution.Funtoo,
    //            "/builds/linux",
    //            "master",
    //            "workstation",
    //            "/projects/active/linux");
    final DistributionConfiguration distributionConfiguration = DistributionConfiguration.Funtoo;
    final BuildPhase buildPhase = BuildPhase.Build;
    final CollectionConfiguration<WebDriverRequest> collectionConfiguration =
        new CollectionConfiguration<>();
    collectionConfiguration
        .getItems()
        .add(
            new Yaml()
                .loadAs(
                    new BufferedInputStream(
                        new FileInputStream("/tmp/webDriverRequestTest.patch/0.get")),
                    WebDriverRequest.class));
    collectionConfiguration
        .getItems()
        .add(
            new Yaml()
                .loadAs(
                    new BufferedInputStream(
                        new FileInputStream("/tmp/webDriverRequestTest.patch/1.find")),
                    WebDriverRequest.class));
    collectionConfiguration
        .getItems()
        .add(
            new Yaml()
                .loadAs(
                    new BufferedInputStream(
                        new FileInputStream("/tmp/webDriverRequestTest.patch/2.find")),
                    WebDriverRequest.class));
    collectionConfiguration
        .getItems()
        .add(
            new Yaml()
                .loadAs(
                    new BufferedInputStream(
                        new FileInputStream("/tmp/webDriverRequestTest.patch/3.click")),
                    WebDriverRequest.class));

    LOGGER.debug("user:" + collectionConfiguration.getItems().iterator().next());
    final String patchName = "webDriverRequestTest";

    final String filename = "/tmp/webDriverRequestTest.patch";

    final WebDriverRequestModule webDriverRequestModule =
        GuiceHelper.getGuiceApplicationInjector().getInstance(WebDriverRequestModule.class);
    // webDriverRequestModule.setBuildConfiguration();
    webDriverRequestModule.run();
  }
}
