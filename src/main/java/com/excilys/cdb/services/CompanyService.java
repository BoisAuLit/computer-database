package com.excilys.cdb.services;

import java.util.List;
import java.util.Optional;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.DtoManager;

public class CompanyService {

  private CompanyService() {

  }

  private static class LazyHolder {
    private static final CompanyService INSTANCE = new CompanyService();
  }

  public static CompanyService getInstance() {
    return LazyHolder.INSTANCE;
  }

  public Optional<CompanyDto> getCompanyDtoById(long id) {
    return DtoManager.getCompanyDtoById(id);
  }

  public List<CompanyDto> getCompanyDtos() {
    return DtoManager.getCompanyDtos();
  }
}
