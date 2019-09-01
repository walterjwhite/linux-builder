package com.walterjwhite.linux.builder.api.model.configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class ISOConfiguration implements Configurable {
  // http://cdimage.ubuntu.com/ubuntu-gnome/releases/17.04/release/ubuntu-gnome-17.04-desktop-amd64.iso
  protected String downloadURI;
  // 2526926971cf0133d03e316bde8f882d /
  // 5224e9f5db84e8f0f09c8abbb3917d6ab0ee528ca34aecf742b67d608a17422e
  // @Value.Auxiliary
  @EqualsAndHashCode.Exclude protected String downloadChecksum;

  // Ubuntu uses zlib compression, I'm using lzma / xz compression
  // @Value.Auxiliary
  @EqualsAndHashCode.Exclude protected String kernelPath;

  // @Value.Auxiliary
  @EqualsAndHashCode.Exclude protected String initPath;

  // @Value.Auxiliary
  @EqualsAndHashCode.Exclude protected String rootFSPath;
}
