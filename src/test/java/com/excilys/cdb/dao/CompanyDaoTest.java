package com.excilys.cdb.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.utils.TestUtils;

public class CompanyDaoTest {

  private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();

  private static final Company VALID_COMPANY;
  static {
    VALID_COMPANY = new Company();
    VALID_COMPANY.setId(1);
    VALID_COMPANY.setName("Apple Inc.");
  }

  /**
   * Initialize the database.
   */
  @BeforeAll
  public static void beforeAll() throws SQLException, IOException, ClassNotFoundException {
    TestUtils.setupDatabase();
  }

  @AfterAll
  public static void afterAll() throws SQLException {
    TestUtils.clear();
  }

  @Test
  public void testGetSuccess() throws SQLException {
    /* Get a company that exists in the database. */
    // Given

    Optional<Company> companyOpt = COMPANY_DAO.get(1);
    // Then
    assertTrue(companyOpt.isPresent());
    Company company = companyOpt.get();
    assertEquals(VALID_COMPANY, company);
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
