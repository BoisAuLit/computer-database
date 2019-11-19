package com.excilys.cdb.persistence.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "introduced")
  private Timestamp introduced;

  @Column(name = "discontinued")
  private Timestamp discontinued;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyModel companyModel;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Timestamp getIntroduced() {
    return introduced;
  }

  public void setIntroduced(Timestamp introduced) {
    this.introduced = introduced;
  }

  public Timestamp getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(Timestamp discontinued) {
    this.discontinued = discontinued;
  }

  public CompanyModel getCompanyModel() {
    return companyModel;
  }

  public void setCompanyModel(CompanyModel companyModel) {
    this.companyModel = companyModel;
  }


}
