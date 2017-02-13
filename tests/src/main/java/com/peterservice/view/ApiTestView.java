package com.peterservice.view;

import com.peterservice.context.TestContext;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import static com.peterservice.context.ContextKey.RESPONSE_MESSAGE;
import static com.peterservice.utils.RestTemplateView.getRestTemplate;

/**
 * <p>
 * Класс содержит методы, используемые в тестовых шагах, для
 * отправки REST запросов и обработки ответов
 */
public class ApiTestView {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private static final String generalUrl = System.getProperty("env") + "/test-rest/app";

    public static String getResponseAsString() {
        return (String) TestContext.getContextVariable(RESPONSE_MESSAGE);
    }

    public static void sendPostRequest(String value, HashMap map) {
        String url = generalUrl + "/" + value;
        RestTemplate template = getRestTemplate(url);
        String result = template.postForObject(url, map, String.class);
        putResponseToContainer(result);
    }

    public static void sendGetRequest(String value) {
        RestTemplate template = getRestTemplate(generalUrl);
        String url = generalUrl + "/" + value;
        LOGGER.info("URL  - " + url);
        String result = template.getForObject(url, String.class);
        putResponseToContainer(result);
    }

    public static void sendGetRequest(String value, int limit, int offset) {
        RestTemplate template = getRestTemplate(generalUrl);
        Properties props = new Properties();
        props.put("limit", limit);
        props.put("offset", offset);
        String url = generalUrl + "/" + value;
        LOGGER.info("URL  - " + url);
        String result = template.getForObject(url, String.class, props);
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
