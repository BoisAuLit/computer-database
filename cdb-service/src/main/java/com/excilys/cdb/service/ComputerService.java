package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.excilys.cdb.persistence.model.Computer;
import com.excilys.cdb.persistence.repository.ComputerRepository;

@Service
@Transactional(readOnly = true)
public class ComputerService {

  @Autowired
  ComputerRepository computerRepository;

  public List<Computer> getAllComputers() {
    return (List<Computer>) computerRepository.findAll();
  }

  public Optional<Computer> getComputerById(Long id) {
    return computerRepository.findById(id);
  }

  public Computer createComputer(Computer computer) {
    return computerRepository.save(computer);
  }

  public List<Computer> findComputersByName(String name) {
    return computerRepository.findByNameContainingIgnoreCase(name);
  }

  public Computer updateComputer(Computer computer) {
    return computerRepository.save(computer);
  }

  public void deleteComputerById(Long id) {
    computerRepository.deleteById(id);
  }

  public void deleteComputerByIds(List<Long> ids) {
    computerRepository.deleteByIdIn(ids);
  }
}
