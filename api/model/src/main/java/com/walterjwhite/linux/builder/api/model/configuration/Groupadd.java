package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.Objects;

public class Groupadd implements Configurable {
  protected String groupname;
  protected String username;

  public Groupadd(String group, String username) {
    this();

    this.groupname = group;
    this.username = username;
  }

  public Groupadd() {
    super();
  }

  public String getGroupname() {
    return groupname;
  }

  public void setGroupname(String groupname) {
    this.groupname = groupname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Groupadd groupadd = (Groupadd) o;
    return Objects.equals(groupname, groupadd.groupname)
        && Objects.equals(username, groupadd.username);
  }

  @Override
  public int hashCode() {

    return Objects.hash(groupname, username);
  }

  public boolean isRun() {
    return (groupname != null && !groupname.isEmpty() && username != null && !username.isEmpty());
  }
}
