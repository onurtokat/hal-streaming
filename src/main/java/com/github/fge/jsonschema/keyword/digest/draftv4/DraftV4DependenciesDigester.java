// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.draftv4;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Ordering;
import java.util.Map;
import com.github.fge.jackson.JacksonUtils;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public final class DraftV4DependenciesDigester extends AbstractDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return DraftV4DependenciesDigester.INSTANCE;
    }
    
    private DraftV4DependenciesDigester() {
        super("dependencies", NodeType.OBJECT, new NodeType[0]);
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = DraftV4DependenciesDigester.FACTORY.objectNode();
        final ObjectNode propertyDeps = DraftV4DependenciesDigester.FACTORY.objectNode();
        ret.put("propertyDeps", propertyDeps);
        final ArrayNode schemaDeps = DraftV4DependenciesDigester.FACTORY.arrayNode();
        ret.put("schemaDeps", schemaDeps);
        final List<String> list = (List<String>)Lists.newArrayList();
        final Map<String, JsonNode> map = JacksonUtils.asMap(schema.get(this.keyword));
        for (final Map.Entry<String, JsonNode> entry : map.entrySet()) {
            final String key = entry.getKey();
            final JsonNode value = entry.getValue();
            if (value.isObject()) {
                list.add(key);
            }
            else {
                propertyDeps.put(key, sortedSet(value));
            }
        }
        for (final String s : Ordering.natural().sortedCopy(list)) {
            schemaDeps.add(s);
        }
        return ret;
    }
    
    private static JsonNode sortedSet(final JsonNode node) {
        final List<JsonNode> list = (List<JsonNode>)Lists.newArrayList((Iterable<?>)node);
        Collections.sort(list, new Comparator<JsonNode>() {
            @Override
            public int compare(final JsonNode o1, final JsonNode o2) {
                return o1.textValue().compareTo(o2.textValue());
            }
        });
        final ArrayNode ret = DraftV4DependenciesDigester.FACTORY.arrayNode();
        ret.addAll(list);
        return ret;
    }
    
    static {
        INSTANCE = new DraftV4DependenciesDigester();
    }
}
