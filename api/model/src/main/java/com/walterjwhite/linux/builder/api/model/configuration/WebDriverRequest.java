package com.walterjwhite.linux.builder.api.model.configuration;

import com.walterjwhite.linux.builder.api.model.enumeration.WebDriverAction;
import java.util.Objects;

// TODO: not currently implemented
public class WebDriverRequest implements Configurable {
  /** IE. get, findByXPath, click */
  protected WebDriverAction action;

  /** Argument to the above. */
  protected String argument;

  public WebDriverAction getAction() {
    return action;
  }

  public void setAction(WebDriverAction action) {
    this.action = action;
  }

  public String getArgument() {
    return argument;
  }

  public void setArgument(String argument) {
    this.argument = argument;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WebDriverRequest that = (WebDriverRequest) o;
    return action == that.action && Objects.equals(argument, that.argument);
  }

  @Override
  public int hashCode() {

    return Objects.hash(action, argument);
  }

  @Override
  public boolean isRun() {
    if (action == null) return (false);

    if (action.getArgumentClass() != null && argument == null) return (false);

    return false;
  }
}
