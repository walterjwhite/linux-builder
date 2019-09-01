package com.walterjwhite.linux.builder.impl.service.module;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.WebDriverRequest;
import com.walterjwhite.linux.builder.api.model.enumeration.WebDriverAction;
import com.walterjwhite.linux.builder.api.service.BuildService;
import com.walterjwhite.linux.builder.impl.service.annotation.ModuleSupports;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.YamlConfigurer;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

// TODO: 1. this is dependent upon jbrowser driver, instead use remote cli driver 2. remote cli
// driver has proxy issues 3. jbrowser driver has classpath issues ...
@ModuleSupports(
    distribution = DistributionConfiguration.Linux,
    configurer = YamlConfigurer.class,
    configurationClass = WebDriverRequest.class)
public class WebDriverRequestModule extends AbstractCollectionModule<WebDriverRequest> {
  // protected final WebDriver webDriver;
  protected final JBrowserDriver webDriver;
  protected WebElement webElement;

  @Inject
  public WebDriverRequestModule(
      BuildService buildService,
      BuildConfiguration buildConfiguration,
      DistributionConfiguration distributionConfiguration,
      JBrowserDriver webDriver) {
    super(buildService, buildConfiguration, distributionConfiguration);
    this.webDriver = webDriver;
  }

  // @NOTE: this requires JBROWSERDRIVER_UBER_JAR to be set

  // construct instance
  //    webDriver =
  //        new JBrowserDriver(
  //            Settings.builder()
  //                .timezone(Timezone.AMERICA_NEWYORK) /*.ssl("trustanything")*/
  //                .cacheDir(new File(System.getenv("HOMEDRIVE") + System.getenv("HOMEPATH")))
  //                .saveAttachments(true)
  //                //.javaOptions("-classpath", System.getenv("JBROWSERDRIVER_UBER_JAR"))
  //                .javaOptions("-classpath", System.getProperty("JBROWSERDRIVER_UBER_JAR"))
  //                .proxy(new ProxyConfig(ProxyConfig.Type.HTTP, "localhost", 8118))
  //                .javascript(true)
  //                .build());

  public void document() {
    // tex.link_file(documentation_directory, 'RunModule Script', self.chroot_file, prefix=prefix)
  }

  protected void saveAttachment(final String filename) throws IOException {
    for (final File attachment :
        webDriver.attachmentsDir().listFiles(new DownloadFilenameFilter())) {
      FileUtils.copyFile(attachment, getDownloadFile(filename));
    }
  }

  protected File getDownloadFile(final String filename) {
    return (new File(
        buildConfiguration.getRootDirectory()
            + File.separator
            + "tmp/downloads"
            + File.separator
            + filename));
  }

  protected class DownloadFilenameFilter implements FilenameFilter {

    @Override
    public boolean accept(File file, String s) {
      return (patchName.toLowerCase().endsWith(".content"));
    }
  }

  /**
   * Download all of the files. TODO: runInChroot each download in a separate thread and wait for
   * all to finish
   */
  protected void doRun(final WebDriverRequest webDriverRequest) throws IOException {
    // actions: get, findByXPath, click
    if (WebDriverAction.Get.equals(webDriverRequest.getAction())) {
      webDriver.get(webDriverRequest.getArgument());
    } else if (WebDriverAction.FindByXPath.equals(webDriverRequest.getAction())) {
      webElement = webDriver.findElement(By.xpath(webDriverRequest.getArgument()));
    } else if (WebDriverAction.Click.equals(webDriverRequest.getAction())) {
      if (webElement == null) {
        throw (new IllegalStateException("cli element is null"));
      }

      webElement.click();
    } else if (WebDriverAction.SaveAttachment.equals(webDriverRequest.getAction())) {
      saveAttachment(webDriverRequest.getArgument());
    }
  }
}
