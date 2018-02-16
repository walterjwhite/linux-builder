package com.walterjwhite.linux.builder.api.service;

import com.walterjwhite.linux.builder.api.model.configuration.Runlevel;

public interface RunlevelManagementService {
  void add(Runlevel runlevel) throws Exception;

  void remove(Runlevel runlevel) throws Exception;
}
