package com.peterservice.utils;

import static com.peterservice.context.ContextKey.STATUS_CODE;
import com.peterservice.context.TestContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * <p>
 * Класс содержит методы, реализующие работу request/response с помощью Rest Template, ловит
 * необходимые данные и добавляет их в ResultsContainer
 */
public class RestTemplateView {
  private static final Logger LOGGER = Logger.getAnonymousLogger();

  /**
   * Gets rest template.
   *
   * @param url the url
   * @return the rest template
   */
  public static RestTemplate getRestTemplate(final String url) {
    LOGGER.info("URL - " + url);
    HttpComponentsClientHttpRequestFactory requestFactory = getHttpRequest();
    final RestTemplate template = new RestTemplate(requestFactory);
    template.getInterceptors().add(
            new ClientHttpRequestInterceptor() {
              @Override
              public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
                ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
                LOGGER.info("STATUS CODE: " + response.getStatusCode().toString());
                LOGGER.info("STATUS TEXT: " + response.getStatusText());
                TestContext.put(STATUS_CODE, response.getStatusCode().toString());
                return response;
              }
            });

    return template;
  }

  private static HttpComponentsClientHttpRequestFactory getHttpRequest() {
    try {
      CloseableHttpClient httpclient = HttpClients.createDefault();
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
      requestFactory.setHttpClient(httpclient);
      return requestFactory;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
