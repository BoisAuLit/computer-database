package com.excilys.cdb.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.persistence.model.CompanyModel;

@Repository
public interface CompanyRepository extends CrudRepository<CompanyModel, Long> {
}
