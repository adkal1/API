package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {
    private static LoggerUtil instance = null;
    private final Logger logger;

    private LoggerUtil() {
        logger = Logger.getLogger(LoggerUtil.class.getName());
    }

    public static LoggerUtil getInstance() {
        if (instance == null) {
            instance = new LoggerUtil();
        }
        return instance;
    }

    public void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

    public void logDebug(String message) {
        logger.log(Level.FINE, message);
    }

    public void logWarning(String message) {
        logger.log(Level.WARNING, message);
    }

    public void logError(String message) {
        logger.log(Level.SEVERE, message);
    }
}
