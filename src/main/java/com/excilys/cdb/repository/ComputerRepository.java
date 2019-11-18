package com.excilys.cdb.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.model.Computer;

@Repository
public interface ComputerRepository extends CrudRepository<Computer, Long> {

  List<Computer> findByNameContainingIgnoreCase(String name);

}
