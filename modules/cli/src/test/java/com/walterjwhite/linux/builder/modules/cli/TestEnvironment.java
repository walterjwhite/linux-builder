package com.walterjwhite.linux.builder.modules.cli;

import org.junit.Test;

public class TestEnvironment {

  @Test
  public void testReflections() {
    System.out.println("proxy_host" + System.getenv("proxy_host"));
    System.out.println("proxy_host" + System.getenv("http_proxy"));
  }
}
