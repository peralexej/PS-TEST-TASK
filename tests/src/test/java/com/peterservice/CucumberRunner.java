package com.peterservice;

import cucumber.api.CucumberOptions;

/**
 * Класс раннер кукумбера
 */
@CucumberOptions(
        features = "src/test/resources/",
        format = {"pretty", "html:target/cucumber1", "json:target/cucumber1/cucumber_json_report.json", "junit:target/cucumber-junit1/cucumber.xml"},
        glue = {"com.peterservice.test.functional"}
//        , tags = {"@ping"}
)
public class CucumberRunner {
}
