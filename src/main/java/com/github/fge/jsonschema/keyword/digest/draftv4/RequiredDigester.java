// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.draftv4;

import java.util.List;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public final class RequiredDigester extends AbstractDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return RequiredDigester.INSTANCE;
    }
    
    private RequiredDigester() {
        super("required", NodeType.OBJECT, new NodeType[0]);
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = RequiredDigester.FACTORY.objectNode();
        final ArrayNode required = RequiredDigester.FACTORY.arrayNode();
        ret.put(this.keyword, required);
        final List<JsonNode> list = (List<JsonNode>)Lists.newArrayList((Iterable<?>)schema.get(this.keyword));
        Collections.sort(list, new Comparator<JsonNode>() {
            @Override
            public int compare(final JsonNode o1, final JsonNode o2) {
                return o1.textValue().compareTo(o2.textValue());
            }
        });
        required.addAll(list);
        return ret;
    }
    
    static {
        INSTANCE = new RequiredDigester();
    }
}
