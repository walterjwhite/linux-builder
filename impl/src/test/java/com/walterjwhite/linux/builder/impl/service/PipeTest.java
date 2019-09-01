package com.walterjwhite.linux.builder.impl.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.junit.Test;

public class PipeTest {
  @Test
  public void testPipe() throws Exception {
    String line;
    String[] cmd = {"/bin/sh", "-c", "tar cp -C /tmp/source . | tar xp -C /tmp/target"};
    Process p = Runtime.getRuntime().exec(cmd);
    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
    while ((line = in.readLine()) != null) {
      System.out.println(line);
    }
    in.close();
  }
}
