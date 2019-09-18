package com.excilys.cdb.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

public class ComputerListHandler extends BeanListHandler<Computer> {
  // private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();
  private static final String COMPANY_ID_COLUMN_STRING = "company_id";
  private static final String COMPNAY_NAME_COLUMN_STRING = "company_name";

  private ComputerListHandler() {
    super(Computer.class);
  }

  private static class LazyHolder {
    private static final ComputerListHandler INSTANCE = new ComputerListHandler();
  }

  public static ComputerListHandler getInstnace() {
    return LazyHolder.INSTANCE;
  }

  @Override
  public List<Computer> handle(ResultSet rs) throws SQLException {

    List<Computer> computers = new ArrayList<>();
    BasicRowProcessor brp = new BasicRowProcessor(new BeanProcessor());

    while (rs.next()) {
      Computer computer = brp.toBean(rs, Computer.class);
      Company company = null;
      long companyId = rs.getLong(COMPANY_ID_COLUMN_STRING);
      if (!rs.wasNull()) {
        String companyName = rs.getString(COMPNAY_NAME_COLUMN_STRING);
        company = new Company(companyId, companyName);
      }

      Optional<Company> companyOptional = Optional.ofNullable(company);
      computer.setCompany(companyOptional);
      computers.add(computer);
    }
    return computers;
  }
}
