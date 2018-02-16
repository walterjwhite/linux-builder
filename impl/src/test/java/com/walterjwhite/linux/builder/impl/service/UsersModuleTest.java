package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.linux.builder.api.model.BuildPhase;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.CollectionConfiguration;
import com.walterjwhite.linux.builder.api.model.configuration.User;
import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.module.UsersModule;
import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class UsersModuleTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(UsersModuleTest.class);

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
    final CollectionConfiguration<User> users = new CollectionConfiguration<>();
    users
        .getItems()
        .add(
            new Yaml()
                .loadAs(
                    new FileInputStream("/tmp/user-test.patch/build/0.users/walterjwhite"),
                    User.class));

    LOGGER.debug("user:" + users.getItems().iterator().next().getPassword());
    final String patchName = "walterjwhite-user";

    final String filename = "/builds/linux/linux-master/patches/users/walterjwhite-user.patch";

    UsersModule usersModule = GuiceHelper.getGuiceInjector().getInstance(UsersModule.class);
    // this needs set
    // usersModule.setBuildConfiguration();
    usersModule.run();
  }
}
