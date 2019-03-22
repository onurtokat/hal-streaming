// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util;

import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import javax.annotation.concurrent.Immutable;
import com.github.fge.jsonschema.core.report.MessageProvider;

@Immutable
public abstract class ValueHolder<T> implements MessageProvider
{
    protected static final JsonNodeFactory FACTORY;
    private final String name;
    protected final T value;
    
    public static <V> ValueHolder<V> hold(final V value) {
        return new SimpleValueHolder<V>("value", value);
    }
    
    public static <V> ValueHolder<V> hold(final String name, final V value) {
        return new SimpleValueHolder<V>(name, value);
    }
    
    public static <V extends AsJson> ValueHolder<V> hold(final V value) {
        return new AsJsonValueHolder<V>("value", value);
    }
    
    public static <V extends AsJson> ValueHolder<V> hold(final String name, final V value) {
        return new AsJsonValueHolder<V>(name, value);
    }
    
    protected ValueHolder(final String name, final T value) {
        this.name = name;
        this.value = value;
    }
    
    protected abstract JsonNode valueAsJson();
    
    public final String getName() {
        return this.name;
    }
    
    public final T getValue() {
        return this.value;
    }
    
    @Override
    public final ProcessingMessage newMessage() {
        return new ProcessingMessage().put(this.name, this.valueAsJson());
    }
    
    static {
        FACTORY = JacksonUtils.nodeFactory();
    }
}
