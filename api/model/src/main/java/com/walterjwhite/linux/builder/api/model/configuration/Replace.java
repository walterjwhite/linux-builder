package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.Map;
import lombok.Data;
import lombok.ToString;

// TODO: not currently implemented
@Data
@ToString(doNotUseGetters = true)
public class Replace implements Configurable {
  protected String filename;

  protected Map<String, Map<Integer, String>> mimeMapping;

  public boolean isRun() {
    return (filename != null
        && !filename.isEmpty()
        && mimeMapping != null
        && !mimeMapping.isEmpty());
  }
}
