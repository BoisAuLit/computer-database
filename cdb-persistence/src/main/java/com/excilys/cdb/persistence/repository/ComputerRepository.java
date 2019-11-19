package com.excilys.cdb.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.persistence.model.ComputerModel;

@Repository
public interface ComputerRepository extends CrudRepository<ComputerModel, Long> {

  List<ComputerModel> findByNameContainingIgnoreCase(String name);

}
