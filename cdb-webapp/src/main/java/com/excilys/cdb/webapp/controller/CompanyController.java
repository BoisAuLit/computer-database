package com.excilys.cdb.webapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.persistence.model.CompanyModel;
import com.excilys.cdb.service.CompanyServiceTest;

@RestController
@RequestMapping("api/v1/company")
public class CompanyController {
  @Autowired
  CompanyServiceTest companyServiceTest;

  @GetMapping("get-all")
  public List<CompanyModel> getPersoneByName() {
    return companyServiceTest.getAllCompanies();
  }
}
