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
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.dao.mappers.ComputerHandler;
import com.excilys.cdb.dao.validators.ComputerValidator;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.ComputerPage;
import com.excilys.cdb.dto.DtoManager;
import com.excilys.cdb.utils.ConnectionUtils;

public class ComputerDao implements Dao<Computer> {

  private static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.ComputerDao");

  private static final String GENERAL_ERROR_PREFIX = "Error happend when ";

  public enum ComputerDaoErrors {
    // General errors
    GET_ERROR(
        GENERAL_ERROR_PREFIX + "getting computer by id!"),
    COUNT_COMPUTERS_ERROR(
        GENERAL_ERROR_PREFIX + "counting the number of all computers!"),
    GET_PARTIAL_ERROR(
        GENERAL_ERROR_PREFIX + "getting a page of computers!"),
    GET_ALL_ERROR(
        GENERAL_ERROR_PREFIX + "getting all computers!"),
    SAVE_ERROR(
        GENERAL_ERROR_PREFIX + "saving a computer!"),
    UPDATE_ERROR(
        GENERAL_ERROR_PREFIX + "updating a computer!"),
    DELETE_ERROR(
        GENERAL_ERROR_PREFIX + "deleting a computer!"),

    // save() / update() / delete() errors
    INVALID_COMPUTER_ARGUEMNT_ERROR("The arguemnt computer object cannot be null!"),
    // save() / update() errors
    LACK_NAME_ERROR("The name should be provided!"),
    INVALID_COMPANY_ERROR(
        "The company should exist in the database!"),
    INVALID_DATE_ERROR(
        "The discontinued date should be later than the introduced date!"),
    // update() / delete() errors
    INVALID_ID_ERROR("The id doesn't exist in the database!");

    String message;

    ComputerDaoErrors(String message) {
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

  private static final String COUNT_COMPUTERS_QUERY =
      "SELECT COUNT(id) AS nb_computers FROM computer";

  private static final String GET_PARTIAL_QUERY =
      "SELECT id, name, introduced, discontinued, company_id "
          + "FROM computer LIMIT ? OFFSET ?";

  private static final String GET_ALL_QUERY =
      "SELECT "
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

  // Count the number of all computers
  public long countComputers() {
    try (Connection connection = ConnectionUtils.getConnection()) {
      long nbComputers =
          QUERY_RUNNER.query(connection, COUNT_COMPUTERS_QUERY, new ScalarHandler<Long>());
      return nbComputers;
    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.COUNT_COMPUTERS_ERROR.getMessage(), e);
    }
    return 0;
  }

  // For pagination
  public ComputerPage getPartial(int limit, int offset) {

    ComputerPage computerPage = new ComputerPage();

    int nbComputers = (int) countComputers();


    List<Computer> computers = new ArrayList<>();
    try (Connection connection = ConnectionUtils.getConnection()) {

      List<Map<String, Object>> mapList =
          QUERY_RUNNER.query(connection, GET_PARTIAL_QUERY, new MapListHandler(), limit, offset);

      computers = ComputerHandler.convert(mapList);

      List<ComputerDto> computerDtos = DtoManager.getComputerDtoList(computers);

      int firstElementIndex = offset + 1;



      int currentPage = firstElementIndex / limit + 1;
      int totalPages;
      if (nbComputers % 10 == 0) {
        totalPages = nbComputers / 10;
      } else {
        totalPages = nbComputers / limit + 1;
      }

      computerPage.setCurrentPage(currentPage);
      computerPage.setTotalPages(totalPages);
      computerPage.setNbAllComptuers(nbComputers);
      computerPage.setComputerDtos(computerDtos);
      computerPage.computeBeginEndPages();

      return computerPage;
    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.GET_PARTIAL_ERROR.getMessage(), e);
    }
    return null;
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

    if (!ComputerValidator.checkSaveComputerValidity(c)) {
      return 0;
    }

    /* We won't use the id of the compnay object passed as argument, we will use auto-increment */
    try (Connection connection = ConnectionUtils.getConnection()) {

      Optional<Company> companyOpt = c.getCompany();

      return QUERY_RUNNER.update(connection, SAVE_QUERY, null, c.getName(),
          c.getIntroduced().orElse(null), c.getDiscontinued().orElse(null),
          companyOpt.isPresent() ? companyOpt.get().getId() : null);
    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.SAVE_ERROR.getMessage(), e);
    }
    return 0;
  }

  @Override
  public int update(Computer c) {

    if (!ComputerValidator.checkUpdateComputerValidity(c)) {
      return 0;
    }

    try (Connection conneciton = ConnectionUtils.getConnection()) {

      Optional<Company> companyOpt = c.getCompany();

      int rowsAffected = QUERY_RUNNER.update(conneciton, UPDATE_QUERY, c.getName(),
          c.getIntroduced().orElse(null), c.getDiscontinued().orElse(null),
          companyOpt.isPresent() ? companyOpt.get().getId() : null, c.getId());
      return rowsAffected;
    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.UPDATE_ERROR.getMessage(), e);
    }
    return 0;
  }

  @Override
  public int delete(Computer c) {

    if (!ComputerValidator.checkDeleteComputerValidity(c)) {
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
}
