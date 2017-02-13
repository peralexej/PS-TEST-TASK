package com.peterservice.test.functional.steps;

import com.peterservice.conf.ConfigProperties;
import com.peterservice.context.TestContext;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;
import java.util.logging.Logger;

import static com.peterservice.view.ApiTestView.*;
import static junit.framework.TestCase.assertTrue;


public class ApiTestSteps {
    private static final ConfigProperties config = ConfigProperties.getInstance();
    private static Logger logger = TestContext.getLogger();

    @When("^Send get request to url \"([^\"]*)\"$")
    public void sendGetRequestToUrl(String url) throws Throwable {
        sendGetRequest(url);
    }

    @When("^Send get request to url \"([^\"]*)\" limit = (\\d+) offset = (\\d+)$")
    public void sendGetRequestToUrlLimitOffset(String url, int limit, int offset) throws Throwable {
        sendGetRequest(url, limit, offset);
    }


    @When("^Send post request to \"([^\"]*)\" with parameters$")
    public void sendPostRequestToWithParameters(String url, List<String> values) throws Throwable {
        sendPostRequest(url, createJsonMapForRequest(values));
    }

    @Then("^Response contains$")
    public void responseContains(List<String> values) throws Throwable {
        logger.info("size - " + values.size() + "");
        for (String pair : values) {
            String key = pair.substring(0, pair.indexOf(":"));
            String value = pair.substring(pair.indexOf(":") + 1);
            assertTrue(key + " - not contains in response", getResponseAsString().contains(key));
            assertTrue(value + " - not contains in response", getResponseAsString().contains(value));
        }
    }

    @Then("^Response id > (\\d+)$")
    public void responseId(int value) throws Throwable {
        String response = getResponseAsString();
        int id = Integer.parseInt(response.substring(response.indexOf(":") + 1).replaceAll("}", ""));
        assertTrue(value < id);
    }
}
