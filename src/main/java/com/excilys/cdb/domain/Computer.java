package com.excilys.cdb.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public final class Computer {
  private long id;
  private String name;
  private Optional<LocalDate> introduced;
  private Optional<LocalDate> discontinued;
  private Optional<Company> company;

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
    return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced
        + ", discontinued=" + discontinued + ", company=" + company + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Computer)) {
      return false;
    }
    Computer other = (Computer) obj;
    return id == other.id;
  }
}
