// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.draftv3;

import java.util.SortedSet;
import java.util.Collection;
import com.google.common.collect.Sets;
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

public final class DraftV3DependenciesDigester extends AbstractDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return DraftV3DependenciesDigester.INSTANCE;
    }
    
    private DraftV3DependenciesDigester() {
        super("dependencies", NodeType.OBJECT, new NodeType[0]);
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = DraftV3DependenciesDigester.FACTORY.objectNode();
        final ObjectNode propertyDeps = DraftV3DependenciesDigester.FACTORY.objectNode();
        ret.put("propertyDeps", propertyDeps);
        final ArrayNode schemaDeps = DraftV3DependenciesDigester.FACTORY.arrayNode();
        ret.put("schemaDeps", schemaDeps);
        final List<String> list = (List<String>)Lists.newArrayList();
        final Map<String, JsonNode> map = JacksonUtils.asMap(schema.get(this.keyword));
        for (final Map.Entry<String, JsonNode> entry : map.entrySet()) {
            final String key = entry.getKey();
            final JsonNode value = entry.getValue();
            final NodeType type = NodeType.getNodeType(value);
            switch (type) {
                case OBJECT: {
                    list.add(key);
                    continue;
                }
                case ARRAY: {
                    final JsonNode node = sortedSet(value);
                    if (node.size() != 0) {
                        propertyDeps.put(key, node);
                        continue;
                    }
                    continue;
                }
                case STRING: {
                    propertyDeps.put(key, DraftV3DependenciesDigester.FACTORY.arrayNode().add(value.textValue()));
                    continue;
                }
            }
        }
        for (final String s : Ordering.natural().sortedCopy(list)) {
            schemaDeps.add(s);
        }
        return ret;
    }
    
    private static JsonNode sortedSet(final JsonNode node) {
        final SortedSet<JsonNode> set = (SortedSet<JsonNode>)Sets.newTreeSet((Comparator<? super Object>)new Comparator<JsonNode>() {
            @Override
            public int compare(final JsonNode o1, final JsonNode o2) {
                return o1.textValue().compareTo(o2.textValue());
            }
        });
        set.addAll((Collection<?>)Sets.newHashSet((Iterable<?>)node));
        final ArrayNode ret = DraftV3DependenciesDigester.FACTORY.arrayNode();
        ret.addAll(set);
        return ret;
    }
    
    static {
        INSTANCE = new DraftV3DependenciesDigester();
    }
}
