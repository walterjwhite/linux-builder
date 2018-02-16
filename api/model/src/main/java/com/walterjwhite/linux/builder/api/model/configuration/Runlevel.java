package com.walterjwhite.linux.builder.api.model.configuration;

import com.walterjwhite.linux.builder.api.model.enumeration.RunlevelAction;
import java.util.Objects;

public class Runlevel implements Configurable {
  protected String serviceName;
  protected RunlevelAction runlevelAction;
  protected String runlevel;

  public Runlevel(String serviceName, RunlevelAction runlevelAction, String runlevel) {
    this();

    this.serviceName = serviceName;
    this.runlevelAction = runlevelAction;
    this.runlevel = runlevel;
  }

  public Runlevel() {
    super();
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public RunlevelAction getRunlevelAction() {
    return runlevelAction;
  }

  public void setRunlevelAction(RunlevelAction runlevelAction) {
    this.runlevelAction = runlevelAction;
  }

  public String getRunlevel() {
    return runlevel;
  }

  public void setRunlevel(String runlevel) {
    this.runlevel = runlevel;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Runlevel runlevel1 = (Runlevel) o;
    return Objects.equals(serviceName, runlevel1.serviceName)
        && runlevelAction == runlevel1.runlevelAction
        && Objects.equals(runlevel, runlevel1.runlevel);
  }

  @Override
  public int hashCode() {

    return Objects.hash(serviceName, runlevelAction, runlevel);
  }

  @Override
  public String toString() {
    return "Runlevel{"
        + "serviceName='"
        + serviceName
        + '\''
        + ", runlevelAction="
        + runlevelAction
        + ", runlevel='"
        + runlevel
        + '\''
        + '}';
  }

  public boolean isRun() {
    return (serviceName != null
        && !serviceName.isEmpty()
        && runlevelAction != null
        && runlevel != null
        && !runlevel.isEmpty());
  }
}
