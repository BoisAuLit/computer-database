package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.repository.ComputerRepository;

@Service
public class ComputerService {

  @Autowired
  ComputerRepository computerRepository;

  @Transactional
  public List<Computer> getAllComputers() {
    return (List<Computer>) computerRepository.findAll();
  }

  @Transactional
  public Optional<Computer> getComputerById(Long id) {
    return computerRepository.findById(id);
  }

  @Transactional
  public Computer createComputer(Computer computer) {
    return computerRepository.save(computer);
  }

  @Transactional
  public List<Computer> findComputersByName(String name) {
    return computerRepository.findByNameContainingIgnoreCase(name);
  }

  @Transactional
  public Computer updateComputer(Computer computer) {
    return computerRepository.save(computer);
  }
}
