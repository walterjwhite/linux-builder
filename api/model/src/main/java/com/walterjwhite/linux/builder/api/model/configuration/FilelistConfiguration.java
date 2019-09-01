package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// @Builder
@NoArgsConstructor
@Data
@ToString(doNotUseGetters = true)
public class FilelistConfiguration implements Configurable {
  protected List<String> filenames = new ArrayList<>();

  @Override
  public boolean isRun() {
    return (filenames != null && !filenames.isEmpty());
  }
}
