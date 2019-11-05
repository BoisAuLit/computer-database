package com.excilys.cdb.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.excilys.cdb.model.ComputerModel;
import com.excilys.cdb.repository.ComputerRepository;

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
}
