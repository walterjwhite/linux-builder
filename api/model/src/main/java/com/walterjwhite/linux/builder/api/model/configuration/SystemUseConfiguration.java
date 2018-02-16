package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.HashSet;
import java.util.Set;

public class SystemUseConfiguration implements Configurable {
  protected Set<String> videoCards;
  protected Set<String> inputDevices;
  protected Set<String> saneBackends;
  protected Set<String> linguas;
  protected Set<String> use;
  protected String pythonSingleTarget;
  protected Set<String> pythonTargets;

  public SystemUseConfiguration(
      Set<String> videoCards,
      Set<String> inputDevices,
      Set<String> saneBackends,
      Set<String> linguas,
      Set<String> use,
      Set<String> pythonTargets,
      String pythonSingleTarget) {
    this();

    if (videoCards != null) this.videoCards.addAll(videoCards);

    if (inputDevices != null) this.inputDevices.addAll(inputDevices);

    if (saneBackends != null) this.saneBackends.addAll(saneBackends);

    if (linguas != null) this.linguas.addAll(linguas);

    if (use != null) this.use.addAll(use);

    if (pythonTargets != null) this.pythonTargets.addAll(pythonTargets);

    this.pythonSingleTarget = pythonSingleTarget;
  }

  public SystemUseConfiguration() {
    super();

    videoCards = new HashSet<>();
    inputDevices = new HashSet<>();
    saneBackends = new HashSet<>();
    linguas = new HashSet<>();
    use = new HashSet<>();
    pythonTargets = new HashSet<>();
  }

  public Set<String> getVideoCards() {
    return videoCards;
  }

  public void setVideoCards(Set<String> videoCards) {
    this.videoCards = videoCards;
  }

  public Set<String> getInputDevices() {
    return inputDevices;
  }

  public void setInputDevices(Set<String> inputDevices) {
    this.inputDevices = inputDevices;
  }

  public Set<String> getSaneBackends() {
    return saneBackends;
  }

  public void setSaneBackends(Set<String> saneBackends) {
    this.saneBackends = saneBackends;
  }

  public Set<String> getLinguas() {
    return linguas;
  }

  public void setLinguas(Set<String> linguas) {
    this.linguas = linguas;
  }

  public Set<String> getUse() {
    return use;
  }

  public void setUse(Set<String> use) {
    this.use = use;
  }

  public Set<String> getPythonTargets() {
    return pythonTargets;
  }

  public void setPythonTargets(Set<String> pythonTargets) {
    this.pythonTargets = pythonTargets;
  }

  public String getPythonSingleTarget() {
    return pythonSingleTarget;
  }

  public void setPythonSingleTarget(String pythonSingleTarget) {
    this.pythonSingleTarget = pythonSingleTarget;
  }

  @Override
  public String toString() {
    return "SystemUseConfiguration{"
        + "videoCards="
        + videoCards
        + ", inputDevices="
        + inputDevices
        + ", saneBackends="
        + saneBackends
        + ", linguas="
        + linguas
        + ", use="
        + use
        + ", pythonTargets="
        + pythonTargets
        + ", pythonSingleTarget="
        + pythonSingleTarget
        + '}';
  }

  @Override
  public boolean isRun() {
    return ((use != null && !use.isEmpty())
        || (pythonTargets != null && !pythonTargets.isEmpty())
        || (videoCards != null && !videoCards.isEmpty())
        || (inputDevices != null && !inputDevices.isEmpty())
        || (saneBackends != null && !saneBackends.isEmpty())
        || (linguas != null && !linguas.isEmpty()
            || (pythonSingleTarget != null && !pythonSingleTarget.isEmpty())));
  }
}
