package com.excilys.cdb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.excilys.cdb.model.CompanyModel;
import com.excilys.cdb.persistence.CompanyRepository;

@Component
public class TestService {

  @Autowired
  private CompanyRepository companyRepository;

  @Transactional
  public Iterable<CompanyModel> getAll() {
    return companyRepository.findAll();
  }
}
