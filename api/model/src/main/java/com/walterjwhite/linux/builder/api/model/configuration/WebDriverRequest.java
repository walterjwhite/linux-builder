package com.walterjwhite.linux.builder.api.model.configuration;

import com.walterjwhite.linux.builder.api.model.enumeration.WebDriverAction;
import lombok.Data;
import lombok.ToString;

// TODO: not currently implemented
@Data
@ToString(doNotUseGetters = true)
public class WebDriverRequest implements Configurable {
  /** IE. get, findByXPath, click */
  protected WebDriverAction action;

  /** Argument to the above. */
  protected String argument;

  @Override
  public boolean isRun() {
    if (action == null) return (false);

    if (action.getArgumentClass() != null && argument == null) return (false);

    return false;
  }
}
