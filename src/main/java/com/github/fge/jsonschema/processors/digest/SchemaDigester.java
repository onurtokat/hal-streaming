// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.digest;

import com.github.fge.jsonschema.core.report.MessageProvider;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collection;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import java.util.Iterator;
import com.google.common.collect.Maps;
import com.google.common.collect.ArrayListMultimap;
import com.github.fge.jsonschema.core.util.Dictionary;
import com.github.fge.jsonschema.library.Library;
import com.github.fge.jsonschema.keyword.digest.Digester;
import java.util.Map;
import com.github.fge.jackson.NodeType;
import com.google.common.collect.ListMultimap;
import com.github.fge.jsonschema.processors.data.SchemaDigest;
import com.github.fge.jsonschema.processors.data.SchemaContext;
import com.github.fge.jsonschema.core.processing.Processor;

public final class SchemaDigester implements Processor<SchemaContext, SchemaDigest>
{
    private final ListMultimap<NodeType, String> typeMap;
    private final Map<String, Digester> digesterMap;
    
    public SchemaDigester(final Library library) {
        this(library.getDigesters());
    }
    
    public SchemaDigester(final Dictionary<Digester> dict) {
        this.typeMap = (ListMultimap<NodeType, String>)ArrayListMultimap.create();
        this.digesterMap = (Map<String, Digester>)Maps.newHashMap();
        final Map<String, Digester> map = dict.entries();
        for (final Map.Entry<String, Digester> entry : map.entrySet()) {
            final String keyword = entry.getKey();
            final Digester digester = entry.getValue();
            this.digesterMap.put(keyword, digester);
            for (final NodeType type : digester.supportedTypes()) {
                this.typeMap.put(type, keyword);
            }
        }
    }
    
    @Override
    public SchemaDigest process(final ProcessingReport report, final SchemaContext input) throws ProcessingException {
        final JsonNode schema = input.getSchema().getNode();
        final NodeType type = input.getInstanceType();
        final Map<String, JsonNode> map = (Map<String, JsonNode>)Maps.newHashMap((Map<?, ?>)this.buildDigests(schema));
        map.keySet().retainAll(this.typeMap.get(type));
        return new SchemaDigest(input, map);
    }
    
    private Map<String, JsonNode> buildDigests(final JsonNode schema) {
        final ImmutableMap.Builder<String, JsonNode> builder = ImmutableMap.builder();
        final Map<String, Digester> map = (Map<String, Digester>)Maps.newHashMap((Map<?, ?>)this.digesterMap);
        map.keySet().retainAll(Sets.newHashSet((Iterator<?>)schema.fieldNames()));
        for (final Map.Entry<String, Digester> entry : map.entrySet()) {
            builder.put(entry.getKey(), entry.getValue().digest(schema));
        }
        return builder.build();
    }
    
    @Override
    public String toString() {
        return "schema digester";
    }
}
