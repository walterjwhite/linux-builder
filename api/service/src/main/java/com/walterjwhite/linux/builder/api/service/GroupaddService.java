package com.walterjwhite.linux.builder.api.service;

import com.walterjwhite.linux.builder.api.model.configuration.Groupadd;

public interface GroupaddService {
  void addUserToGroup(Groupadd groupadd);
}
