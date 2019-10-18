package com.excilys.cdb.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;


@Component
public class DtoManager {

  private static final long LONG_FIELD_ABSENT_SPECIFIER = -1L;
  private static final String STRING_FIELD_ABSENT_SPECIFIER = "";

  @Autowired
  @Lazy
  private CompanyDao companyDao;
  @Autowired
  @Lazy
  private ComputerDao computerDao;

  // Computer -> ComputerDto
  private ComputerDto getComputerDto(Computer c) {
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

  // List<Computer> -> List<ComputerDto>
  public List<ComputerDto> getComputerDtoList(List<Computer> computers) {

    List<ComputerDto> computerDtos = new ArrayList<>();

    for (Computer c : computers) {
      ComputerDto cd = getComputerDto(c);
      computerDtos.add(cd);
    }

    return computerDtos;
  }

  // Company -> CompanyDto
  private CompanyDto getCompnayDto(Company c) {

    CompanyDto cd = new CompanyDto();

    cd.setId(c.getId());
    cd.setName(c.getName());

    return cd;
  }

  // List<Compnay> -> List<CompanyDto>
  private List<CompanyDto> getCompnayDtoList(List<Company> companies) {

    List<CompanyDto> companyDtos = new ArrayList<>();

    for (Company c : companies) {
      CompanyDto cd = getCompnayDto(c);
      companyDtos.add(cd);
    }

    return companyDtos;
  }

  public Optional<ComputerDto> getComputerDtoById(long id) {

    Optional<Computer> computerOpt = computerDao.get(id);

    if (!computerOpt.isPresent()) {
      return Optional.empty();
    }

    Computer computer = computerOpt.get();
    ComputerDto computerDto = getComputerDto(computer);
    return Optional.of(computerDto);

  }

  public List<ComputerDto> getComputerDtos() {
    List<Computer> computers = computerDao.getAll();
    return getComputerDtoList(computers);
  }

  public Optional<CompanyDto> getCompanyDtoById(long id) {
    Optional<Company> companyOpt = companyDao.get(id);

    if (!companyOpt.isPresent()) {
      return Optional.empty();
    }

    Company company = companyOpt.get();
    CompanyDto companyDto = getCompnayDto(company);
    return Optional.of(companyDto);
  }

  public List<CompanyDto> getCompanyDtos() {
    List<Company> companies = companyDao.getAll();
    return getCompnayDtoList(companies);
  }

  // ComputerDto -> Computer
  public Computer getComputer(ComputerDto computerDto) {
    Computer computer = new Computer();

    computer.setId(computerDto.getId());
    computer.setName(computerDto.getName());

    String introducedStr = computerDto.getIntroduced();
    String discontinuedStr = computerDto.getDiscontinued();
    long companyId = computerDto.getCompanyId();


    if (!StringUtils.isEmpty(introducedStr)) {
      LocalDate introduced = LocalDate.parse(introducedStr, DateTimeFormatter.ISO_LOCAL_DATE);
      computer.setIntroduced(Optional.of(introduced));
    }

    if (!StringUtils.isEmpty(discontinuedStr)) {
      LocalDate discontinued = LocalDate.parse(discontinuedStr, DateTimeFormatter.ISO_LOCAL_DATE);
      computer.setDiscontinued(Optional.of(discontinued));
    }

    if (companyId != LONG_FIELD_ABSENT_SPECIFIER) {
      Company company = new Company();
      company.setId(companyId);
      computer.setCompany(Optional.of(company));
    }

    return computer;
  }

}
