// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.ref;

import java.net.URI;

final class HierarchicalJsonRef extends JsonRef
{
    HierarchicalJsonRef(final URI uri) {
        super(uri);
    }
    
    @Override
    public boolean isAbsolute() {
        return this.legal && this.locator.isAbsolute() && this.pointer.isEmpty();
    }
    
    @Override
    public JsonRef resolve(final JsonRef other) {
        return JsonRef.fromURI(this.uri.resolve(other.uri));
    }
}
