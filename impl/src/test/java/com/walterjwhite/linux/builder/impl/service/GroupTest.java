package com.walterjwhite.linux.builder.impl.service;

import com.walterjwhite.linux.builder.impl.service.module.GroupaddModule;
import com.walterjwhite.linux.builder.impl.service.module.GroupsModule;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(GroupTest.class);

  public static void main(final String[] arguments) throws IOException {
    LOGGER.debug(
        Boolean.toString(
            GroupsModule.doesGroupExist("/builds/linux/master/workstation/root", "ssh")));
    LOGGER.debug(
        Boolean.toString(
            GroupaddModule.isUserMemberOfGroup(
                "/builds/linux/master/workstation/root", "walterjwhite", "ssh")));
  }
}
