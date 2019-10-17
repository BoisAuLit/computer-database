package com.excilys.cdb.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.domain.Computer;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.ComputerPage;
import com.excilys.cdb.dto.DtoManager;

@Service
public class ComputerService {

  @Autowired
  private DtoManager dtoManager;
  @Autowired
  private ComputerDao computerDao;

  public Optional<ComputerDto> getComputerDtoById(long id) {
    return dtoManager.getComputerDtoById(id);
  }

  public List<ComputerDto> getComputerDtos() {
    return dtoManager.getComputerDtos();
  }

  public int updateComputer(ComputerDto computerDto) {
    Computer computer = dtoManager.getComputer(computerDto);
    return computerDao.update(computer);
  }

  public int saveComputer(ComputerDto computerDto) {
    Computer computer = dtoManager.getComputer(computerDto);
    return computerDao.save(computer);
  }

  public boolean batchDeleteComputer(List<String> ids) {
    int rowsAffected = computerDao.batchDelete(ids);
    return rowsAffected > 0;
  }

  /**
   * The following methods are about pagination
   */
  public ComputerPage getDefaultComputerPage() {
    // Todo, get all these from configuration file
    return computerDao.getPartial("", 10, 0);
  }

  public ComputerPage getComputerPage(String nameToFind, int limit, int offset) {
    return computerDao.getPartial(nameToFind, limit, offset);
  }
}
