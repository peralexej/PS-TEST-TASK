package com.peterservice.logging;

import com.google.common.base.Throwables;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

/**
 * Класс реализующий работу логгера
 */

final class LoggerFactory {

    private static final String SLASH = File.separator;

    static Logger create() {
        Logger logger = Logger.getAnonymousLogger();
        logger.setUseParentHandlers(false);
        Formatter formatter = new RecordFormatter();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler);
        String logsRootDirPath = LoggingUtils.getLogsDirPath();
        FileHandler fileHandler;
        String name = "logs";
        try {
            File logsRootDir = new File(logsRootDirPath);
            if (!logsRootDir.exists() && !logsRootDir.mkdirs()) {
                throw new IOException("Cannot create logs dir");
            }
            File threadDir = new File(logsRootDirPath + SLASH + name);
            if (!threadDir.exists() && !threadDir.mkdir()) {
                throw new IOException("Cannot create logs dir for thread");
            }
            fileHandler = new FileHandler(logsRootDirPath + SLASH + name + SLASH + name + ".log");
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            logger.severe(Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e);
        }
        logger.addHandler(fileHandler);
        return logger;
    }
}
