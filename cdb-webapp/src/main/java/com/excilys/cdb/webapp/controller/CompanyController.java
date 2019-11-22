package com.excilys.cdb.webapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.persistence.model.Company;
import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {
  @Autowired
  CompanyService companyService;

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @GetMapping("get-all")
  public List<Company> getCompanyByName() {
    return companyService.getAllCompanies();
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @DeleteMapping("delete/{id}")
  public void deleteById(@PathVariable Long id) {
    companyService.deleteCompany(id);
  }

}
