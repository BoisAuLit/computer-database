package com.excilys.cdb.dao.mappers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

public class ComputerHandler extends BeanHandler<Computer> {
  private Connection connection;
  private static BeanHandler<Company> companyHandler = new BeanHandler<>(Company.class);

  public ComputerHandler(Connection connection) {
    super(Computer.class);
    this.connection = connection;
  }

  @Override
  public Computer handle(ResultSet rs) throws SQLException {
    Computer computer = super.handle(rs);

    Long companyId = rs.getLong("company_id");
    QueryRunner runner = new QueryRunner();
    String innerQuery = "SELECT id,name from company WHERE id=?";
    Company company = runner.query(connection, innerQuery, companyHandler, companyId);
    computer.setCompany(Optional.ofNullable(company));

    return computer;
  }
}
