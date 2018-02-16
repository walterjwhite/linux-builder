package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User implements Configurable {
  protected String username;
  protected int uid;
  protected String gid;
  protected boolean system;
  protected String shell;
  protected Set<String> groups;
  protected String password;

  public User(String username, int uid, String gid, boolean system, String shell, String password) {
    this();

    this.username = username;
    this.uid = uid;
    this.gid = gid;
    this.system = system;
    this.shell = shell;

    this.password = password;
  }

  public User() {
    super();

    this.groups = new HashSet<>();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getGid() {
    return gid;
  }

  public void setGid(String gid) {
    this.gid = gid;
  }

  public boolean isSystem() {
    return system;
  }

  public void setSystem(boolean system) {
    this.system = system;
  }

  public String getShell() {
    return shell;
  }

  public void setShell(String shell) {
    this.shell = shell;
  }

  public Set<String> getGroups() {
    return groups;
  }

  public void setGroups(Set<String> groups) {
    this.groups = groups;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(username, user.username);
  }

  @Override
  public int hashCode() {

    return Objects.hash(username);
  }

  public boolean isRun() {
    return (username != null && !username.isEmpty());
  }
}
