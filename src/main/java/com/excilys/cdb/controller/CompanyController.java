package com.excilys.cdb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.model.CompanyModel;
import com.excilys.cdb.services.CompanyServiceTest;

@RestController
public class CompanyController {
  @Autowired
  CompanyServiceTest companyServiceTest;

  @RequestMapping(value = "/cousin", method = RequestMethod.GET)
  public List<CompanyModel> getPersoneByName() {
    return companyServiceTest.getAllCompanies();
  }

}
