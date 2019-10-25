package com.excilys.cdb.persistence;

import java.util.Set;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.model.CompanyModel;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<CompanyModel, Long> {
  Set<CompanyModel> findByName(String name);
}
