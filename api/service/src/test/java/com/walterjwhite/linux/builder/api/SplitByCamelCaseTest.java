package com.walterjwhite.linux.builder.api;

import java.util.Arrays;
import org.junit.Test;

public class SplitByCamelCaseTest {
  @Test
  public void doTestSplit() {
    System.out.println(Arrays.asList("AbstractSingleModule".split("(?<=[a-z0-9])(?=[A-Z])")));
  }
}
