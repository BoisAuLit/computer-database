package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.dao.mappers.ComputerHandler;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;
import com.excilys.cdb.utils.ConnectionUtils;

public class ComputerDao implements Dao<Computer> {

  private static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.ComputerDao");

//@formatter:off
  private static final String GET_QUERY =
      "SELECT id, name, introduced, discontinued, company_id "
      + "FROM computer WHERE id=?";

  private static final String GET_ALL_QUERY = "SELECT "
      + "c1.id as id, "
      + "c1.name as name, "
      + "introduced, "
      + "discontinued, "
      + "company_id, "
      + "c2.name as company_name "
      + "FROM computer c1 "
      + "LEFT JOIN company c2 "
      + "ON c1.company_id=c2.id";

  private static final String SAVE_QUERY =
      "INSERT INTO "
      + "computer(id, name, introduced, discontinued, company_id) "
      + "VALUES(?, ?, ?, ?, ?)";

  private static final String UPDATE_QUERY =
      "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";

  private static final String DELETE_QUERY = "DELETE FROM computer WHERE id=?";
//@formatter:on

  // Error messages
  private static final String ERROR_MESSAGE_PREFIX = "Error happpend when ";
  private static final String GET_ERROR = ERROR_MESSAGE_PREFIX + "getting computer by id";
  private static final String GET_ALL_ERROR = ERROR_MESSAGE_PREFIX + "getting all computers";
  private static final String SAVE_ERROR = ERROR_MESSAGE_PREFIX + "creating a record of computer";

  private static final String COMPNAY_ID_NOT_VALID_ERROR =
      "When you insert a company record, please make sure that the computer id is valid!";
  private static final String UPDATE_ERROR =
      ERROR_MESSAGE_PREFIX + "updating a record of computer!";
  private static final String DELETE_ERROR =
      ERROR_MESSAGE_PREFIX + "deleting a record of computer!";

  private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();
  private static final QueryRunner QUERY_RUNNER = new QueryRunner();

  private ComputerDao() {

  }

  private static class LazyHolder {
    private static final ComputerDao INSTANCE = new ComputerDao();

  }

  public static ComputerDao getInstance() {
    return LazyHolder.INSTANCE;
  }

  @Override
  public Optional<Computer> get(long id) {
    try (Connection connection = ConnectionUtils.getConnection()) {

      Map<String, Object> map = QUERY_RUNNER.query(connection, GET_QUERY, new MapHandler(), id);
      if (Objects.nonNull(map)) {
        return Optional.of(ComputerHandler.convert(map));
      }

      return Optional.empty();

    } catch (SQLException e) {
      logger.error(GET_ERROR, e);
    }
    return Optional.empty();
  }

  @Override
  public List<Computer> getAll() {
    List<Computer> computers = new ArrayList<>();

    try (Connection connection = ConnectionUtils.getConnection()) {

      List<Map<String, Object>> mapList =
          QUERY_RUNNER.query(connection, GET_ALL_QUERY, new MapListHandler());
      computers = ComputerHandler.convert(mapList);

      return computers;
    } catch (SQLException e) {
      logger.error(GET_ALL_ERROR, e);
    }
    return computers;
  }

  @Override
  public int save(Computer c) {
    try (Connection connection = ConnectionUtils.getConnection()) {
      long companyId;
      int rowsAffected;
      if (c.getCompany().isPresent()) {
        companyId = c.getCompany().get().getId();

        Optional<Company> company = COMPANY_DAO.get(companyId);

        company.orElseThrow(() -> new SQLException(COMPNAY_ID_NOT_VALID_ERROR));

        rowsAffected = QUERY_RUNNER.update(connection, SAVE_QUERY, null, c.getName(),
            c.getIntroduced(), c.getDiscontinued(), companyId);
      } else {
        rowsAffected = QUERY_RUNNER.update(connection, SAVE_QUERY, null, c.getName(),
            c.getIntroduced(), c.getDiscontinued(), null);
      }

      return rowsAffected;
    } catch (SQLException e) {
      logger.error(SAVE_ERROR, e);
    }
    return 0;
  }

  @Override
  public int update(Computer c) {
    try (Connection conneciton = ConnectionUtils.getConnection()) {
      int rowsAffected = QUERY_RUNNER.update(conneciton, UPDATE_QUERY, c.getName(),
          c.getIntroduced(), c.getDiscontinued(), c.getCompany(), c.getId());
      return rowsAffected;
    } catch (SQLException e) {
      logger.error(UPDATE_ERROR, e);
    }
    return 0;
  }

  @Override
  public int delete(Computer c) {
    try (Connection connection = ConnectionUtils.getConnection()) {
      int rowsAffected = QUERY_RUNNER.update(connection, DELETE_QUERY, c.getId());
      return rowsAffected;
    } catch (SQLException e) {
      logger.error(DELETE_ERROR, e);
    }

    return 0;
  }

}
