package com.excilys.cdb.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

public class TestUtils {
  private static final String INIT_FILE_PATH =
      "/home/excilys/Bureau/tuto/cdb/computer-database/src/test/resources/init.sql";
  private static final String INSERT_FILE_PAHT =
      "/home/excilys/Bureau/tuto/cdb/computer-database/src/test/resources/insert.sql";

  private static final String TEST_PROP_NAME = "test";
  private static final String TEST_PROP_VALUE = "true";

  public static void setupDatabase() throws SQLException, FileNotFoundException, IOException {
    System.setProperty(TEST_PROP_NAME, TEST_PROP_VALUE);
    try (Connection connection = ConnectionUtils.getConnection();
        Reader initFileReader = new BufferedReader(new FileReader(INIT_FILE_PATH));
        Reader insertFileReader = new BufferedReader(new FileReader(INSERT_FILE_PAHT))) {
      ScriptRunner sr = new ScriptRunner(connection);
      sr.runScript(initFileReader);
      sr.runScript(insertFileReader);
    }
  }

  // public static void dropDatabase() throws SQLException {
  // try (Connection connection = ConnectionUtils.getConnection();) {
  // Statement statement = connection.createStatement();
  // statement.execute("DROP ALL OBJECTS [DELETE FILES]");
  // }
  // }

  public static void clear() {
    System.clearProperty(TEST_PROP_NAME);
  }
}
