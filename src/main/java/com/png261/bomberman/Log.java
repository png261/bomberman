package com.png261.bomberman;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Log
{
    private static final Logger logger = Logger.getLogger(Log.class.getName());

    public static void info(String message) { logger.log(Level.INFO, message); }

    public static void warning(String message) { logger.log(Level.WARNING, message); }

    public static void error(String message) { logger.log(Level.SEVERE, message); }
}
