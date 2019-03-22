// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

import com.github.fge.jackson.JacksonUtils;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import java.util.List;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fge.jsonschema.core.util.AsJson;

public final class ListProcessingReport extends AbstractProcessingReport implements AsJson
{
    private static final JsonNodeFactory FACTORY;
    private final List<ProcessingMessage> messages;
    
    public ListProcessingReport(final LogLevel logLevel, final LogLevel exceptionThreshold) {
        super(logLevel, exceptionThreshold);
        this.messages = (List<ProcessingMessage>)Lists.newArrayList();
    }
    
    public ListProcessingReport(final LogLevel logLevel) {
        super(logLevel);
        this.messages = (List<ProcessingMessage>)Lists.newArrayList();
    }
    
    public ListProcessingReport() {
        this.messages = (List<ProcessingMessage>)Lists.newArrayList();
    }
    
    public ListProcessingReport(final ProcessingReport other) {
        this(other.getLogLevel(), other.getExceptionThreshold());
    }
    
    @Override
    public void log(final LogLevel level, final ProcessingMessage message) {
        this.messages.add(message);
    }
    
    @Override
    public JsonNode asJson() {
        final ArrayNode ret = ListProcessingReport.FACTORY.arrayNode();
        for (final ProcessingMessage message : this.messages) {
            ret.add(message.asJson());
        }
        return ret;
    }
    
    @Override
    public Iterator<ProcessingMessage> iterator() {
        return Iterators.unmodifiableIterator(this.messages.iterator());
    }
    
    static {
        FACTORY = JacksonUtils.nodeFactory();
    }
}
