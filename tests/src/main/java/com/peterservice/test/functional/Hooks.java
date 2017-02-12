package com.peterservice.test.functional;

import com.google.common.base.Throwables;
import com.peterservice.logging.LoggingUtils;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.ScenarioImpl;
import gherkin.formatter.model.Result;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Запись данных из кукумбера в логгер
 *
 */

public final class Hooks {
    private static final String STEP_RESULTS_FIELD_NAME = "stepResults";
    private static final String TAG_ONE_TIME_INITIALISABLE = "@OneTimeInitialisable";
    private static ThreadLocal<List<Scenario>> failedScenarios;
    private static Logger logger;

    static {
        LoggingUtils.cleanUpLogsDir();
    }

    private String logDirPath;
    private String currentFeature;
    private boolean setupMade = false;

    private static void addFailedScenario(Scenario scenario) {
        failedScenarios.get().add(scenario);
    }

    /**
     * Try to resolve scenario type by scenario name.
     *
     * @param scenario
     * @return
     */

    @Before
    public void setUp(Scenario scenario) throws Throwable {
        if (!setupMade) {
            setup(scenario);
            setupMade = true;
        }
        logScenarioStart(scenario);
        if (!isTagPresent(scenario, TAG_ONE_TIME_INITIALISABLE)) {
            return;
        }
        String feature = getFeatureNameForScenario(scenario);
        if (currentFeature == null || !currentFeature.equals(feature)) {
            currentFeature = feature;
        }
    }

    @After
    public void tearDown(Scenario scenario) throws Exception {
        logScenarioEnd(scenario);
    }

    private void logScenarioStart(Scenario scenario) {
        logger.info(":::::::::::::::::::::::::::::::::::::");
        logger.info("Running scenario '" + scenario.getId() + "'");
    }

    private void logScenarioEnd(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            addFailedScenario(scenario);
            logScenarioFailure(scenario);
            logger.warning("Scenario '" + scenario.getId() + "' failed");
        } else {
            logger.info("Scenario '" + scenario.getId() + "' finished");
        }
        logger.info(":::::::::::::::::::::::::::::::::::::");
    }

    private void setup(Scenario scenario) throws Throwable {
        failedScenarios = new ThreadLocal<List<Scenario>>() {
            @Override
            protected List<Scenario> initialValue() {
                return new ArrayList<Scenario>();
            }
        };
        logDirPath = LoggingUtils.getLogsDirPath();
        logger = LoggingUtils.createLogger();
    }

    private boolean isTagPresent(Scenario scenario, String tag) {
        List<String> tags = (List<String>) scenario.getSourceTagNames();
        for (String t : tags) {
            if (t.equals(tag)) {
                return true;
            }
        }
        return false;
    }

    private String getFeatureNameForScenario(Scenario scenario) {
        String scenarioId = scenario.getId();
        return scenarioId.substring(0, scenarioId.indexOf(";"));
    }

    private void logScenarioFailure(Scenario scenario) throws NoSuchFieldException, IllegalAccessException, FileNotFoundException {
        Field stepResultsField = ScenarioImpl.class.getDeclaredField(STEP_RESULTS_FIELD_NAME);
        stepResultsField.setAccessible(true);
        Throwable error = null;
        List<Result> stepResults = (List<Result>) stepResultsField.get(scenario);
        for (Result stepResult : stepResults) {
            if (stepResult.getError() != null) {
                error = stepResult.getError();
                break;
            }
        }
        logger.severe(Throwables.getStackTraceAsString(error));
    }
}
