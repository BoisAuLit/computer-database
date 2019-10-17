package com.excilys.cdb.dao.validators;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.dao.ComputerDao.ComputerDaoErrors;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

@Component
public class ComputerValidator {

  private static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.ComputerDao");

  @Autowired
  private ComputerDao computerDao;
  @Autowired
  private CompanyDao companyDao;


  private boolean checkDatesValidity(Computer c) {

    Optional<LocalDate> introducedOpt = c.getIntroduced();
    Optional<LocalDate> discontinuedOpt = c.getDiscontinued();

    if (introducedOpt.isPresent() && discontinuedOpt.isPresent()) {
      LocalDate introduced = introducedOpt.get();
      LocalDate discontinued = discontinuedOpt.get();

      if (!discontinued.isAfter(introduced)) {
        return false;
      }
    }

    return true;
  }

  private boolean checkIdValidity(Computer c) {
    Optional<Computer> computerOpt = computerDao.get(c.getId());
    return computerOpt.isPresent();
  }

  private boolean checkCompanyValidity(Computer c) {

    Optional<Company> companyOpt = c.getCompany();
    if (!companyOpt.isPresent()) {
      return true;
    }

    long companyId = companyOpt.get().getId();

    return companyDao.get(companyId).isPresent();
  }

  public boolean checkSaveComputerValidity(Computer c) {
    if (!Objects.nonNull(c)) {
      logger.error(ComputerDaoErrors.INVALID_COMPUTER_ARGUEMNT_ERROR.getMessage());
      return false;
    }

    if (StringUtils.isEmpty(c.getName())) {
      logger.error(ComputerDaoErrors.LACK_NAME_ERROR.getMessage());
      return false;
    }

    if (!checkDatesValidity(c)) {
      logger.error(ComputerDaoErrors.INVALID_DATE_ERROR.getMessage());
      return false;
    }

    if (!checkCompanyValidity(c)) {
      logger.error(ComputerDaoErrors.INVALID_COMPANY_ERROR.getMessage());
      return false;
    }

    return true;
  }

  public boolean checkUpdateComputerValidity(Computer c) {
    if (!checkSaveComputerValidity(c)) {
      return false;
    }

    if (!checkIdValidity(c)) {
      logger.error(ComputerDaoErrors.INVALID_ID_ERROR.getMessage());
      return false;
    }

    return true;
  }

  public boolean checkDeleteComputerValidity(Computer c) {

    if (!Objects.nonNull(c)) {
      logger.error(ComputerDaoErrors.INVALID_COMPUTER_ARGUEMNT_ERROR.getMessage());
      return false;
    }

    if (!checkIdValidity(c)) {
      logger.error(ComputerDaoErrors.INVALID_ID_ERROR.getMessage());
      return false;
    }

    return true;
  }
}
