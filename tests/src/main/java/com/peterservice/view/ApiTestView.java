package com.peterservice.view;

import com.peterservice.conf.ConfigProperties;
import static com.peterservice.context.ContextKey.RESPONSE_MESSAGE;
import com.peterservice.context.TestContext;
import static com.peterservice.utils.RestTemplateView.getRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * <p>
 * Класс содержит методы, используемые в тестовых шагах, для
 * отправки REST запросов и обработки ответов
 */
public class ApiTestView {
  private static final Logger LOGGER = Logger.getAnonymousLogger();
  private static final ConfigProperties CONFIG_PROPERTIES = ConfigProperties.getInstance();
  private static final String mvnUrl = System.getProperty("env");


  /**
   * Gets current url.
   *
   * @return the current url
   */
  public static String getCurrentUrl() {
    if (mvnUrl != null) {
      return mvnUrl;
    }
    return CONFIG_PROPERTIES.getDefaultEnvAddress();
  }

  /**
   * Gets response as string.
   *
   * @return the response as string
   */
  public static String getResponseAsString() {
    return (String) TestContext.getContextVariable(RESPONSE_MESSAGE);
  }

  /**
   * Send post request.
   *
   * @param value the value
   * @param map   the map
   */
  public static void sendPostRequest(String value, HashMap map) {
    String url = getCurrentUrl() + "/" + value;
    RestTemplate template = getRestTemplate(url);
    String result = template.postForObject(url, map, String.class);
    putResponseToContainer(result);
  }


  /**
   * Send put request.
   *
   * @param value the value
   * @param map   the map
   */
  public static void sendPutRequest(String value, HashMap map) {
    String url = getCurrentUrl() + "/" + value;
    LOGGER.info("URL-" + url);
    RestTemplate template = getRestTemplate(url);
    HttpEntity<?> entity = new HttpEntity<Object>(map);
    ResponseEntity<String> response = template.exchange(url, HttpMethod.PUT, entity, String.class);
    String responseBody = response.getBody();
    LOGGER.info("RESPONSE_MSG -" + responseBody);
  }

  /**
   * Send get request.
   *
   * @param value the value
   */
  public static void sendGetRequest(String value) {
    RestTemplate template = getRestTemplate(getCurrentUrl());
    String url = getCurrentUrl() + "/" + value;
    LOGGER.info("URL  - " + url);
    String result = template.getForObject(url, String.class);
    putResponseToContainer(result);
  }

  /**
   * Send get request.
   *
   * @param value  the value
   * @param limit  the limit
   * @param offset the offset
   */
  public static void sendGetRequest(String value, int limit, int offset) {
    RestTemplate template = getRestTemplate(getCurrentUrl());
    Properties props = new Properties();
    props.put("limit", limit);
    props.put("offset", offset);
    String url = getCurrentUrl() + "/" + value;
    LOGGER.info("URL  - " + url);
    String result = template.getForObject(url, String.class, props);
    putResponseToContainer(result);
  }


  /**
   * Create json map for request hash map.
   *
   * @param values the values
   * @return the hash map
   */
  public static HashMap<String, Object> createJsonMapForRequest(List<String> values) {
    HashMap<String, Object> map = new HashMap<>();
    HashMap<String, Object> dateJson = new HashMap<>();
    for (String pair : values) {
      String key = pair.substring(0, pair.indexOf(":"));
      String value = pair.substring(pair.indexOf(":") + 1);
      if (key.equals("dateFrom") || key.equals("dateTo")) {
        dateJson.put(key, value);
      } else {
        map.put(key, value);
      }
    }
    map.put("validFor", dateJson);
    return map;
  }

  private static void putResponseToContainer(String result) {
    TestContext.put(RESPONSE_MESSAGE, result);
    LOGGER.info("RESPONSE - " + result);
  }
}
