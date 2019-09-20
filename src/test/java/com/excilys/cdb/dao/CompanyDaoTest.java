package com.excilys.cdb.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.excilys.cdb.domain.Company;

public class CompanyDaoTest {

  private static final String H2_DRIVER_NAME = "org.h2.Driver";
  private static final String H2_CONNECTION_QUERY = "jdbc:h2:~/test";
  private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();

  // todo, to be removed
  private static final String INIT_FILE_PATH =
      "/home/excilys/Bureau/tuto/cdb/computer-database/src/test/resources/init.sql";
  private static final String INSERT_FILE_PAHT =
      "/home/excilys/Bureau/tuto/cdb/computer-database/src/test/resources/insert.sql";

  /**
   * Initialize the database.
   */
  @BeforeAll
  public static void beforeAll() throws SQLException, IOException, ClassNotFoundException {
    Class.forName(H2_DRIVER_NAME);
    System.setProperty("test", "true");
    try (Connection connection = DriverManager.getConnection(H2_CONNECTION_QUERY);
        Reader initFileReader = new BufferedReader(new FileReader(INIT_FILE_PATH));
        Reader insertFileReader = new BufferedReader(new FileReader(INSERT_FILE_PAHT))) {
      ScriptRunner sr = new ScriptRunner(connection);
      sr.runScript(initFileReader);
      sr.runScript(insertFileReader);
    }
  }

  @AfterAll
  public static void afterAll() throws SQLException {
    System.clearProperty("test");
  }

  /**
   * Get a company that exists in the database.
   */
  @Test
  public void testGetSuccess() throws SQLException {
    // Given

    // When
    Optional<Company> companyOpt = COMPANY_DAO.get(1);
    // Then
    assertTrue(companyOpt.isPresent());
    Company company = companyOpt.get();
    assertEquals(1, company.getId());
    assertEquals("Apple Inc.", company.getName());

  }

  @Test
  public void testGetFailure() throws SQLException {
    /* Get a company that doesn't exist in the database. */
    // Given

    // When
    Optional<Company> companyOpt = COMPANY_DAO.get(10);
    // Then
    assertFalse(companyOpt.isPresent());
  }

  @Test
  public void testGetAll() throws SQLException {
    /* Get all the companies from the database. */
    // Given

    // When
    List<Company> companies = COMPANY_DAO.getAll();
    // Then
    assertEquals(4, companies.size());
  }

  @Test
  public void testSave() {
    assertThrows(UnsupportedOperationException.class, () -> COMPANY_DAO.update(null));
  }

  @Test
  public void testUpdate() {
    assertThrows(UnsupportedOperationException.class, () -> COMPANY_DAO.update(null));
  }

  @Test
  public void testDelet() {
    assertThrows(UnsupportedOperationException.class, () -> COMPANY_DAO.delete(null));
  }
}
