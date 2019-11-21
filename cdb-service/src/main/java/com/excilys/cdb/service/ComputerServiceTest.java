package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.excilys.cdb.persistence.model.ComputerModel;
import com.excilys.cdb.persistence.repository.ComputerRepository;

@Service
public class ComputerServiceTest {

  @Autowired
  ComputerRepository computerRepository;

  @Transactional
  public List<ComputerModel> getAllComputers() {
    return (List<ComputerModel>) computerRepository.findAll();
  }

  @Transactional
  public Optional<ComputerModel> getComputerById(Long id) {
    return computerRepository.findById(id);
  }

  @Transactional
  public ComputerModel createComputer(ComputerModel computerModel) {
    return computerRepository.save(computerModel);
  }

  @Transactional
  public List<ComputerModel> findComputersByName(String name) {
    return computerRepository.findByNameContainingIgnoreCase(name);
  }

  @Transactional
  public ComputerModel updateComputer(ComputerModel computerModel) {
    return computerRepository.save(computerModel);
  }

  @Transactional
  public void deleteComputerById(Long id) {
    computerRepository.deleteById(id);
  }
}
