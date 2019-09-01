package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.model.configuration.User;
import com.walterjwhite.linux.builder.impl.service.module.UsersModule;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import org.yaml.snakeyaml.Yaml;

public class UsersModuleTest {

  public static void main(final String[] arguments) throws Exception {
    for (final String argument : arguments) {
      User user =
          new Yaml()
              .loadAs(
                  new BufferedInputStream(
                      new FileInputStream("/tmp/user-test.patch/build/0.users/" + argument)),
                  User.class);
      LOGGER.debug("user:" + user.getPassword());

      UsersModule usersModule =
          GuiceHelper.getGuiceApplicationInjector().getInstance(UsersModule.class);
      // this needs set
      // usersModule.setBuildConfiguration();
      usersModule.run();
    }
  }
}
