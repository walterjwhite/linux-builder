package com.walterjwhite.linux.builder.api.model.configuration;

import java.util.Objects;

public class Group implements Configurable {
  protected String groupname;
  protected String password;
  protected int gid;
  protected boolean system;
  protected String chroot;

  public Group(String groupname, String password, int gid, boolean system, String chroot) {
    this();

    this.groupname = groupname;
    this.password = password;
    this.gid = gid;
    this.system = system;
    this.chroot = chroot;
  }

  public Group() {
    super();
  }

  public String getGroupname() {
    return groupname;
  }

  public void setGroupname(String groupname) {
    this.groupname = groupname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getGid() {
    return gid;
  }

  public void setGid(int gid) {
    this.gid = gid;
  }

  public boolean isSystem() {
    return system;
  }

  public void setSystem(boolean system) {
    this.system = system;
  }

  public String getChroot() {
    return chroot;
  }

  public void setChroot(String chroot) {
    this.chroot = chroot;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Group group = (Group) o;
    return Objects.equals(groupname, group.groupname);
  }

  @Override
  public int hashCode() {

    return Objects.hash(groupname);
  }

  public boolean isRun() {
    return !(groupname == null || groupname.isEmpty());
  }
}
