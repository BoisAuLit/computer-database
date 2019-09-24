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
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.dao.ComputerDao.ComputerDaoErrors;
import com.excilys.cdb.domain.Company;
import com.excilys.cdb.domain.Computer;
import com.excilys.cdb.utils.TestUtils;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@TestMethodOrder(OrderAnnotation.class)
public class ComputerDaoTest {
  private static final ComputerDao COMPUTER_DAO = ComputerDao.getInstance();

  /**
   * Testing loggers
   */
  private static Logger logger;
  private static ListAppender<ILoggingEvent> listAppender;
  private static List<ILoggingEvent> logsList;

  /* Constants to test the get() & getAll() methods. */
  private static final long INVALID_COMPUTER_ID;
  private static final long VALID_COMPUTER_ID;
  private static final Computer VALID_COMPUTER;

  /* Test null reference error for save(), update() & delete(). */
  private static final Computer NULL_COMPUTER_REFERENCE = null;

  /**
   * Test the following errors for save() & udpate methods.
   * 1. Error happens when name is null or empty.
   * 2. Error happens when discontinued date is earlier than the introduced date.
   * 3. Error happens when the compnay id does not exist in the database.
   */
  private static final Computer COMPUTER_WITHOUT_NAME;
  private static final Computer COMPUTER_INVALID_DATE;
  private static final Computer COMPUTER_INVALID_COMPANY;

  /* Test invalid computer id error for update() & delete() methods. */
  private static final Computer COMPUTER_INVALID_ID;

  /* Valid computer object to save. */
  private static final long VALID_COMPUTER_TO_SAVE_ID;
  private static final Computer VALID_COMPUTER_TO_SAVE;
  /* Valid computer to update. */
  private static final long VALID_COMPUTER_TO_UPDATE_ID;
  private static final Computer VALID_COMPUTER_TO_UPDATE;
  /* Valid computer to delete. */
  private static final long VALID_COMPUTER_TO_DELETE_ID;
  private static final Computer VALID_COMPUTER_TO_DELETE;

  private static final Object[][] TEST_DATA;

  private static Computer parseComputer(Object[] a) {
    Computer c = new Computer();

    if (a[0] != null) {
      c.setId((long) a[0]);
    }
    if (a[1] != null) {
      c.setName((String) a[1]);
    }
    c.setIntroduced(Optional.ofNullable((LocalDate) a[2]));
    c.setDiscontinued(Optional.ofNullable((LocalDate) a[3]));
    c.setCompany(Optional.ofNullable((Company) a[4]));

    return c;
  }

  static {

    /**
     * Order:
     * id, name, introduced date, discontinued date, company.
     */
    TEST_DATA = new Object[][] {
        {
            17L,
            "Apple III Plus",
            LocalDate.of(1983, 12, 1),
            LocalDate.of(1984, 4, 1),
            new Company(1, "Apple Inc.")
        }, // index 0 -> Valid computer
        {
            null, null, null, null, null
        }, // index 1 -> Computer without name.
        {
            null,
            "Macbook Pro mid-2015",
            LocalDate.of(2015, 6, 1),
            LocalDate.of(2013, 6, 1),
            null
        }, // index 2 -> Computer with invalid dates.
        {
            null,
            "Macbook Pro mid-2015",
            LocalDate.of(2013, 6, 1),
            LocalDate.of(2015, 6, 1),
            new Company(99L, "Apple Inc.")
        }, // index 3 -> Computer with invalid compnay.
        {
            -1L,
            "Macbook Pro mid-2015",
            LocalDate.of(2013, 6, 1),
            LocalDate.of(2015, 6, 1),
            new Company(1L, "Apple Inc.")
        }, // index 4 -> Computer for which the id doesn't exist in the database.
        {
            21L,
            "Macbook Pro mid-2015",
            LocalDate.of(2013, 6, 1),
            LocalDate.of(2015, 6, 1),
            new Company(1L, "Apple Inc.")
        }, // index 5 -> valid computer to save()
        {
            3L,
            "Macbook Pro mid-2015",
            LocalDate.of(2013, 6, 1),
            LocalDate.of(2015, 6, 1),
            new Company(1L, "Apple Inc.")
        }, // index 6 -> valid computer to update()
        {
            3L, null, null, null, null
        } // index 7 -> valid computer to delete()

    };

    /* Constants to test the get() & getAll() methods. */
    VALID_COMPUTER_ID = (long) TEST_DATA[0][0];
    INVALID_COMPUTER_ID = -1L;
    VALID_COMPUTER = parseComputer(TEST_DATA[0]);

    /* Constants to test the save() & update() methods. */
    COMPUTER_WITHOUT_NAME = parseComputer(TEST_DATA[1]);
    COMPUTER_INVALID_DATE = parseComputer(TEST_DATA[2]);
    COMPUTER_INVALID_COMPANY = parseComputer(TEST_DATA[3]);

    /* Constants to test the update() & delete() methods. */
    COMPUTER_INVALID_ID = parseComputer(TEST_DATA[4]);

    /* Valid computer object to save. */
    VALID_COMPUTER_TO_SAVE_ID = (long) TEST_DATA[5][0];
    VALID_COMPUTER_TO_SAVE = parseComputer(TEST_DATA[5]);
    /* Valid computer to update. */
    VALID_COMPUTER_TO_UPDATE_ID = (long) TEST_DATA[6][0];
    VALID_COMPUTER_TO_UPDATE = parseComputer(TEST_DATA[6]);
    /* Valid computer to delete. */
    VALID_COMPUTER_TO_DELETE_ID = (long) TEST_DATA[7][0];
    VALID_COMPUTER_TO_DELETE = parseComputer(TEST_DATA[7]);
  }

  @BeforeAll
  public static void beforeAll() throws SQLException, IOException, ClassNotFoundException {
    logger = (Logger) LoggerFactory.getLogger("com.excilys.cdb.dao.ComputerDao");
    listAppender = new ListAppender<>();
    listAppender.start();
    logger.addAppender(listAppender);
    logsList = listAppender.list;

    TestUtils.setupDatabase();
  }

  @AfterAll
  public static void afterAll() throws SQLException {
    TestUtils.clear();
  }

  private static void assertErrorLogEvent(ComputerDaoErrors error) {
    ILoggingEvent logEvent = logsList.get(logsList.size() - 1);
    assertEquals(Level.ERROR, logEvent.getLevel());
    assertEquals(error.getMessage(), logEvent.getMessage());
  }

  @Test
  @Order(1)
  public void testGetSuccess() {
    /* Get a compmuter that exists in the database. */
    // Given
    // When
    Optional<Computer> computerOpt = COMPUTER_DAO.get(VALID_COMPUTER_ID);
    assertTrue(computerOpt.isPresent());
    Computer computer = computerOpt.get();
    // Then
    assertTrue(VALID_COMPUTER.StrictlyEqauls(computer));
  }

  @Test
  @Order(2)
  public void testGetFailure() {
    /* Get a compmuter that doesn't not exist in the database. */
    // Given
    // When
    Optional<Computer> computer = COMPUTER_DAO.get(INVALID_COMPUTER_ID);
    // Then
    assertFalse(computer.isPresent());
  }

  @Test
  @Order(3)
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
    assertTrue(VALID_COMPUTER.StrictlyEqauls(computer));
  }

  @Test
  @Order(4)
  public void testSaveNullReference() {
    /**
     * Test when a null reference is passed to the save() method.
     * Expect the affected rows in the database to be 0.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.save(NULL_COMPUTER_REFERENCE);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.INVALID_COMPUTER_ARGUEMNT_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(5)
  public void testSaveEmptyName() {
    /**
     * Test when no name is provided when we want to save a computer.
     * Expect the affected rows in the database to be 0.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.save(COMPUTER_WITHOUT_NAME);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.LACK_NAME_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(6)
  public void testSaveInvalidDates() {
    /**
     * Test when 'date discontinued' is not later than 'date introduced'.
     * Expect the affected rows in the database to be 0.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.save(COMPUTER_INVALID_DATE);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.INVALID_DATE_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(7)
  public void testSaveInvalidCompany() {
    /**
     * Test when the company_id of the computer to save is not valid.
     * Expect the affected rows in the database to be 0.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.save(COMPUTER_INVALID_COMPANY);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.INVALID_COMPANY_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(8)
  public void testSavaSuccess() {
    /**
     * Test saving with a valid company.
     * Expect the affected rows in the database to be 1.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.save(VALID_COMPUTER_TO_SAVE);
    Optional<Computer> computerOpt = COMPUTER_DAO.get(VALID_COMPUTER_TO_SAVE_ID);
    // Then
    assertEquals(1, rowsAffected);
    assertTrue(computerOpt.isPresent());
    assertTrue(VALID_COMPUTER_TO_SAVE.StrictlyEqauls(computerOpt.get()));
  }

  @Test
  @Order(9)
  public void testUpdateNullReference() {
    /**
     * Test when a null reference is passed to the update() method.
     * Expect the affected rows in the database to be 0.
     */
    // Given
    int rowsAffected = COMPUTER_DAO.save(NULL_COMPUTER_REFERENCE);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.INVALID_COMPUTER_ARGUEMNT_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(10)
  public void testUpdateEmptyName() {
    /**
     * Test when no name is provided when we want to update a computer.
     * Expect the affected rows in the database to be 0.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.update(COMPUTER_WITHOUT_NAME);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.LACK_NAME_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(11)
  public void testUpdateInvalidDates() {
    /**
     * Test when 'date discontinued' is not later than 'date introduced'.
     * Expect the affected rows in the database to be 0.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.update(COMPUTER_INVALID_DATE);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.INVALID_DATE_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(12)
  public void testUpdateInvalidCompany() {
    /**
     * Test when the company_id of the computer to update is not valid.
     * Expect the affected rows in the database to be 0.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.update(COMPUTER_INVALID_COMPANY);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.INVALID_COMPANY_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(13)
  public void testUpdateInvalidId() {
    /**
     * Test when the id of the computer to update in the database is invalid.
     * Expect the affected rows in the database to be 0.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.update(COMPUTER_INVALID_ID);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.INVALID_ID_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(14)
  public void testUpdateSuccess() {
    /**
     * Test update a valid computer in the database.
     * Expect the affected rows in the database to be 1.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.update(VALID_COMPUTER_TO_UPDATE);
    Optional<Computer> computerOpt = COMPUTER_DAO.get(VALID_COMPUTER_TO_UPDATE_ID);
    // Then
    assertEquals(1, rowsAffected);
    assertTrue(computerOpt.isPresent());
    assertTrue(VALID_COMPUTER_TO_UPDATE.StrictlyEqauls(computerOpt.get()));
  }

  @Test
  @Order(15)
  public void testDeleteNullReferenec() {
    /**
     * Test when a null reference is passed to the delete() method.
     * Expect the affected rows in the database to be 0.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.delete(NULL_COMPUTER_REFERENCE);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.INVALID_COMPUTER_ARGUEMNT_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(16)
  public void testDeleteInvalidId() {
    /**
     * Test when the id of the computer to delete in the database is invalid.
     * Expect the affected rows in the database to be 0.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.delete(COMPUTER_INVALID_ID);
    // Then
    assertErrorLogEvent(ComputerDaoErrors.INVALID_ID_ERROR);
    assertEquals(0, rowsAffected);
  }

  @Test
  @Order(17)
  public void testDeleteSuccess() {
    /**
     * Test when deleting a valid record of computer in the database.
     * Expect the affected rows in the database to be 1.
     */
    // Given

    // When
    int rowsAffected = COMPUTER_DAO.delete(VALID_COMPUTER_TO_DELETE);
    Optional<Computer> computerOpt = COMPUTER_DAO.get(VALID_COMPUTER_TO_DELETE_ID);
    // Then
    assertEquals(1, rowsAffected);
    assertFalse(computerOpt.isPresent());
  }
}
