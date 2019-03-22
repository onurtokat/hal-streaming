// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree;

import com.github.fge.jackson.jsonpointer.JsonPointer;

public interface JsonTree extends SimpleTree
{
    JsonTree append(final JsonPointer p0);
}
