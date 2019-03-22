// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

import java.util.List;
import com.google.common.collect.Lists;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

public abstract class AbstractProcessingReport implements ProcessingReport
{
    private LogLevel currentLevel;
    private final LogLevel logLevel;
    private final LogLevel exceptionThreshold;
    
    protected AbstractProcessingReport(final LogLevel logLevel, final LogLevel exceptionThreshold) {
        this.currentLevel = LogLevel.DEBUG;
        this.logLevel = logLevel;
        this.exceptionThreshold = exceptionThreshold;
    }
    
    protected AbstractProcessingReport(final LogLevel logLevel) {
        this(logLevel, LogLevel.FATAL);
    }
    
    protected AbstractProcessingReport() {
        this(LogLevel.INFO, LogLevel.FATAL);
    }
    
    @Override
    public final LogLevel getLogLevel() {
        return this.logLevel;
    }
    
    @Override
    public final LogLevel getExceptionThreshold() {
        return this.exceptionThreshold;
    }
    
    @Override
    public final void debug(final ProcessingMessage message) throws ProcessingException {
        this.dispatch(message.setLogLevel(LogLevel.DEBUG));
    }
    
    @Override
    public final void info(final ProcessingMessage message) throws ProcessingException {
        this.dispatch(message.setLogLevel(LogLevel.INFO));
    }
    
    @Override
    public final void warn(final ProcessingMessage message) throws ProcessingException {
        this.dispatch(message.setLogLevel(LogLevel.WARNING));
    }
    
    @Override
    public final void error(final ProcessingMessage message) throws ProcessingException {
        this.dispatch(message.setLogLevel(LogLevel.ERROR));
    }
    
    @Override
    public final void fatal(final ProcessingMessage message) throws ProcessingException {
        this.dispatch(message.setLogLevel(LogLevel.FATAL));
    }
    
    @Override
    public final boolean isSuccess() {
        return this.currentLevel.compareTo(LogLevel.ERROR) < 0;
    }
    
    public abstract void log(final LogLevel p0, final ProcessingMessage p1);
    
    protected final void dispatch(final ProcessingMessage message) throws ProcessingException {
        final LogLevel level = message.getLogLevel();
        if (level.compareTo(this.exceptionThreshold) >= 0) {
            throw message.asException();
        }
        if (level.compareTo(this.currentLevel) > 0) {
            this.currentLevel = level;
        }
        if (level.compareTo(this.logLevel) >= 0) {
            this.log(level, message);
        }
    }
    
    @Override
    public Iterator<ProcessingMessage> iterator() {
        return (Iterator<ProcessingMessage>)Iterators.emptyIterator();
    }
    
    @Override
    public final void mergeWith(final ProcessingReport other) throws ProcessingException {
        if (!other.isSuccess() && this.currentLevel.compareTo(LogLevel.ERROR) < 0) {
            this.currentLevel = LogLevel.ERROR;
        }
        for (final ProcessingMessage message : other) {
            this.dispatch(message);
        }
    }
    
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getCanonicalName()).append(": ").append(this.isSuccess() ? "success" : "failure").append('\n');
        final List<ProcessingMessage> messages = (List<ProcessingMessage>)Lists.newArrayList((Iterable<?>)this);
        if (!messages.isEmpty()) {
            sb.append("--- BEGIN MESSAGES ---\n");
            for (final ProcessingMessage message : messages) {
                sb.append(message);
            }
            sb.append("---  END MESSAGES  ---\n");
        }
        return sb.toString();
    }
}
