package com.excilys.cdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.model.ComputerModel;

@Repository
public interface ComputerRepository extends CrudRepository<ComputerModel, Long> {

}
