package com.excilys.cdb.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.excilys.cdb.domain.Company;

class CompanyDaoTest {

  private static final String H2_DRIVER_NAME = "org.h2.Driver";
  private static final String H2_CONNECTION_QUERY = "jdbc:h2:~/test";
  private static final CompanyDao COOMPANY_DAO = CompanyDao.getInstance();

  // todo, to be removed
  private static final String INIT_FILE_PATH =
      "/home/excilys/Bureau/tuto/cdb/computer-database/src/test/resources/init.sql";
  private static final String INSERT_FILE_PAHT =
      "/home/excilys/Bureau/tuto/cdb/computer-database/src/test/resources/insert.sql";

  @BeforeAll
  public static void BeforeAll() throws ClassNotFoundException {
    Class.forName(H2_DRIVER_NAME);
  }

  // insert the database
  @BeforeEach
  public void beforeEach() throws SQLException, FileNotFoundException {
    try (Connection connection = DriverManager.getConnection(H2_CONNECTION_QUERY)) {
      ScriptRunner sr = new ScriptRunner(connection);
      Reader initFileReader = new BufferedReader(new FileReader(INIT_FILE_PATH));
      Reader insertFileReader = new BufferedReader(new FileReader(INSERT_FILE_PAHT));
      sr.runScript(initFileReader);
      sr.runScript(insertFileReader);
    }
  }

  // drop the database
  @AfterEach
  public void afterEach() {

  }

  @Test
  public void testGetSuccess() throws SQLException, ClassNotFoundException {

    try (Connection connection = DriverManager.getConnection(H2_CONNECTION_QUERY)) {
      Optional<Company> company = COOMPANY_DAO.get(1);
      System.out.println(company.get());
    }
  }

}
