// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import java.util.EnumSet;

public interface Digester
{
    EnumSet<NodeType> supportedTypes();
    
    JsonNode digest(final JsonNode p0);
}
