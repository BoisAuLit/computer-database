package com.excilys.cdb.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.DtoManager;

@Service
public class CompanyService {
  @Autowired
  private DtoManager dtoManager;

  public Optional<CompanyDto> getCompanyDtoById(long id) {
    return dtoManager.getCompanyDtoById(id);
  }

  public List<CompanyDto> getCompanyDtos() {
    return dtoManager.getCompanyDtos();
  }
}
