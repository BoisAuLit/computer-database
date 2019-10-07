package com.excilys.cdb.dto;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ComputerDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;
  private String name;
  private String introduced;
  private String discontinued;
  private long companyId;
  private String companyName;

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

  public String getIntroduced() {
    return introduced;
  }

  public void setIntroduced(String introduced) {
    this.introduced = introduced;
  }

  public String getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(String discontinued) {
    this.discontinued = discontinued;
  }

  public long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(long companyId) {
    this.companyId = companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  @Override
  public String toString() {

    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("id", id)
        .append("name", name)
        .append("introduced", introduced)
        .append("discontinued", discontinued)
        .append("company", companyId)
        .append("companyName", companyName)
        .toString();

  }

}
