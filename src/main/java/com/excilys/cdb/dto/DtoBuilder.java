package com.excilys.cdb.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

public class DtoBuilder {

  private static long LONG_FIELD_ABSENT_SPECIFIER = -1L;
  private static String STRING_FIELD_ABSENT_SPECIFIER = "";

  public static ComputerDto getComputerDto(Computer c) {
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

  public static List<ComputerDto> getComputerDtoList(List<Computer> computers) {

    List<ComputerDto> computerDtos = new ArrayList<>();

    for (Computer c : computers) {
      ComputerDto cd = getComputerDto(c);
      computerDtos.add(cd);
    }

    return computerDtos;
  }

  public static CompanyDto getCompnayDto(Company c) {

    CompanyDto cd = new CompanyDto();

    cd.setId(c.getId());
    cd.setName(c.getName());

    return cd;
  }

  public static List<CompanyDto> getCompnayDtoList(List<Company> companies) {

    List<CompanyDto> companyDtos = new ArrayList<>();

    for (Company c : companies) {
      CompanyDto cd = getCompnayDto(c);
      companyDtos.add(cd);
    }

    return companyDtos;
  }
}
