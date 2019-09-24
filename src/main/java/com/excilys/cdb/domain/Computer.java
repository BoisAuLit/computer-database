package com.excilys.cdb.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class Computer {
  private long id;
  private String name;
  private Optional<LocalDate> introduced = Optional.empty();
  private Optional<LocalDate> discontinued = Optional.empty();
  private Optional<Company> company = Optional.empty();

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

  public Optional<LocalDate> getIntroduced() {
    return introduced;
  }

  public void setIntroduced(Optional<LocalDate> introduced) {
    this.introduced = introduced;
  }

  public Optional<LocalDate> getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(Optional<LocalDate> discontinued) {
    this.discontinued = discontinued;
  }

  public Optional<Company> getCompany() {
    return company;
  }

  public void setCompany(Optional<Company> company) {
    this.company = company;
  }

  @Override
  public String toString() {

    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("id", id)
        .append("name", name)
        .append("introduced", introduced)
        .append("discontinued", discontinued)
        .append("compnay", company)
        .toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  private boolean basicCheck(Object obj) {
    if (this == obj) {
      return true;
    }
    if ((getClass() != obj.getClass())) {
      return false;
    }
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (!basicCheck(obj)) {
      return false;
    }

    Computer other = (Computer) obj;
    return id == other.id;
  }

  public boolean StrictlyEqauls(Object obj) {
    if (!basicCheck(obj)) {
      return false;
    }
    Computer other = (Computer) obj;

    return new EqualsBuilder()
        .append(id, other.id)
        .append(name, other.name)
        .append(introduced, other.introduced)
        .append(discontinued, other.discontinued)
        .append(company, other.company)
        .isEquals();

  }
}
