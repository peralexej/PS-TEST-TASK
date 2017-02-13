package com.peterservice;

import com.peterservice.context.TestContext;
import com.peterservice.logging.LoggingUtils;
import com.peterservice.test.functional.TestFailedException;
import cucumber.api.junit.Cucumber;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс, который запускает кукумбер фичи и завершает их.
 */
public class AcceptanceTest {
    private final ZipParameters ZIP_PARAMETERS = new ZipParameters();
    private List<Failure> failures = new ArrayList<Failure>();

    {
        ZIP_PARAMETERS.setCompressionMethod(Zip4jConstants.COMP_STORE);
        ZIP_PARAMETERS.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
        ZIP_PARAMETERS.setIncludeRootFolder(false);
    }

    @Test
    public void test() throws Throwable {
        Cucumber cucumber = new Cucumber(CucumberRunner.class);
        cucumber.run(new CustomRunNotifier());
        if (!failures.isEmpty()) {
            throw new TestFailedException(failures);
        }
    }

    @After
    public void tearDown() throws Exception {
        LoggingUtils.closeLoggerHandlers(TestContext.getLogger());
    }


    private class CustomRunNotifier extends RunNotifier {
        @Override
        public void fireTestFailure(Failure failure) {
            super.fireTestFailure(failure);
            failures.add(failure);
        }
    }
}
