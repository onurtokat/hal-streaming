// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree;

import javax.annotation.Nullable;
import com.github.fge.jsonschema.core.ref.JsonRef;
import com.github.fge.jackson.jsonpointer.JsonPointer;

public interface SchemaTree extends SimpleTree
{
    SchemaTree append(final JsonPointer p0);
    
    SchemaTree setPointer(final JsonPointer p0);
    
    JsonRef resolve(final JsonRef p0);
    
    boolean containsRef(final JsonRef p0);
    
    @Nullable
    JsonPointer matchingPointer(final JsonRef p0);
    
    @Deprecated
    long getId();
    
    JsonRef getDollarSchema();
    
    JsonRef getLoadingRef();
    
    JsonRef getContext();
}
