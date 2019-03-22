// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.ref;

import java.net.URI;

final class JarJsonRef extends JsonRef
{
    private final String jarPrefix;
    private final URI pathURI;
    
    JarJsonRef(final URI uri) {
        super(uri);
        final String str = uri.toString();
        final int index = str.indexOf(33);
        this.jarPrefix = str.substring(0, index + 1);
        final String path = str.substring(index + 1);
        this.pathURI = URI.create(path);
    }
    
    private JarJsonRef(final URI uri, final String jarPrefix, final URI pathURI) {
        super(uri);
        this.jarPrefix = jarPrefix;
        this.pathURI = pathURI;
    }
    
    @Override
    public boolean isAbsolute() {
        return this.legal && this.pointer.isEmpty();
    }
    
    @Override
    public JsonRef resolve(final JsonRef other) {
        if (other.uri.isAbsolute()) {
            return other;
        }
        final URI targetPath = this.pathURI.resolve(other.uri);
        final URI targetURI = URI.create(this.jarPrefix + targetPath.toString());
        return new JarJsonRef(targetURI, this.jarPrefix, targetPath);
    }
}
