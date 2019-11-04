package com.excilys.cdb.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.excilys.cdb.model.CompanyModel;
import com.excilys.cdb.repository.CompanyRepository;

@Service
public class CompanyServiceTest {

  @Autowired
  CompanyRepository companyRepository;


  @Transactional
  public List<CompanyModel> getAllCompanies() {
    return (List<CompanyModel>) companyRepository.findAll();
  }
}
