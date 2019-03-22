// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public final class ResourceURIDownloader implements URIDownloader
{
    private static final Class<ResourceURIDownloader> MYSELF;
    private static final URIDownloader INSTANCE;
    
    public static URIDownloader getInstance() {
        return ResourceURIDownloader.INSTANCE;
    }
    
    @Override
    public InputStream fetch(final URI source) throws IOException {
        final String resource = source.getPath();
        final InputStream in = ResourceURIDownloader.MYSELF.getResourceAsStream(resource);
        if (in == null) {
            throw new IOException("resource " + resource + " not found");
        }
        return in;
    }
    
    static {
        MYSELF = ResourceURIDownloader.class;
        INSTANCE = new ResourceURIDownloader();
    }
}
