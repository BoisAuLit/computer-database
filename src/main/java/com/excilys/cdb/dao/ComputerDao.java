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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.dao.mappers.ComputerHandler;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;
import com.excilys.cdb.utils.ConnectionUtils;

public class ComputerDao implements Dao<Computer> {

  private static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.ComputerDao");

  private static final String GENERAL_ERROR_PREFIX = "Error happend when ";

  public enum ComputerDaoErrors {
    // General errors
    GET_ERROR(GENERAL_ERROR_PREFIX + "getting computer by id!"),
    GET_ALL_ERROR(GENERAL_ERROR_PREFIX + "getting all computers!"),
    SAVE_ERROR(GENERAL_ERROR_PREFIX + "saving a computer!"),
    UPDATE_ERROR(GENERAL_ERROR_PREFIX + "updating a computer!"),
    DELETE_ERROR(GENERAL_ERROR_PREFIX + "deleting a computer!"),

    // save() / update() / delete() errors
    INVALID_COMPUTER_ARGUEMNT_ERROR("The arguemnt computer object cannot be null!"),
    // save() / update() errors
    LACK_NAME_ERROR("The name should be provided!"),
    INVALID_COMPANY_ERROR("The company should exist in the database!"),
    INVALID_DATE_ERROR("The discontinued date should be later than the introduced date!"),
    // update() / delete() errors
    INVALID_ID_ERROR("The id doesn't exist in the database!");

    private String message;

    private ComputerDaoErrors(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }

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
      logger.error(ComputerDaoErrors.GET_ERROR.getMessage(), e);
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
      logger.error(ComputerDaoErrors.GET_ALL_ERROR.getMessage(), e);
    }
    return computers;
  }

  @Override
  public int save(Computer c) {

    if (!checkSaveComputerValidity(c)) {
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
      logger.error(ComputerDaoErrors.SAVE_ERROR.getMessage(), e);
    }
    return 0;
  }

  @Override
  public int update(Computer c) {

    if (!checkUpdateComputerValidity(c)) {
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
      logger.error(ComputerDaoErrors.UPDATE_ERROR.getMessage(), e);
    }
    return 0;
  }

  @Override
  public int delete(Computer c) {

    if (!checkDeleteComputerValidity(c)) {
      return 0;
    }

    try (Connection connection = ConnectionUtils.getConnection()) {
      int rowsAffected = QUERY_RUNNER.update(connection, DELETE_QUERY, c.getId());
      return rowsAffected;
    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.DELETE_ERROR.getMessage(), e);
    }

    return 0;
  }

  /**
   * Following are the methods to check argument validity for
   * save(), update() & delete() method
   */

  private static boolean checkDatesValidity(Computer c) {

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

  private static boolean checkIdValidity(Computer c) {
    Optional<Computer> computerOpt = ComputerDao.getInstance().get(c.getId());
    return computerOpt.isPresent();
  }

  private static boolean checkCompanyValidity(Computer c) {

    Optional<Company> companyOpt = c.getCompany();
    if (!companyOpt.isPresent()) {
      return true;
    }

    long companyId = companyOpt.get().getId();

    return CompanyDao.getInstance().get(companyId).isPresent();
  }

  private static boolean checkSaveComputerValidity(Computer c) {
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

  private static boolean checkUpdateComputerValidity(Computer c) {
    if (!checkSaveComputerValidity(c)) {
      return false;
    }

    if (!checkIdValidity(c)) {
      logger.error(ComputerDaoErrors.INVALID_ID_ERROR.getMessage());
      return false;
    }

    return true;
  }

  private static boolean checkDeleteComputerValidity(Computer c) {

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
