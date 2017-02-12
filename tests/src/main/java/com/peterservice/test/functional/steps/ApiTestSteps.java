package com.peterservice.test.functional.steps;

import com.peterservice.conf.ConfigProperties;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.List;

import static com.peterservice.view.ApiTestView.*;
import static junit.framework.TestCase.assertTrue;


public class ApiTestSteps {
    private static final ConfigProperties config = ConfigProperties.getInstance();

    @When("^Send get request to url \"([^\"]*)\"$")
    public void sendGetRequestToUrl(String url) throws Throwable {
        sendGetRequest(url);
    }

    @When("^Send get request to url \"([^\"]*)\" limit = (\\d+) offset = (\\d+)$")
    public void sendGetRequestToUrlLimitOffset(String url, int limit, int offset) throws Throwable {
        sendGetRequest(url, limit, offset);
    }

    @Then("^Response contains$")
    public void responseContains(List<String> values) throws Throwable {
        for (String pair : values) {
            String key = pair.substring(0, pair.indexOf(":"));
            String value = pair.substring(pair.indexOf(":") + 1);
            assertTrue(key.toLowerCase().contains(getResponseAsString().toLowerCase()));
            assertTrue(value.toLowerCase().contains(getResponseAsString().toLowerCase()));
        }
    }

    @When("^Send post request to \"([^\"]*)\" with parameters$")
    public void sendPostRequestToWithParameters(String url, List<String> values) throws Throwable {
        sendPostRequest(url, createJsonMapForRequest(values));
    }
}
