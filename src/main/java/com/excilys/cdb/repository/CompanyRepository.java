package com.excilys.cdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.model.CompanyModel;

@Repository
public interface CompanyRepository extends CrudRepository<CompanyModel, Long> {
}
