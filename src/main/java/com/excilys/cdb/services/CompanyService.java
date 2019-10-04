package com.excilys.cdb.services;

import java.util.List;
import java.util.Optional;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.DtoBuilder;

public class CompanyService {

  private CompanyService() {}

  private static class LazyHolder {
    private static final CompanyService INSTANCE = new CompanyService();
  }

  public static CompanyService getInstance() {
    return LazyHolder.INSTANCE;
  }

  public Optional<CompanyDto> getCompanyDtoById(long id) {
    return DtoBuilder.getCompanyDtoById(id);
  }

  public List<CompanyDto> getCompanyDtos() {
    return DtoBuilder.getCompanyDtos();
  }
}
