// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.Writer;
import java.io.StringWriter;
import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public final class JacksonUtils
{
    private static final JsonNodeFactory FACTORY;
    private static final ObjectReader READER;
    private static final ObjectWriter WRITER;
    
    public static ObjectReader getReader() {
        return JacksonUtils.READER;
    }
    
    public static JsonNodeFactory nodeFactory() {
        return JacksonUtils.FACTORY;
    }
    
    public static Map<String, JsonNode> asMap(final JsonNode node) {
        if (!node.isObject()) {
            return Collections.emptyMap();
        }
        final Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        final Map<String, JsonNode> ret = (Map<String, JsonNode>)Maps.newHashMap();
        while (iterator.hasNext()) {
            final Map.Entry<String, JsonNode> entry = iterator.next();
            ret.put(entry.getKey(), entry.getValue());
        }
        return ret;
    }
    
    public static String prettyPrint(final JsonNode node) {
        final StringWriter writer = new StringWriter();
        try {
            JacksonUtils.WRITER.writeValue(writer, node);
            writer.flush();
        }
        catch (JsonGenerationException e) {
            throw new RuntimeException("How did I get there??", e);
        }
        catch (JsonMappingException e2) {
            throw new RuntimeException("How did I get there??", e2);
        }
        catch (IOException ex) {}
        return writer.toString();
    }
    
    public static ObjectMapper newMapper() {
        return new ObjectMapper().setNodeFactory(JacksonUtils.FACTORY).enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS).enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN).enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    static {
        FACTORY = JsonNodeFactory.instance;
        final ObjectMapper mapper = newMapper();
        READER = mapper.reader();
        WRITER = mapper.writer();
    }
}
