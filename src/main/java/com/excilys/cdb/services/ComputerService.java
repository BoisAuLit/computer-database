package com.excilys.cdb.services;

import java.util.List;
import java.util.Optional;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.DtoBuilder;

public class ComputerService {

  private ComputerService() {}

  private static class LazyHolder {
    private static final ComputerService INSTANCE = new ComputerService();
  }

  public static ComputerService getInstance() {
    return LazyHolder.INSTANCE;
  }

  public Optional<ComputerDto> getComputerDtoById(long id) {
    return DtoBuilder.getComputerDtoById(id);
  }

  public List<ComputerDto> getComputerDtos() {
    return DtoBuilder.getComputerDtos();
  }
}
