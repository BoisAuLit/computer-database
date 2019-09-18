package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CompanyDaoTest {

  private static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.CompanyDaoTest");

  private static Connection connection;
  static {
    try {
      Class.forName("org.h2.Driver");
      connection = DriverManager.getConnection("jdbc:h2:~/test");
    } catch (ClassNotFoundException | SQLException e) {
      logger.error("Error happpend when configuring h2 in-memory datbase");
      e.printStackTrace();
    }

  }

  @BeforeAll
  void beforeAll() throws ClassNotFoundException, SQLException {

  }

  @BeforeEach
  void beforeEach() {

  }

  @AfterEach
  void afterEach() {

  }

  @AfterAll
  void afterAll() {

  }

  @Test
  void test() {

  }

}
