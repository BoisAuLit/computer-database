package com.excilys.cdb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.model.CompanyModel;
import com.excilys.cdb.services.CompanyServiceTest;

@RestController
@RequestMapping("/user/")
public class TestController {
  @Autowired
  CompanyServiceTest companyServiceTest;

  @GetMapping()
  public List<CompanyModel> getPersoneByName() {
    return companyServiceTest.getAllCompanies();
  }
}
