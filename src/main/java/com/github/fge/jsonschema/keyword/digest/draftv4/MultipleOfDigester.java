// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.draftv4;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.digest.helpers.NumericDigester;

public final class MultipleOfDigester extends NumericDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return MultipleOfDigester.INSTANCE;
    }
    
    private MultipleOfDigester() {
        super("multipleOf");
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        return this.digestedNumberNode(schema);
    }
    
    static {
        INSTANCE = new MultipleOfDigester();
    }
}
