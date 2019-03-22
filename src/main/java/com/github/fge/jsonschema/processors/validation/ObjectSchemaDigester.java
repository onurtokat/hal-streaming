// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.validation;

import java.util.Set;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Ordering;
import java.util.Iterator;
import com.google.common.collect.Sets;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public final class ObjectSchemaDigester extends AbstractDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return ObjectSchemaDigester.INSTANCE;
    }
    
    private ObjectSchemaDigester() {
        super("", NodeType.OBJECT, new NodeType[0]);
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = ObjectSchemaDigester.FACTORY.objectNode();
        ret.put("hasAdditional", schema.path("additionalProperties").isObject());
        ArrayNode node = ObjectSchemaDigester.FACTORY.arrayNode();
        ret.put("properties", node);
        Set<String> set = (Set<String>)Sets.newHashSet((Iterator<?>)schema.path("properties").fieldNames());
        for (final String field : Ordering.natural().sortedCopy(set)) {
            node.add(field);
        }
        node = ObjectSchemaDigester.FACTORY.arrayNode();
        ret.put("patternProperties", node);
        set = (Set<String>)Sets.newHashSet((Iterator<?>)schema.path("patternProperties").fieldNames());
        for (final String field : Ordering.natural().sortedCopy(set)) {
            node.add(field);
        }
        return ret;
    }
    
    static {
        INSTANCE = new ObjectSchemaDigester();
    }
}
