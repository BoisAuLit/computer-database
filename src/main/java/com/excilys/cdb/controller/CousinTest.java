package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.excilys.cdb.model.CompanyModel;
import com.excilys.cdb.persistence.CompanyRepository;

@Controller
@RequestMapping("cousin")
public class CousinTest {

  // @Autowired
  // private CompanyService companyService;

  @Autowired
  private CompanyRepository companyRepository;

  @GetMapping
  @ResponseBody
  public List<CompanyModel> getCousin() {
    // List<CompanyDto> companyDtos = companyService.getCompanyDtos();

    Iterable<CompanyModel> it = companyRepository.findAll();

    List<CompanyModel> l = new ArrayList<>();

    it.forEach(k -> l.add(k));

    return l;

    // return companyDtos;
  }
}
