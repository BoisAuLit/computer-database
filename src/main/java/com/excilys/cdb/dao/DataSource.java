package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.utils.ConfigurationUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {

  static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.DataSource");

  private static HikariConfig configMysql = new HikariConfig();
  private static HikariDataSource dsMysql;

  private static HikariConfig configH2 = new HikariConfig();
  private static HikariDataSource dsH2;

  private static Configuration configuration;

  private static final String USERNAME_KEY = "username";
  private static final String PASSWORD_KEY = "password";
  private static final String DATABASE_TYPE_KEY = "dbType";
  private static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

  private static String connectionQuery;


  private static final String H2_DRIVER_NAME = "org.h2.Driver";
  private static final String H2_CONNECTION_QUERY = "jdbc:h2:~/test";

  private static final String CONNECTION_QUERY_TEMPLATE =
      String.format("jdbc:${%s}://localhost:3306/"
          + "computer-database-db?"
          + "useUnicode=true"
          + "&useJDBCCompliantTimezoneShift=true"
          + "&useLegacyDatetimeCode=false"
          + "&serverTimezone=UTC", DATABASE_TYPE_KEY);


  static {
    configuration = ConfigurationUtils.getConfiguration();

    if (Objects.isNull(System.getProperty("test"))) {
      Map<String, String> valuesMap = new HashMap<>();
      valuesMap.put(DATABASE_TYPE_KEY, configuration.getString(DATABASE_TYPE_KEY));

      StringSubstitutor ss = new StringSubstitutor(valuesMap);
      connectionQuery = ss.replace(CONNECTION_QUERY_TEMPLATE);
    }

    try {
      Class.forName(MYSQL_DRIVER_NAME).newInstance();
      Class.forName(H2_DRIVER_NAME).newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      logger.error("Error loading database driver!", e);
      System.exit(1);
    }

    // Configure mysql connection
    configMysql.setJdbcUrl("jdbc:mysql://localhost:3306/"
        + "computer-database-db?"
        + "useUnicode=true"
        + "&useJDBCCompliantTimezoneShift=true"
        + "&useLegacyDatetimeCode=false"
        + "&serverTimezone=UTC");
    configMysql.setUsername(configuration.getString(USERNAME_KEY));
    configMysql.setPassword(configuration.getString(PASSWORD_KEY));
    configMysql.addDataSourceProperty("cachePrepStmts", "true");
    configMysql.addDataSourceProperty("prepStmtCacheSize", "250");
    configMysql.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    dsMysql = new HikariDataSource(configMysql);

    // Configure H2 connection
    configH2.setJdbcUrl(H2_CONNECTION_QUERY);
    configH2.addDataSourceProperty("cachePrepStmts", "true");
    configH2.addDataSourceProperty("prepStmtCacheSize", "250");
    configH2.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    dsH2 = new HikariDataSource(configH2);
  }

  private DataSource() {

  }


  public static Connection getConnection() throws SQLException {
    if (Objects.nonNull(System.getProperty("test"))) {
      return dsH2.getConnection();
    } else {
      return dsMysql.getConnection();
    }
  }
}
