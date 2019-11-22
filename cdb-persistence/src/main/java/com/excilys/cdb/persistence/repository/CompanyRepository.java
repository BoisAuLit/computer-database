package com.excilys.cdb.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.persistence.model.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
}
