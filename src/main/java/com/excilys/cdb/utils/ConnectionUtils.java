package com.excilys.cdb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionUtils {

  static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.ConnectionManager");

  /**
   * Property keys in the properties file
   */
  private static final String USERNAME_KEY = "username";
  private static final String PASSWORD_KEY = "password";
  private static final String DATABASE_TYPE_KEY = "dbType";
  private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

  private static Configuration CONFIGURATION;
  private static String CONNECTION_QUERY;

  // @formatter:off
  private static final String CONNECTION_QUERY_TEMPLATE =
      String.format("jdbc:${%s}://localhost:3306/"
      + "computer-database-db?"
      + "useUnicode=true"
      + "&useJDBCCompliantTimezoneShift=true"
      + "&useLegacyDatetimeCode=false"
      + "&serverTimezone=UTC", DATABASE_TYPE_KEY);
  // @formatter:on

  private static Connection connection;
  static {
    try {
      Class.forName(DRIVER_NAME).newInstance();

      CONFIGURATION = ConfigurationUtils.getConfiguration();

      Map<String, String> valuesMap = new HashMap<>();
      valuesMap.put(DATABASE_TYPE_KEY, CONFIGURATION.getString(DATABASE_TYPE_KEY));

      StringSubstitutor ss = new StringSubstitutor(valuesMap);
      CONNECTION_QUERY = ss.replace(CONNECTION_QUERY_TEMPLATE);
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      logger.error("Error loading JDBC driver!", e);
      System.exit(1);
    }
  }

  public static Connection getConnection() throws SQLException {
    String username = CONFIGURATION.getString(USERNAME_KEY);
    String password = CONFIGURATION.getString(PASSWORD_KEY);
    connection = DriverManager.getConnection(CONNECTION_QUERY, username, password);
    return connection;
  }
}
