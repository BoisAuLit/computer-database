package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.dao.mappers.ComputerHandler;
import com.excilys.cdb.dao.validators.ComputerValidator;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.ComputerPage;
import com.excilys.cdb.dto.DtoManager;

@Repository
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
    DELETE_ALL_ERROR(
        GENERAL_ERROR_PREFIX + "deleting a list of computer!"),


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

  private static final String GET_PARTIAL_QUERY_PREFIX =
      "SELECT id, name, introduced, discontinued, company_id "
          + "FROM computer WHERE name LIKE '%";

  private static final String GET_PARTIAL_QUERY_SUFFIX = "%' LIMIT ? OFFSET ?";

  // private static final String GET_PARTIAL_QUERY =
  // "SELECT id, name, introduced, discontinued, company_id "
  // + "FROM computer WHERE name LIKE ? LIMIT ? OFFSET ?";

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

  private static final String DELETE_ALL_QUERY_PREFIX = "DELETE FROM computer WHERE id IN ";

  private static final QueryRunner QUERY_RUNNER = new QueryRunner();

  @Autowired
  @Lazy
  private DtoManager dtoManager;
  @Autowired
  @Lazy
  private ComputerValidator computerValidator;
  @Autowired
  @Lazy
  private ComputerHandler computerHandler;


  private String getCountQuery(String nameToFind) {
    return COUNT_COMPUTERS_QUERY + " WHERE name like '%" + nameToFind + "%'";
  }

  private String getPartialQuery(String nameToFind) {
    return GET_PARTIAL_QUERY_PREFIX + nameToFind + GET_PARTIAL_QUERY_SUFFIX;
  }

  @Override
  public Optional<Computer> get(long id) {
    try (Connection connection = DataSource.getConnection()) {

      Map<String, Object> map = QUERY_RUNNER.query(connection, GET_QUERY, new MapHandler(), id);
      if (Objects.nonNull(map)) {
        return Optional.of(computerHandler.convert(map));
      }

      return Optional.empty();

    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.GET_ERROR.getMessage(), e);
    }
    return Optional.empty();
  }

  // Count the number of all computers
  public long countComputers(String nameToFind) {
    try (Connection connection = DataSource.getConnection()) {
      long nbComputers =
          QUERY_RUNNER.query(connection, getCountQuery(nameToFind), new ScalarHandler<Long>());
      return nbComputers;
    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.COUNT_COMPUTERS_ERROR.getMessage(), e);
    }
    return 0;
  }

  // For pagination
  public ComputerPage getPartial(String nameToFind, int limit, int offset) {

    ComputerPage computerPage = new ComputerPage();

    int nbComputers = (int) countComputers(nameToFind);

    List<Computer> computers = new ArrayList<>();
    try (Connection connection = DataSource.getConnection()) {
      List<Map<String, Object>> mapList =
          QUERY_RUNNER.query(connection, getPartialQuery(nameToFind), new MapListHandler(), limit,
              offset);

      computers = computerHandler.convert(mapList);

      List<ComputerDto> computerDtos = dtoManager.getComputerDtoList(computers);


      int currentPage = offset / limit + 1;


      int totalPages;
      if (nbComputers % limit == 0) {
        totalPages = nbComputers / limit;
      } else {
        totalPages = nbComputers / limit + 1;
      }

      computerPage.setCurrentPage(currentPage);
      computerPage.setTotalPages(totalPages);
      computerPage.setNbAllComputers(nbComputers);
      computerPage.setComputerDtos(computerDtos);
      computerPage.setCurrentMaxElementsPerPage(limit);
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

    try (Connection connection = DataSource.getConnection()) {

      List<Map<String, Object>> mapList =
          QUERY_RUNNER.query(connection, GET_ALL_QUERY, new MapListHandler());
      computers = computerHandler.convert(mapList);

      return computers;
    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.GET_ALL_ERROR.getMessage(), e);
    }
    return computers;
  }

  @Override
  public int save(Computer c) {

    if (!computerValidator.checkSaveComputerValidity(c)) {
      return 0;
    }

    /* We won't use the id of the compnay object passed as argument, we will use auto-increment */
    try (Connection connection = DataSource.getConnection()) {

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

    if (!computerValidator.checkUpdateComputerValidity(c)) {
      return 0;
    }

    try (Connection connection = DataSource.getConnection()) {
      Optional<Company> companyOpt = c.getCompany();
      Optional<LocalDate> introducedOpt = c.getIntroduced();
      Optional<LocalDate> discontinuedOpt = c.getDiscontinued();

      Object introduced = null;
      if (introducedOpt.isPresent()) {
        LocalDate ld = introducedOpt.get();
        ZonedDateTime zdt = ld.atStartOfDay(ZoneId.of("Europe/Paris"));
        Timestamp ts = Timestamp.from(zdt.toInstant());
        introduced = ts;
      }

      Object discontinued = null;
      if (discontinuedOpt.isPresent()) {
        LocalDate ld = discontinuedOpt.get();
        ZonedDateTime zdt = ld.atStartOfDay(ZoneId.of("Europe/Paris"));
        Timestamp ts = Timestamp.from(zdt.toInstant());
        discontinued = ts;
      }

      int rowsAffected = QUERY_RUNNER.update(connection, UPDATE_QUERY, c.getName(),
          introduced, discontinued,
          companyOpt.isPresent() ? companyOpt.get().getId() : null, c.getId());

      return rowsAffected;
    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.UPDATE_ERROR.getMessage(), e);
    }
    return 0;
  }

  @Override
  public int delete(Computer c) {

    if (!computerValidator.checkDeleteComputerValidity(c)) {
      return 0;
    }

    try (Connection connection = DataSource.getConnection()) {
      int rowsAffected = QUERY_RUNNER.update(connection, DELETE_QUERY, c.getId());
      return rowsAffected;
    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.DELETE_ERROR.getMessage(), e);
    }

    return 0;
  }

  private static String composeDeleteAlQuery(List<String> ids) {
    return DELETE_ALL_QUERY_PREFIX + "(" +
        String.join(",", ids)
        + ")";

  }

  public int batchDelete(List<String> ids) {

    try (Connection connection = DataSource.getConnection()) {
      int rowsAffected = QUERY_RUNNER.update(connection, composeDeleteAlQuery(ids));
      return rowsAffected;
    } catch (SQLException e) {
      logger.error(ComputerDaoErrors.DELETE_ALL_ERROR.getMessage(), e);
    }

    return 0;
  }

}
