// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.common;

import java.util.List;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Collections;
import java.util.Iterator;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public final class AdditionalPropertiesDigester extends AbstractDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return AdditionalPropertiesDigester.INSTANCE;
    }
    
    private AdditionalPropertiesDigester() {
        super("additionalProperties", NodeType.OBJECT, new NodeType[0]);
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = AdditionalPropertiesDigester.FACTORY.objectNode();
        final ArrayNode properties = AdditionalPropertiesDigester.FACTORY.arrayNode();
        final ArrayNode patternProperties = AdditionalPropertiesDigester.FACTORY.arrayNode();
        ret.put(this.keyword, true);
        ret.put("properties", properties);
        ret.put("patternProperties", patternProperties);
        if (schema.get(this.keyword).asBoolean(true)) {
            return ret;
        }
        ret.put(this.keyword, false);
        List<String> list = (List<String>)Lists.newArrayList((Iterator<?>)schema.path("properties").fieldNames());
        Collections.sort(list);
        for (final String s : list) {
            properties.add(s);
        }
        list = (List<String>)Lists.newArrayList((Iterator<?>)schema.path("patternProperties").fieldNames());
        Collections.sort(list);
        for (final String s : list) {
            patternProperties.add(s);
        }
        return ret;
    }
    
    static {
        INSTANCE = new AdditionalPropertiesDigester();
    }
}
