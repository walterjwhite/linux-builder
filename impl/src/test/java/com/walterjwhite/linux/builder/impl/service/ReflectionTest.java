package com.walterjwhite.linux.builder.impl.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionTest.class);

  public static void main(final String[] arguments)
      throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException,
          InvocationTargetException {
    /*
    AddPackagesModule addPackages = new AddPackagesModule(new BuildConfiguration(Distribution.Funtoo, "/builds/linux",
            "master", "workstation", "/projects/active/linux"), DistributionConfiguration.Funtoo, "",
            new ListConfiguration(), BuildPhase.Setup, "test-patch.patch");

    final Method method = AddPackagesModule.class.getMethod("isRun");
    LOGGER.debug("class:" +  method.getClass());
    LOGGER.debug("class:" + method.getDeclaringClass());
    */
  }
}
