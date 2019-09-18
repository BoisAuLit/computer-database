package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.utils.ConfigurationUtils;

public class ConnectionManager {

  static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.ConnectionManager");

  /**
   * Property keys in the properties file
   */
  private static final String USERNAME_KEY = "username";
  private static final String PASSWORD_KEY = "password";
  private static final String DATABASE_TYPE_KEY = "dbType";
  private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

  // @formatter:off
  private static final String CONNECTION_QUERY_TEMPLATE = String.format("jdbc:${%s}://localhost:3306/"
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
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      logger.error("Error loading JDBC driver!", e);
      System.exit(1);
    }
  }

  static Connection getConnection() throws SQLException {

    Configuration configuration = ConfigurationUtils.getConfiguration();

    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put(DATABASE_TYPE_KEY, configuration.getString(DATABASE_TYPE_KEY));

    StringSubstitutor ss = new StringSubstitutor(valuesMap);
    String connectionQuery = ss.replace(CONNECTION_QUERY_TEMPLATE);

    String username = configuration.getString(USERNAME_KEY);
    String password = configuration.getString(PASSWORD_KEY);
    connection = DriverManager.getConnection(connectionQuery, username, password);
    return connection;
  }
}
