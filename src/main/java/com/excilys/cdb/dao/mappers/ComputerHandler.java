package com.excilys.cdb.dao.mappers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

public class ComputerHandler {
  private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();

  private static final String ID_COLUMN_NAME = "id";
  private static final String NAME_COLUMN_NAME = "name";
  private static final String DATE_INTRODUCED_COLUMN_NAME = "introduced";
  private static final String DATE_DISCONTINUED_COLUMN_NAME = "discontinued";
  private static final String COMPANY_ID_COLUMN_NAME = "company_id";

  public static Computer convert(Map<String, Object> map) {

    Computer computer = new Computer();

    Long idLong = (Long) map.get(ID_COLUMN_NAME);
    String nameStr = (String) map.get(NAME_COLUMN_NAME);
    Timestamp introduceTimestamp = (Timestamp) map.get(DATE_INTRODUCED_COLUMN_NAME);
    Timestamp discontinuedTimestamp = (Timestamp) map.get(DATE_DISCONTINUED_COLUMN_NAME);
    Long companyIdLong = (Long) map.get(COMPANY_ID_COLUMN_NAME);

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
      company = COMPANY_DAO.get(companyIdLong);
    }

    computer.setId(idLong);
    computer.setName(nameStr);
    computer.setIntroduced(introduced);
    computer.setDiscontinued(discontinued);
    computer.setCompany(company);

    return computer;
  }

  public static List<Computer> convert(List<Map<String, Object>> mapList) {

    List<Computer> computers = new ArrayList<>();

    for (Map<String, Object> map : mapList) {
      Computer computer = ComputerHandler.convert(map);
      computers.add(computer);
    }

    return computers;
  }

}
