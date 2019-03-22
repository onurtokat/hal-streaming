// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.minlog;

import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class Log
{
    public static final int LEVEL_NONE = 6;
    public static final int LEVEL_ERROR = 5;
    public static final int LEVEL_WARN = 4;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_TRACE = 1;
    private static int level;
    public static boolean ERROR;
    public static boolean WARN;
    public static boolean INFO;
    public static boolean DEBUG;
    public static boolean TRACE;
    private static Logger logger;
    
    static {
        Log.level = 3;
        Log.ERROR = (Log.level <= 5);
        Log.WARN = (Log.level <= 4);
        Log.INFO = (Log.level <= 3);
        Log.DEBUG = (Log.level <= 2);
        Log.TRACE = (Log.level <= 1);
        Log.logger = new Logger();
    }
    
    public static void set(final int level) {
        Log.level = level;
        Log.ERROR = (level <= 5);
        Log.WARN = (level <= 4);
        Log.INFO = (level <= 3);
        Log.DEBUG = (level <= 2);
        Log.TRACE = (level <= 1);
    }
    
    public static void NONE() {
        set(6);
    }
    
    public static void ERROR() {
        set(5);
    }
    
    public static void WARN() {
        set(4);
    }
    
    public static void INFO() {
        set(3);
    }
    
    public static void DEBUG() {
        set(2);
    }
    
    public static void TRACE() {
        set(1);
    }
    
    public static void setLogger(final Logger logger) {
        Log.logger = logger;
    }
    
    public static void error(final String message, final Throwable ex) {
        if (Log.ERROR) {
            Log.logger.log(5, null, message, ex);
        }
    }
    
    public static void error(final String category, final String message, final Throwable ex) {
        if (Log.ERROR) {
            Log.logger.log(5, category, message, ex);
        }
    }
    
    public static void error(final String message) {
        if (Log.ERROR) {
            Log.logger.log(5, null, message, null);
        }
    }
    
    public static void error(final String category, final String message) {
        if (Log.ERROR) {
            Log.logger.log(5, category, message, null);
        }
    }
    
    public static void warn(final String message, final Throwable ex) {
        if (Log.WARN) {
            Log.logger.log(4, null, message, ex);
        }
    }
    
    public static void warn(final String category, final String message, final Throwable ex) {
        if (Log.WARN) {
            Log.logger.log(4, category, message, ex);
        }
    }
    
    public static void warn(final String message) {
        if (Log.WARN) {
            Log.logger.log(4, null, message, null);
        }
    }
    
    public static void warn(final String category, final String message) {
        if (Log.WARN) {
            Log.logger.log(4, category, message, null);
        }
    }
    
    public static void info(final String message, final Throwable ex) {
        if (Log.INFO) {
            Log.logger.log(3, null, message, ex);
        }
    }
    
    public static void info(final String category, final String message, final Throwable ex) {
        if (Log.INFO) {
            Log.logger.log(3, category, message, ex);
        }
    }
    
    public static void info(final String message) {
        if (Log.INFO) {
            Log.logger.log(3, null, message, null);
        }
    }
    
    public static void info(final String category, final String message) {
        if (Log.INFO) {
            Log.logger.log(3, category, message, null);
        }
    }
    
    public static void debug(final String message, final Throwable ex) {
        if (Log.DEBUG) {
            Log.logger.log(2, null, message, ex);
        }
    }
    
    public static void debug(final String category, final String message, final Throwable ex) {
        if (Log.DEBUG) {
            Log.logger.log(2, category, message, ex);
        }
    }
    
    public static void debug(final String message) {
        if (Log.DEBUG) {
            Log.logger.log(2, null, message, null);
        }
    }
    
    public static void debug(final String category, final String message) {
        if (Log.DEBUG) {
            Log.logger.log(2, category, message, null);
        }
    }
    
    public static void trace(final String message, final Throwable ex) {
        if (Log.TRACE) {
            Log.logger.log(1, null, message, ex);
        }
    }
    
    public static void trace(final String category, final String message, final Throwable ex) {
        if (Log.TRACE) {
            Log.logger.log(1, category, message, ex);
        }
    }
    
    public static void trace(final String message) {
        if (Log.TRACE) {
            Log.logger.log(1, null, message, null);
        }
    }
    
    public static void trace(final String category, final String message) {
        if (Log.TRACE) {
            Log.logger.log(1, category, message, null);
        }
    }
    
    public static class Logger
    {
        private long firstLogTime;
        
        public Logger() {
            this.firstLogTime = new Date().getTime();
        }
        
        public void log(final int level, final String category, final String message, final Throwable ex) {
            final StringBuilder builder = new StringBuilder(256);
            final long time = new Date().getTime() - this.firstLogTime;
            final long minutes = time / 60000L;
            final long seconds = time / 1000L % 60L;
            if (minutes <= 9L) {
                builder.append('0');
            }
            builder.append(minutes);
            builder.append(':');
            if (seconds <= 9L) {
                builder.append('0');
            }
            builder.append(seconds);
            switch (level) {
                case 5: {
                    builder.append(" ERROR: ");
                    break;
                }
                case 4: {
                    builder.append("  WARN: ");
                    break;
                }
                case 3: {
                    builder.append("  INFO: ");
                    break;
                }
                case 2: {
                    builder.append(" DEBUG: ");
                    break;
                }
                case 1: {
                    builder.append(" TRACE: ");
                    break;
                }
            }
            if (category != null) {
                builder.append('[');
                builder.append(category);
                builder.append("] ");
            }
            builder.append(message);
            if (ex != null) {
                final StringWriter writer = new StringWriter(256);
                ex.printStackTrace(new PrintWriter(writer));
                builder.append('\n');
                builder.append(writer.toString().trim());
            }
            this.print(builder.toString());
        }
        
        protected void print(final String message) {
            System.out.println(message);
        }
    }
}
