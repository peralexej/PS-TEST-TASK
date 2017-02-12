package com.peterservice.view;

import com.peterservice.conf.ConfigProperties;
import com.peterservice.context.TestContext;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import static com.peterservice.context.ContextKey.*;
import static com.peterservice.utils.RestTemplateView.getRestTemplate;

/**
 *
 * <p>
 * Класс содержит методы, используемые в тестовых шагах, для
 * отправки REST запросов и обработки ответов
 */
public class ApiTestView {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private static final ConfigProperties config = ConfigProperties.getInstance();

    public static String getResponseAsString() {
        return (String) TestContext.getContextVariable(RESPONSE_MESSAGE);
    }

    public static String getStatusCodeAsString() {
        return (String) TestContext.getContextVariable(STATUS_CODE);
    }

    public static String getCurrentId() {
        return (String) TestContext.getContextVariable(CURRENT_ID);
    }

    public static void sendPostRequest(String value, HashMap map) {
        String url = config.getDefaultEnvAddress() + "/" + value;
        RestTemplate template = getRestTemplate(url);
        String result = template.postForObject(url, map, String.class);
        putResponseToContainer(result);
    }

    public static void sendGetRequest(String value) {
        RestTemplate template = getRestTemplate(config.getDefaultEnvAddress());
        String url = config.getDefaultEnvAddress() + "/" + value;
        LOGGER.info("URL  - " + url);
        String result = template.getForObject(url, String.class);
        putResponseToContainer(result);
    }
    public static void sendGetRequest(String value, int limit, int offset) {
        RestTemplate template = getRestTemplate(config.getDefaultEnvAddress());
        Properties props = new Properties();
        props.put("limit" , limit);
        props.put("offset" , offset);
        String url = config.getDefaultEnvAddress() + "/" + value;
        LOGGER.info("URL  - " + url);
        String result = template.getForObject(url, String.class, props);
        putResponseToContainer(result);
    }

    public static void sendGetRequestForAllUsers(String url) {
        RestTemplate template = getRestTemplate(url);
        String result = template.getForObject(url, String.class);
        putResponseToContainer(result);
    }


    public static HashMap<String, String> createJsonMapForRequest(List<String> values) {
        HashMap<String, String> map = new HashMap<>();
        for (String pair : values) {
            String key = pair.substring(0, pair.indexOf(":"));
            String value = pair.substring(pair.indexOf(":") + 1);
            map.put(key, value);
        }
        return map;
    }

    private static void putResponseToContainer(String result) {
        TestContext.put(RESPONSE_MESSAGE, result);
        LOGGER.info("RESPONSE - " + result);
    }
}
