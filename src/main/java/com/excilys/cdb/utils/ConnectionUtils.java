package com.excilys.cdb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
  private static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

  private static final String H2_DRIVER_NAME = "org.h2.Driver";
  private static final String H2_CONNECTION_QUERY = "jdbc:h2:~/test";

  private static Configuration configuration;
  private static String connectionQuery;

  private static final String CONNECTION_QUERY_TEMPLATE =
      String.format("jdbc:${%s}://localhost:3306/"
          + "computer-database-db?"
          + "useUnicode=true"
          + "&useJDBCCompliantTimezoneShift=true"
          + "&useLegacyDatetimeCode=false"
          + "&serverTimezone=UTC", DATABASE_TYPE_KEY);

  private static Connection connection;
  static {
    try {
      if (Objects.isNull(System.getProperty("test"))) {
        configuration = ConfigurationUtils.getConfiguration();

        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put(DATABASE_TYPE_KEY, configuration.getString(DATABASE_TYPE_KEY));

        StringSubstitutor ss = new StringSubstitutor(valuesMap);
        connectionQuery = ss.replace(CONNECTION_QUERY_TEMPLATE);
      }

      Class.forName(MYSQL_DRIVER_NAME).newInstance();
      Class.forName(H2_DRIVER_NAME).newInstance();

    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      logger.error("Error loading JDBC driver!", e);
      System.exit(1);
    }
  }

  public static Connection getMySQLConnection() throws SQLException {
    String username = configuration.getString(USERNAME_KEY);
    String password = configuration.getString(PASSWORD_KEY);
    connection = DriverManager.getConnection(connectionQuery, username, password);
    return connection;
  }

  public static Connection getH2Connection() throws SQLException {
    return DriverManager.getConnection(H2_CONNECTION_QUERY);
  }

  public static Connection getConnection() throws SQLException {
    if (Objects.nonNull(System.getProperty("test"))) {
      return getH2Connection();
    } else {
      return getMySQLConnection();
    }
  }
}
