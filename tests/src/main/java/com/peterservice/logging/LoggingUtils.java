package com.peterservice.logging;

import com.peterservice.conf.ConfigProperties;
import com.peterservice.context.ContextKey;
import com.peterservice.context.TestContext;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Методы для работы с логгером
 *
 */

public final class LoggingUtils {

    private static final String SLASH = File.separator;
    private static final ConfigProperties config = ConfigProperties.getInstance();

    private static String logsDirPath;

    public static Logger createLogger() {
        Logger logger = TestContext.getLogger();
        if (logger == null) {
            logger = LoggerFactory.create();
            TestContext.put(ContextKey.LOGGER, logger);
        }
        return logger;
    }

    public static String getLogsDirPath() {
        if (logsDirPath == null) {
            logsDirPath = config.getLogsDirPath().replaceAll("//", SLASH);
        }
        return logsDirPath;
    }

    public static void closeLoggerHandlers(Logger logger) {
        if (logger == null) {
            return;
        }
        for (Handler handler : logger.getHandlers()) {
            if (handler instanceof StreamHandler) {
                handler.close();
            }
        }
    }

    public static void cleanUpLogsDir() {
        try {
            String logsRootDirPath = getLogsDirPath();
            File logsRootDir = new File(logsRootDirPath);
            String selenideLogFilesPath = "build" + SLASH + "reports" + SLASH + "tests";
            File selenideLogFiles = new File(selenideLogFilesPath);
            if (logsRootDir.exists()) {
                FileUtils.cleanDirectory(logsRootDir);
                FileUtils.cleanDirectory(selenideLogFiles);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to clean root logs dir");
            System.out.println("Cause - " + e.getMessage());
        }
    }
}
