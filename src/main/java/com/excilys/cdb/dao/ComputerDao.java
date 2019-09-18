package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.dao.mappers.ComputerHandler;
import com.excilys.cdb.dao.mappers.ComputerListHandler;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;

public class ComputerDao implements Dao<Computer> {

  private static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.ComputerDao");

  // @formatter:off
  private static final String GET_QUERY =
      "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?";

  private static final String GET_ALL_QUERY = "SELECT "
          + "c1.id as computer_id, "
          + "c1.name as computer_name, "
          + "introduced, "
          + "discontinued, "
          + "company_id, "
          + "c2.name as company_name "
          + "FROM computer c1 "
          + "LEFT JOIN company c2 "
          + "ON c1.company_id=c2.id";

  private static final String SAVE_QUERY = "INSERT INTO "
          + "computer(id, name, introduced, discontinued, company_id) "
          + "VALUES(?, ?, ?, ?, ?)";

  private static final String UPDATE_QUERY =
      "UPDATE computer SET "
      + "name=?, "
      + "introduced=?, "
      + "discontinued=?, "
      + "company_id=? WHERE id=?";
  private static final String DELETE_QUERY =
      "DELETE FROM computer WHERE id=?";
    // @formatter:on

  private static final QueryRunner QUERY_RUNNER = new QueryRunner();

  @Override
  public Optional<Computer> get(long id) {
    try (Connection connection = ConnectionManager.getConnection()) {
      ComputerHandler computerHandler = new ComputerHandler(connection);
      Computer computer = QUERY_RUNNER.query(connection, GET_QUERY, computerHandler, id);
      return Optional.ofNullable(computer);
    } catch (SQLException e) {
      logger.error("Error happening when getting company by id", e);
    }
    return Optional.empty();
  }

  @Override
  public List<Computer> getAll() {
    List<Computer> computers = new ArrayList<>();
    try (Connection connection = ConnectionManager.getConnection()) {
      ComputerListHandler computerListHandler = new ComputerListHandler(connection);
      computers = QUERY_RUNNER.query(connection, GET_ALL_QUERY, computerListHandler);
    } catch (SQLException e) {
      logger.error("Error happening when getting all companies", e);
    }
    return computers;
  }

  @Override
  public int save(Computer c) {
    try (Connection connection = ConnectionManager.getConnection()) {
      long companyId;
      int rowsAffected;
      if (c.getCompany().isPresent()) {
        companyId = c.getCompany().get().getId();

        Optional<Company> company = new CompanyDao().get(companyId);

        company.orElseThrow(() -> new SQLException(
            "When you insert a company record, please make sure that the company id is valid!"));

        rowsAffected = QUERY_RUNNER.update(connection, SAVE_QUERY, null, c.getName(),
            c.getIntroduced(), c.getDiscontinued(), companyId);
      } else {
        rowsAffected = QUERY_RUNNER.update(connection, SAVE_QUERY, null, c.getName(),
            c.getIntroduced(), c.getDiscontinued(), null);
      }

      return rowsAffected;
    } catch (SQLException e) {
      logger.error("Error happening when inserting a new record of computer into the database!", e);
    }
    return 0;
  }

  @Override
  public int update(Computer c) {
    try (Connection conneciton = ConnectionManager.getConnection()) {
      int rowsAffected = QUERY_RUNNER.update(conneciton, UPDATE_QUERY, c.getName(),
          c.getIntroduced(), c.getDiscontinued(), c.getCompany(), c.getId());
      return rowsAffected;
    } catch (SQLException e) {
      logger.error("Error happening when inserting update a record of computer in the database!",
          e);
    }
    return 0;
  }

  @Override
  public int delete(Computer c) {
    try (Connection connection = ConnectionManager.getConnection()) {
      int rowsAffected = QUERY_RUNNER.update(connection, DELETE_QUERY, c.getId());
      return rowsAffected;
    } catch (SQLException e) {
      logger.error("Error happenin when deleting a record of computer in the database!", e);
    }

    return 0;
  }

}
