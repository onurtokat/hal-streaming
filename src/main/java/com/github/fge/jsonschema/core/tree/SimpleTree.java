// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree;

import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.util.AsJson;

public interface SimpleTree extends AsJson
{
    JsonNode getBaseNode();
    
    JsonPointer getPointer();
    
    JsonNode getNode();
}
