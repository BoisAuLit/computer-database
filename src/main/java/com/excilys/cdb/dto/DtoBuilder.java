package com.excilys.cdb.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

public class DtoBuilder {

  private static long LONG_FIELD_ABSENT_SPECIFIER = -1L;
  private static String STRING_FIELD_ABSENT_SPECIFIER = "";

  /**
   * Transform a {@code Computer} to a {@code ComputerDto}}
   */
  private static ComputerDto getComputerDto(Computer c) {
    ComputerDto cd = new ComputerDto();
    cd.setId(c.getId());
    cd.setName(c.getName());

    Optional<LocalDate> introducedOpt = c.getIntroduced();
    Optional<LocalDate> discontinuedopt = c.getDiscontinued();
    Optional<Company> companyOpt = c.getCompany();

    String introduced =
        introducedOpt.isPresent() ? introducedOpt.get().toString() : STRING_FIELD_ABSENT_SPECIFIER;
    String discontinued = discontinuedopt.isPresent() ? discontinuedopt.get().toString()
        : STRING_FIELD_ABSENT_SPECIFIER;

    long companyId = LONG_FIELD_ABSENT_SPECIFIER;
    String companyName = STRING_FIELD_ABSENT_SPECIFIER;
    if (companyOpt.isPresent()) {
      Company company = companyOpt.get();
      companyId = company.getId();
      companyName = company.getName();
    }

    cd.setIntroduced(introduced);
    cd.setDiscontinued(discontinued);
    cd.setCompanyId(companyId);
    cd.setCompanyName(companyName);

    return cd;
  }

  /**
   * Transform a {@code List<Computer>} to a {@code List<ComputerDto>}
   */
  private static List<ComputerDto> getComputerDtoList(List<Computer> computers) {

    List<ComputerDto> computerDtos = new ArrayList<>();

    for (Computer c : computers) {
      ComputerDto cd = getComputerDto(c);
      computerDtos.add(cd);
    }

    return computerDtos;
  }

  /**
   * Transform a {@code Company} to a {@code CompanyDto}
   */
  private static CompanyDto getCompnayDto(Company c) {

    CompanyDto cd = new CompanyDto();

    cd.setId(c.getId());
    cd.setName(c.getName());

    return cd;
  }

  /**
   * Transform a {@code List<Company>} to a {@code List<CompanyDto>}
   */
  private static List<CompanyDto> getCompnayDtoList(List<Company> companies) {

    List<CompanyDto> companyDtos = new ArrayList<>();

    for (Company c : companies) {
      CompanyDto cd = getCompnayDto(c);
      companyDtos.add(cd);
    }

    return companyDtos;
  }

  public static Optional<ComputerDto> getComputerDtoById(long id) {

    Optional<Computer> computerOpt = ComputerDao.getInstance().get(id);

    if (!computerOpt.isPresent()) {
      return Optional.empty();
    }

    Computer computer = computerOpt.get();
    ComputerDto computerDto = getComputerDto(computer);
    return Optional.of(computerDto);

  }

  public static List<ComputerDto> getComputerDtos() {
    List<Computer> computers = ComputerDao.getInstance().getAll();
    return getComputerDtoList(computers);
  }

  public static Optional<CompanyDto> getCompanyDtoById(long id) {
    Optional<Company> companyOpt = CompanyDao.getInstance().get(id);

    if (!companyOpt.isPresent()) {
      return Optional.empty();
    }

    Company company = companyOpt.get();
    CompanyDto companyDto = getCompnayDto(company);
    return Optional.of(companyDto);
  }

  public static List<CompanyDto> getCompanyDtos() {
    List<Company> companies = CompanyDao.getInstance().getAll();
    return getCompnayDtoList(companies);
  }
}
