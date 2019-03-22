// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.draftv3;

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

public final class DraftV3PropertiesDigester extends AbstractDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return DraftV3PropertiesDigester.INSTANCE;
    }
    
    private DraftV3PropertiesDigester() {
        super("properties", NodeType.OBJECT, new NodeType[0]);
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = DraftV3PropertiesDigester.FACTORY.objectNode();
        final ArrayNode required = DraftV3PropertiesDigester.FACTORY.arrayNode();
        ret.put("required", required);
        final JsonNode node = schema.get(this.keyword);
        final List<String> list = (List<String>)Lists.newArrayList((Iterator<?>)node.fieldNames());
        Collections.sort(list);
        for (final String field : list) {
            if (node.get(field).path("required").asBoolean(false)) {
                required.add(field);
            }
        }
        return ret;
    }
    
    static {
        INSTANCE = new DraftV3PropertiesDigester();
    }
}
