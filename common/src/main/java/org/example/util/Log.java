package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private Log() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Log.class);

    public static void debug(String msg, Object... args) {
        LOGGER.debug(msg, args);
    }

    public static void info(String msg, Object... args) {
        LOGGER.info(msg, args);
    }

    public static void warn(String msg, Object... args) {
        LOGGER.warn(msg, args);
    }

    public static void error(String msg, Object... args) {
        LOGGER.error(msg, args);
    }
}