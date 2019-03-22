// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.report;

import com.github.fge.jackson.JacksonUtils;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.IllegalFormatException;
import java.util.Formatter;
import java.util.Iterator;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.github.fge.jsonschema.core.exceptions.ExceptionProvider;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.NotThreadSafe;
import com.github.fge.jsonschema.core.util.AsJson;

@NotThreadSafe
public final class ProcessingMessage implements AsJson
{
    private static final MessageBundle BUNDLE;
    private static final JsonNodeFactory FACTORY;
    private final Map<String, JsonNode> map;
    private final List<Object> args;
    private ExceptionProvider exceptionProvider;
    private LogLevel level;
    
    public ProcessingMessage() {
        this.map = (Map<String, JsonNode>)Maps.newLinkedHashMap();
        this.args = Lists.newArrayList();
        this.exceptionProvider = SimpleExceptionProvider.getInstance();
        this.setLogLevel(LogLevel.INFO);
    }
    
    public String getMessage() {
        return this.map.containsKey("message") ? this.map.get("message").textValue() : "(no message)";
    }
    
    public LogLevel getLogLevel() {
        return this.level;
    }
    
    public ProcessingMessage setMessage(final String message) {
        this.args.clear();
        return this.put("message", message);
    }
    
    public ProcessingMessage setLogLevel(final LogLevel level) {
        ProcessingMessage.BUNDLE.checkNotNull(level, "processing.nullLevel");
        this.level = level;
        return this.put("level", level);
    }
    
    public ProcessingMessage setExceptionProvider(final ExceptionProvider exceptionProvider) {
        ProcessingMessage.BUNDLE.checkNotNull(exceptionProvider, "processing.nullExceptionProvider");
        this.exceptionProvider = exceptionProvider;
        return this;
    }
    
    public ProcessingMessage put(final String key, final JsonNode value) {
        if (key == null) {
            return this;
        }
        if (value == null) {
            return this.putNull(key);
        }
        this.map.put(key, value.deepCopy());
        return this;
    }
    
    public ProcessingMessage putArgument(final String key, final JsonNode value) {
        this.addArgument(key, value);
        return this.put(key, value);
    }
    
    public ProcessingMessage put(final String key, final AsJson asJson) {
        return this.put(key, asJson.asJson());
    }
    
    public ProcessingMessage putArgument(final String key, final AsJson asJson) {
        this.addArgument(key, asJson.asJson());
        return this.put(key, asJson);
    }
    
    public ProcessingMessage put(final String key, final String value) {
        return (value == null) ? this.putNull(key) : this.put(key, (JsonNode)ProcessingMessage.FACTORY.textNode(value));
    }
    
    public ProcessingMessage put(final String key, final int value) {
        return this.put(key, (JsonNode)ProcessingMessage.FACTORY.numberNode(value));
    }
    
    public ProcessingMessage putArgument(final String key, final int value) {
        this.addArgument(key, value);
        return this.put(key, value);
    }
    
    public <T> ProcessingMessage put(final String key, final T value) {
        return (value == null) ? this.putNull(key) : this.put(key, (JsonNode)ProcessingMessage.FACTORY.textNode(value.toString()));
    }
    
    public <T> ProcessingMessage putArgument(final String key, final T value) {
        this.addArgument(key, value);
        return this.put(key, (Object)value);
    }
    
    public <T> ProcessingMessage put(final String key, final Iterable<T> values) {
        if (values == null) {
            return this.putNull(key);
        }
        final ArrayNode node = ProcessingMessage.FACTORY.arrayNode();
        for (final T value : values) {
            node.add((value == null) ? ProcessingMessage.FACTORY.nullNode() : ProcessingMessage.FACTORY.textNode(value.toString()));
        }
        return this.put(key, (JsonNode)node);
    }
    
    public <T> ProcessingMessage putArgument(final String key, final Iterable<T> values) {
        this.addArgument(key, values);
        return this.put(key, (Iterable<Object>)values);
    }
    
    private void addArgument(final String key, final Object value) {
        if (key != null) {
            this.args.add(value);
        }
        if (!this.map.containsKey("message")) {
            return;
        }
        final String fmt = this.map.get("message").textValue();
        try {
            final String formatted = new Formatter().format(fmt, this.args.toArray()).toString();
            this.map.put("message", ProcessingMessage.FACTORY.textNode(formatted));
        }
        catch (IllegalFormatException ex) {}
    }
    
    private ProcessingMessage putNull(final String key) {
        if (key == null) {
            return this;
        }
        this.map.put(key, ProcessingMessage.FACTORY.nullNode());
        return this;
    }
    
    @Override
    public JsonNode asJson() {
        final ObjectNode ret = ProcessingMessage.FACTORY.objectNode();
        ret.putAll(this.map);
        return ret;
    }
    
    public ProcessingException asException() {
        return this.exceptionProvider.doException(this);
    }
    
    @Override
    public String toString() {
        final Map<String, JsonNode> tmp = (Map<String, JsonNode>)Maps.newLinkedHashMap((Map<?, ?>)this.map);
        final JsonNode node = tmp.remove("message");
        final String message = (node == null) ? "(no message)" : node.textValue();
        final StringBuilder sb = new StringBuilder().append(this.level).append(": ");
        sb.append(message);
        for (final Map.Entry<String, JsonNode> entry : tmp.entrySet()) {
            sb.append("\n    ").append(entry.getKey()).append(": ").append(entry.getValue());
        }
        return sb.append('\n').toString();
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
        FACTORY = JacksonUtils.nodeFactory();
    }
}
