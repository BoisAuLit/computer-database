package com.excilys.cdb.services;

import java.util.List;
import java.util.Optional;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.domain.Computer;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.ComputerPage;
import com.excilys.cdb.dto.DtoManager;

public class ComputerService {

  private ComputerService() {

  }

  private static class LazyHolder {
    private static final ComputerService INSTANCE = new ComputerService();
  }

  public static ComputerService getInstance() {
    return LazyHolder.INSTANCE;
  }

  public Optional<ComputerDto> getComputerDtoById(long id) {
    return DtoManager.getComputerDtoById(id);
  }

  public List<ComputerDto> getComputerDtos() {
    return DtoManager.getComputerDtos();
  }

  public int updateComputer(ComputerDto computerDto) {
    Computer computer = DtoManager.getComputer(computerDto);
    return ComputerDao.getInstance().update(computer);
  }


  public ComputerPage getDefaultComputerPage() {
    // Todo, get all these from configuration file
    return ComputerDao.getInstance().getPartial(10, 0);
  }

  public ComputerPage getComputerPage(int limit, int offset) {
    return ComputerDao.getInstance().getPartial(limit, offset);
  }
}
