package com.walterjwhite.linux.builder.impl.service;

public class FilenameTest {
  public static void main(final String[] arguments) {
    String filename = "/root/3.hardware";
    String[] parts = filename.split("\\.");

    handleParts(parts);
  }

  private static void handleParts(final String[] parts) {}
}
