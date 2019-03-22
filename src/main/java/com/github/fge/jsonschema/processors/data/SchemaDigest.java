// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.data;

import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.google.common.collect.ImmutableMap;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import com.github.fge.jsonschema.core.report.MessageProvider;

public final class SchemaDigest implements MessageProvider
{
    private final SchemaContext context;
    private final Map<String, JsonNode> digested;
    
    public SchemaDigest(final SchemaContext context, final Map<String, JsonNode> map) {
        this.context = context;
        this.digested = (Map<String, JsonNode>)ImmutableMap.copyOf((Map<?, ?>)map);
    }
    
    public SchemaContext getContext() {
        return this.context;
    }
    
    public Map<String, JsonNode> getDigests() {
        return this.digested;
    }
    
    @Override
    public ProcessingMessage newMessage() {
        return this.context.newMessage();
    }
}
