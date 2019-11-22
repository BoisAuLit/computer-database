package com.excilys.cdb.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.excilys.cdb.persistence.model.Company;
import com.excilys.cdb.persistence.repository.CompanyRepository;
import com.excilys.cdb.persistence.repository.ComputerRepository;

@Service
@Transactional(readOnly = true)
public class CompanyService {

  @Autowired
  CompanyRepository companyRepository;
  @Autowired
  ComputerRepository computerRepository;

  public List<Company> getAllCompanies() {
    return (List<Company>) companyRepository.findAll();
  }

  public void deleteCompany(Long id) {
    computerRepository.deleteByCompany_Id(id);
    companyRepository.deleteById(id);
  }
}
