package com.excilys.cdb.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class Company {
  private long id;
  private String name;

  public Company() {

  }

  public Company(long id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("id", id)
        .append("name", name)
        .toString();
  }

  @Override
  public int hashCode() {

    return (int) id * 7;
    // return new HashCodeBuilder()
    // .append(id)
    // .append(name)
    // .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Company)) {
      return false;
    }
    Company other = (Company) obj;
    return new EqualsBuilder()
        .appendSuper(super.equals(obj))
        .append(id, other.id)
        .append(name, other.name)
        .isEquals();
  }

}
