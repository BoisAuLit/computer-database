package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.domain.Company;

public class CompanyDao implements Dao<Company> {
  private static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.CompanyDao");

  static final String GET_QUERY = "SELECT id,name FROM company WHERE id=?";
  private static final String GET_ALL_QUERY = "SELECT id,name FROM company";

  static final BeanHandler<Company> COMPANY_HANDLER = new BeanHandler<>(Company.class);
  private static final BeanListHandler<Company> COMPANY_LIST_HANDLER =
      new BeanListHandler<>(Company.class);
  private static final QueryRunner QUERY_RUNNER = new QueryRunner();

  @Override
  public Optional<Company> get(long id) {
    try (Connection connection = ConnectionManager.getConnection();) {
      Company company = QUERY_RUNNER.query(connection, GET_QUERY, COMPANY_HANDLER, id);
      return Optional.ofNullable(company);
    } catch (SQLException e) {
      logger.error("Error happening when getting company by id", e);
    }
    return Optional.<Company>empty();

  }

  @Override
  public List<Company> getAll() {
    List<Company> companies = new ArrayList<>();
    try (Connection connection = ConnectionManager.getConnection();) {
      companies = QUERY_RUNNER.query(connection, GET_ALL_QUERY, COMPANY_LIST_HANDLER);
      return companies;
    } catch (SQLException e) {
      logger.error("Error happening when getting all companies", e);
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
