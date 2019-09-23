package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
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

  public static final String ID_COLUMN_NAME = "id";
  public static final String NAME_COLUMN_NAME = "name";
  public static final String DATE_INTRODUCED_COLUMN_NAME = "introduced";
  public static final String DATE_DISCONTINUED_COLUMN_NAME = "discontinued";
  public static final String COMPANY_ID_COLUMN_NAME = "company_id";

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

  /* Error messages */
  private static final String ERROR_MESSAGE_PREFIX = "Error happpend when ";
  private static final String GET_ERROR = ERROR_MESSAGE_PREFIX + "getting computer by id";
  private static final String GET_ALL_ERROR = ERROR_MESSAGE_PREFIX + "getting all computers";
  private static final String SAVE_ERROR = ERROR_MESSAGE_PREFIX + "creating a record of computer";

  // Error messages for saving a computer
  private static final String SAVE_COMPUTER_CANNOT_BE_NULL_ERROR =
      "The computer to be saved cannot be null!";
  private static final String SAVE_LACK_NAME_ERROR = "When crearting a new computer, "
      + "you must provide the computer's name!";
  private static final String SAVE_DATE_ERROR =
      "When creating a computer, the discontinued date must be later than introduced date!";

  // Error messages for updating a computer
  private static final String UPDATE_COMPUTER_CANNOT_BE_NULL_ERROR =
      "The computer to be updated cannot be null!";
  private static final String UPDATE_COMPUTER_NAME_ERROR =
      "The name of the computer to be udpated CANNOT BE NULL!";
  private static final String UPDATE_DATE_ERROR =
      "When updating a computer, the discontinued date must be later than introduced date!";

  private static final String UPDATE_ERROR =
      ERROR_MESSAGE_PREFIX + "updating a record of computer!";
  private static final String DELETE_ERROR =
      ERROR_MESSAGE_PREFIX + "deleting a record of computer!";

  private static final QueryRunner QUERY_RUNNER = new QueryRunner();

  private ComputerDao() {

  }

  private static class LazyHolder {
    private static final ComputerDao INSTANCE = new ComputerDao();

  }

  public static ComputerDao getInstance() {
    return LazyHolder.INSTANCE;
  }

  private static boolean checkDates(Computer c) {

    Optional<LocalDate> introducedOpt = c.getIntroduced();
    Optional<LocalDate> discontinuedOpt = c.getDiscontinued();

    if (introducedOpt.isPresent() && discontinuedOpt.isPresent()) {
      LocalDate introduced = introducedOpt.get();
      LocalDate discontinued = discontinuedOpt.get();
      if (!discontinued.isAfter(introduced)) {
        logger.error(SAVE_DATE_ERROR);
        return false;
      }
    }

    return true;
  }

  private static boolean checkComputerValidity(Computer c) {
    if (!Objects.nonNull(c)) {
      logger.error(SAVE_COMPUTER_CANNOT_BE_NULL_ERROR);
      return false;
    }

    if (!Objects.nonNull(c.getName())) {
      logger.error(SAVE_LACK_NAME_ERROR);
      return false;
    }

    return checkDates(c);
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

    if (!checkComputerValidity(c)) {
      return 0;
    }

    /* We won't use the id of the compnay object passed as argument, we will use auto-increment */
    try (Connection connection = ConnectionUtils.getConnection()) {

      Optional<Company> companyOpt = c.getCompany();

      return QUERY_RUNNER.update(connection, SAVE_QUERY,
          null,
          c.getName(),
          c.getIntroduced().orElse(null),
          c.getDiscontinued().orElse(null),
          companyOpt.isPresent() ? companyOpt.get().getId() : null);
    } catch (SQLException e) {
      logger.error(SAVE_ERROR, e);
    }
    return 0;
  }

  @Override
  public int update(Computer c) {

    if (!checkComputerValidity(c)) {
      return 0;
    }

    try (Connection conneciton = ConnectionUtils.getConnection()) {

      Optional<Company> companyOpt = c.getCompany();

      int rowsAffected = QUERY_RUNNER.update(conneciton, UPDATE_QUERY,
          c.getName(),
          c.getIntroduced().orElse(null),
          c.getDiscontinued().orElse(null),
          companyOpt.isPresent() ? companyOpt.get().getId() : null,
          c.getId());
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
