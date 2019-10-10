package com.excilys.cdb.utils;

import java.io.File;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ConfigurationUtils {

  static Logger logger = LoggerFactory.getLogger("com.excilys.cdb.dao.ConfigurationUtils");
  // private static final String CONFIG_FILE_SYS_PROP_KEY = "config";

  private static class LazyHolder {
    static Configuration configuration = null;

    static {
      try {
        configuration = readConfigFiles();
      } catch (ConfigurationException e) {
        logger.error("Failed reading configuration file", e);
        System.exit(1);
      }
    }
  }

  public static Configuration getConfiguration() {
    return LazyHolder.configuration;
  }

  /**
   * @param configFileSysProp the system property for which the value is the absolute path to the
   *        configuration file
   */
  private static Configuration readConfigFiles() throws ConfigurationException {

    // SystemConfiguration sc = new SystemConfiguration();
    // String configFilePath = sc.getString(CONFIG_FILE_SYS_PROP_KEY);
    //
    //
    //
    // if (configFilePath == null) {
    // throw new ConfigurationException(
    // "Please specify the system property for " + "the configuration file!");
    // }

    // File configFile = new File(configFilePath);

    File configFile = new File(
        ConfigurationUtils.class
            .getClassLoader().getResource("application.properties").getFile());

    if (!configFile.exists()) {
      throw new ConfigurationException(
          "Please make sure that the absolute path to the configuration file is correct!");
    }

    Parameters params = new Parameters();

    FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
        new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
            .configure(params.properties().setFile(configFile));
    Configuration config = builder.getConfiguration();

    return config;
  }
}
