package com.walterjwhite.linux.builder.api.model;

import com.walterjwhite.linux.builder.api.model.enumeration.Distribution;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class System {
  protected Distribution distribution;
}
