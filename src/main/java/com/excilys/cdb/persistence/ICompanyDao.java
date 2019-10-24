package com.excilys.cdb.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.excilys.cdb.domain.Company;

public interface ICompanyDao extends JpaRepository<Company, Long> {

}
