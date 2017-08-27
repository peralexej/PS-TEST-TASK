package com.peterservice.conf;

import java.io.IOException;
import java.util.Properties;

/**
 * Класс для конфигов
 */
public class ConfigProperties {
  private static final String DEFAULT_ENV_ADDRESS = "default_env_address";
  private static final String LOG_DIR = "logs.dir";

  private static final String CONFIG_FILE_NAME = "config.properties";

  private static ConfigProperties instance = null;

  private Properties props;

  private ConfigProperties() {
    try {
      props = new Properties();
      props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
    } catch (IOException e) {
      throw new RuntimeException("Unable to load configuration file", e);
    }
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static ConfigProperties getInstance() {
    if (instance == null) {
      instance = new ConfigProperties();
    }
    return instance;
  }

  private String getProperty(String key) {
    return props.getProperty(key);
  }

  /**
   * Gets default env address.
   *
   * @return the default env address
   */
  public String getDefaultEnvAddress() {
    return getProperty(DEFAULT_ENV_ADDRESS);
  }
}
