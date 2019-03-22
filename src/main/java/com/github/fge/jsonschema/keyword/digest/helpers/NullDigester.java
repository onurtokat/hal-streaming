// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public final class NullDigester extends AbstractDigester
{
    public NullDigester(final String keyword, final NodeType first, final NodeType... other) {
        super(keyword, first, other);
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        return NullDigester.FACTORY.nullNode();
    }
}
