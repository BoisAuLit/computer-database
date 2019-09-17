package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.excilys.cdb.dao.mappers.ComputerHandler;
import com.excilys.cdb.domain.Company;

public class CompanyDao extends Dao<Company> {
  private static final String GET_QUERY = "SELECT id,name FROM company WHERE id=?";
  private static final String GET_ALL_QUERY = "SELECT id,name FROM  company";

  private static final ComputerHandler COMPUTER_HANDLER = new ComputerHandler(connection);
  private static final BeanListHandler<Company> COMPANY_LIST_HANDLER =
      new BeanListHandler<>(Company.class);
  private static final QueryRunner QUERY_RUNNER = new QueryRunner();

  public CompanyDao(ConnectionManager cm) {
    super(cm);
  }

  @Override
  public Company get(long id) {

    try (Connection connection = ConnectionManager.getConnection();) {
      Company company = QUERY_RUNNER.query(connection, GET_QUERY, COMPANY_HANDLER, id);
      return company;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Company> getAll() {
    List<Company> companies = new ArrayList<>();
    try (Connection connection = ConnectionManager.getConnection();) {
      companies = QUERY_RUNNER.query(connection, GET_ALL_QUERY, )

      Company company = QUERY_RUNNER.query(connection, GET_QUERY, COMPANY_HANDLER, id);
      return company;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return companies;
  }

  @Override
  public int save(Company c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int update(Company c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int delete(Company c) {
    throw new UnsupportedOperationException();
  }
}
