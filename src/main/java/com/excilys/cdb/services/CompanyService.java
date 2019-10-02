package com.excilys.cdb.services;

import java.util.List;
import java.util.stream.Collectors;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.domain.Company;

public class CompanyService {

  private CompanyService() {}

  private static class LazyHolder {
    private static final CompanyService INSTANCE = new CompanyService();
  }

  public static CompanyService getInstance() {
    return LazyHolder.INSTANCE;
  }

  public List<Long> getCompnayIds() {
    List<Company> companies = CompanyDao.getInstance().getAll();
    return companies.stream()
        .map(Company::getId)
        .collect(Collectors.toList());
  }
}
