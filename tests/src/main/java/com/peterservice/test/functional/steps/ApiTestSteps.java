package com.peterservice.test.functional.steps;

import com.peterservice.conf.ConfigProperties;
import com.peterservice.context.TestContext;
import static com.peterservice.view.ApiTestView.createJsonMapForRequest;
import static com.peterservice.view.ApiTestView.getResponseAsString;
import static com.peterservice.view.ApiTestView.sendGetRequest;
import static com.peterservice.view.ApiTestView.sendPostRequest;
import static com.peterservice.view.ApiTestView.sendPutRequest;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static junit.framework.TestCase.assertTrue;

import java.util.List;
import java.util.logging.Logger;


/**
 * The type Api test steps.
 */
public class ApiTestSteps {
  private static final ConfigProperties config = ConfigProperties.getInstance();
  private static Logger logger = TestContext.getLogger();

  /**
   * Send get request to url.
   *
   * @param url the url
   * @throws Throwable the throwable
   */
  @When("^Send get request to url \"([^\"]*)\"$")
  public void sendGetRequestToUrl(String url) throws Throwable {
    sendGetRequest(url);
  }

  /**
   * Send get request to url limit offset.
   *
   * @param url    the url
   * @param limit  the limit
   * @param offset the offset
   * @throws Throwable the throwable
   */
  @When("^Send get request to url \"([^\"]*)\" limit = (\\d+) offset = (\\d+)$")
  public void sendGetRequestToUrlLimitOffset(String url, int limit, int offset) throws Throwable {
    sendGetRequest(url, limit, offset);
  }


  /**
   * Send post request to with parameters.
   *
   * @param url    the url
   * @param values the values
   * @throws Throwable the throwable
   */
  @When("^Send post request to \"([^\"]*)\" with parameters$")
  public void sendPostRequestToWithParameters(String url, List<String> values) throws Throwable {
    sendPostRequest(url, createJsonMapForRequest(values));
  }

  /**
   * Send put request to with parameters.
   *
   * @param url    the url
   * @param values the values
   * @throws Throwable the throwable
   */
  @When("^Send put request to \"([^\"]*)\" with parameters$")
  public void sendPutRequestToWithParameters(String url, List<String> values) throws Throwable {
    sendPutRequest(url, createJsonMapForRequest(values));
  }

  /**
   * Response contains.
   *
   * @param values the values
   * @throws Throwable the throwable
   */
  @Then("^Response contains$")
  public void responseContains(List<String> values) throws Throwable {
    for (String pair : values) {
      String key = pair.substring(0, pair.indexOf(":"));
      String value = pair.substring(pair.indexOf(":") + 1);
      assertTrue(key + " - not contains in response", getResponseAsString().contains(key));
      assertTrue(value + " - not contains in response", getResponseAsString().contains(value));
    }
  }

  /**
   * Response id.
   *
   * @param value the value
   * @throws Throwable the throwable
   */
  @Then("^Response id > (\\d+)$")
  public void responseId(int value) throws Throwable {
    String response = getResponseAsString();
    int id = Integer.parseInt(response.substring(response.indexOf(":") + 1).replaceAll("}", ""));
    assertTrue(value < id);
  }
}
