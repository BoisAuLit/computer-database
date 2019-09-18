package com.excilys.cdb.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.commons.dbutils.handlers.BeanHandler;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

public class ComputerHandler extends BeanHandler<Computer> {
  private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();
  private static final String COMPANY_ID_COLUMN_STRING = "company_id";

  private ComputerHandler() {
    super(Computer.class);
  }

  private static class LazyHolder {
    private static final ComputerHandler INSTANCE = new ComputerHandler();
  }

  public static ComputerHandler getInstance() {
    return LazyHolder.INSTANCE;
  }

  @Override
  public Computer handle(ResultSet rs) throws SQLException {
    Computer computer = super.handle(rs);
    Long companyId = rs.getLong(COMPANY_ID_COLUMN_STRING);
    Optional<Company> company = COMPANY_DAO.get(companyId);
    computer.setCompany(company);
    return computer;
  }

}
