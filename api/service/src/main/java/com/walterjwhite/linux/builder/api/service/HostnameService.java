package com.walterjwhite.linux.builder.api.service;

import java.io.IOException;

public interface HostnameService {
  /**
   * Set the hostname including the domain name.
   *
   * @throws IOException
   */
  void set(final String hostname) throws IOException;
}
