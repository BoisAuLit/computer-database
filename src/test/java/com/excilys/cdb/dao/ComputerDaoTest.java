package com.excilys.cdb.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;
import com.excilys.cdb.utils.TestUtils;

public class ComputerDaoTest {
  private static final ComputerDao COMPUTER_DAO = ComputerDao.getInstance();

  /* Contants to test the save() method of ComputerDao */
  private static final long VALID_COMPUTER_ID = 17;
  private static final Computer VALID_COMPUTER;
  // This is a valid computer to be inserted in the database.
  private static final Computer COMPUTER_TO_SAVE_VALID;
  private static final Computer COMPUTER_TO_SAVE_WITHOUT_NAME;
  // The "discontinued date" is earlier than the "introduced date" for this computer.
  private static final Computer COMPUTER_TO_SAVE_DATE_ERROR;
  private static final long SAVE_EXPCETED_ID = 21;

  /* Contants to test the update() method of ComputerDao */
  private static final Computer COMPUTER_TO_UPDATE_NOT_EXISTENT;

  private static void testStrictlyEqauls(Computer c1, Computer c2) {
    assertEquals(c1.getId(), c2.getId());
    assertEquals(c1.getName(), c2.getName());
    assertEquals(c1.getIntroduced(), c2.getIntroduced());
    assertEquals(c1.getDiscontinued(), c2.getDiscontinued());
    assertTrue(c1.equals(c2));
  }

  static {
    COMPUTER_TO_SAVE_WITHOUT_NAME = new Computer();

    VALID_COMPUTER = new Computer();
    VALID_COMPUTER.setId(17L);
    VALID_COMPUTER.setName("Apple III Plus");
    VALID_COMPUTER.setIntroduced(Optional.of(LocalDate.of(1983, 12, 1)));
    VALID_COMPUTER.setDiscontinued(Optional.of(LocalDate.of(1984, 4, 1)));
    VALID_COMPUTER.setCompany(Optional.of(new Company(1, "Apple Inc.")));

    COMPUTER_TO_SAVE_VALID = new Computer();
    COMPUTER_TO_SAVE_VALID.setId(21L);
    COMPUTER_TO_SAVE_VALID.setName("Macbook Pro Mid-2015");
    COMPUTER_TO_SAVE_VALID.setIntroduced(Optional.of(LocalDate.of(2015, 6, 1)));
    COMPUTER_TO_SAVE_VALID.setDiscontinued(Optional.of(LocalDate.of(2016, 6, 1)));
    COMPUTER_TO_SAVE_VALID.setCompany(Optional.of(new Company(1, "Apple Inc.")));

    COMPUTER_TO_SAVE_DATE_ERROR = new Computer();
    COMPUTER_TO_SAVE_DATE_ERROR.setName("Macbook Pro Mid-2015");
    COMPUTER_TO_SAVE_DATE_ERROR.setIntroduced(Optional.of(LocalDate.of(2015, 6, 1)));
    COMPUTER_TO_SAVE_DATE_ERROR.setDiscontinued(Optional.of(LocalDate.of(2013, 6, 1)));
    COMPUTER_TO_SAVE_DATE_ERROR.setCompany(Optional.of(new Company(1, "Apple Inc.")));

    COMPUTER_TO_UPDATE_NOT_EXISTENT = new Computer();
    COMPUTER_TO_UPDATE_NOT_EXISTENT.setId(100L);
  }

  @BeforeAll
  public static void beforeAll() throws SQLException, IOException, ClassNotFoundException {
    TestUtils.setupDatabase();
  }

  @AfterAll
  public static void afterAll() throws SQLException {
    TestUtils.clear();
  }

  @Test
  public void testGetSuccess() {
    /* Get a compmuter that exists in the database. */
    // Given
    // When
    Optional<Computer> computerOpt = COMPUTER_DAO.get(VALID_COMPUTER_ID);
    assertTrue(computerOpt.isPresent());
    Computer computer = computerOpt.get();
    // Then
    System.out.println(VALID_COMPUTER);
    System.out.println(computer);

    boolean result = VALID_COMPUTER.StrictlyEqauls(computer);
    System.out.println("The result is: " + result);

    testStrictlyEqauls(VALID_COMPUTER, computer);
  }

  @Test
  public void testGetFailure() {
    /* Get a compmuter that doesn't not exist in the database. */

    // Given
    // When
    Optional<Computer> computer = COMPUTER_DAO.get(-1);
    // Then
    assertFalse(computer.isPresent());
  }

  @Test
  public void getAllTest() {
    /* Get all the computers from the database. */
    // Given

    // When
    List<Computer> computers = COMPUTER_DAO.getAll();
    Computer computer = null;
    for (Computer com : computers) {
      if (com.getId() == VALID_COMPUTER_ID) {
        computer = com;
        break;
      }
    }
    assertTrue(Objects.nonNull(computer));
    // Then
    assertEquals(20, computers.size());
    testStrictlyEqauls(VALID_COMPUTER, computer);
  }

  @Test
  public void testSave_1() {
    /* Create a computer without name, which is not allowed. */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.save(COMPUTER_TO_SAVE_WITHOUT_NAME);
    // Then
    assertEquals(0, rowsAffected);
  }

  @Test
  public void testSave_2() {
    /**
     * Create a computer for which the "discontinued date"
     * is earlier than the "introduced date".
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.save(COMPUTER_TO_SAVE_DATE_ERROR);
    // Then
    assertEquals(0, rowsAffected);
  }

  @Test
  public void testSave_3() {
    /* Create a valid computer */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.save(COMPUTER_TO_SAVE_VALID);
    Optional<Computer> computerOpt = COMPUTER_DAO.get(SAVE_EXPCETED_ID);
    // Then
    assertEquals(1, rowsAffected);
    assertTrue(computerOpt.isPresent());
    testStrictlyEqauls(COMPUTER_TO_SAVE_VALID, computerOpt.get());
  }

  @Test
  public void testUpdate_1() {
    /* Update a computer that DOES NOT EXIST in the database */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.update(COMPUTER_TO_UPDATE_NOT_EXISTENT);
    // Then
    assertEquals(0, rowsAffected);
  }

  @Test
  public void testUpdate_2() {
    /* Update a coputer that EXIST in the database */
  }

  @Test
  public void testDelete() {

  }

}
