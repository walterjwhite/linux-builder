package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.CollectionConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.WebDriverRequest;
import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.module.WebDriverRequestModule;
import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class WebDriverRequestModuleTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverRequestModuleTest.class);

  public static void main(final String[] arguments) throws Exception {
    final BuildConfiguration buildConfiguration =
        new BuildConfiguration(
            Distribution.Funtoo,
            "/builds/linux",
            "master",
            "workstation",
            "/projects/active/linux");
    final DistributionConfiguration distributionConfiguration = DistributionConfiguration.Funtoo;
    final BuildPhase buildPhase = BuildPhase.Build;
    final CollectionConfiguration<WebDriverRequest> webDriverRequests =
        new CollectionConfiguration<>();
    webDriverRequests
        .getItems()
        .add(
            new Yaml()
                .loadAs(
                    new FileInputStream("/tmp/webDriverRequestTest.patch/0.get"),
                    WebDriverRequest.class));
    webDriverRequests
        .getItems()
        .add(
            new Yaml()
                .loadAs(
                    new FileInputStream("/tmp/webDriverRequestTest.patch/1.find"),
                    WebDriverRequest.class));
    webDriverRequests
        .getItems()
        .add(
            new Yaml()
                .loadAs(
                    new FileInputStream("/tmp/webDriverRequestTest.patch/2.find"),
                    WebDriverRequest.class));
    webDriverRequests
        .getItems()
        .add(
            new Yaml()
                .loadAs(
                    new FileInputStream("/tmp/webDriverRequestTest.patch/3.click"),
                    WebDriverRequest.class));

    LOGGER.debug("user:" + webDriverRequests.getItems().iterator().next());
    final String patchName = "webDriverRequestTest";

    final String filename = "/tmp/webDriverRequestTest.patch";

    // System.getenv().put("JBROWSERDRIVER_UBER_JAR",
    // "~/.m2/repository/com/machinepublishers/jbrowserdriver/0.17.3/jbrowserdriver-0.17.3-uberjar.jar");
    System.setProperty(
        "JBROWSERDRIVER_UBER_JAR",
        "~/.m2/repository/com/machinepublishers/jbrowserdriver/0.17.3/jbrowserdriver-0.17.3-uberjar.jar");

    final WebDriverRequestModule webDriverRequestModule =
        GuiceHelper.getGuiceInjector().getInstance(WebDriverRequestModule.class);
    // webDriverRequestModule.setBuildConfiguration();
    webDriverRequestModule.run();
  }
}
