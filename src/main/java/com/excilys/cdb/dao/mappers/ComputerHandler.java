package com.excilys.cdb.dao.mappers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

@Component
public class ComputerHandler {

  @Autowired
  @Lazy
  private CompanyDao companyDao;

  public Computer convert(Map<String, Object> map) {

    Computer computer = new Computer();

    Long idLong = (Long) map.get(ComputerDao.ID_COLUMN_NAME);
    String nameStr = (String) map.get(ComputerDao.NAME_COLUMN_NAME);
    Timestamp introduceTimestamp = (Timestamp) map.get(ComputerDao.DATE_INTRODUCED_COLUMN_NAME);
    Timestamp discontinuedTimestamp =
        (Timestamp) map.get(ComputerDao.DATE_DISCONTINUED_COLUMN_NAME);
    Long companyIdLong = (Long) map.get(ComputerDao.COMPANY_ID_COLUMN_NAME);

    Optional<LocalDate> introduced = Optional.empty();
    Optional<LocalDate> discontinued = Optional.empty();
    Optional<Company> company = Optional.empty();

    if (Objects.nonNull(introduceTimestamp)) {
      LocalDate date = introduceTimestamp.toLocalDateTime().toLocalDate();
      introduced = Optional.of(date);
    }

    if (Objects.nonNull(discontinuedTimestamp)) {
      LocalDate date = discontinuedTimestamp.toLocalDateTime().toLocalDate();
      discontinued = Optional.of(date);
    }

    if (Objects.nonNull(companyIdLong)) {
      company = companyDao.get(companyIdLong);
    }

    computer.setId(idLong);
    computer.setName(nameStr);
    computer.setIntroduced(introduced);
    computer.setDiscontinued(discontinued);
    computer.setCompany(company);

    return computer;
  }

  public List<Computer> convert(List<Map<String, Object>> mapList) {

    List<Computer> computers = new ArrayList<>();

    for (Map<String, Object> map : mapList) {
      Computer computer = convert(map);
      computers.add(computer);
    }

    return computers;
  }

}
