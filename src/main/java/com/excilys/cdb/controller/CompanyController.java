package com.excilys.cdb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.services.CompanyService;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {
  @Autowired
  CompanyService companyService;

  @GetMapping("get-all")
  public List<Company> getPersoneByName() {
    return companyService.getAllCompanies();
  }
}
