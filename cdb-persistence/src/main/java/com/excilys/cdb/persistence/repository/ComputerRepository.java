package com.excilys.cdb.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.persistence.model.Computer;

@Repository
public interface ComputerRepository extends CrudRepository<Computer, Long> {

  List<Computer> findByNameContainingIgnoreCase(String name);

  void deleteByCompany_Id(Long id);

  void deleteByIdIn(List<Long> ids);
}
