// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public final class DefaultURIDownloader implements URIDownloader
{
    private static final URIDownloader INSTANCE;
    
    public static URIDownloader getInstance() {
        return DefaultURIDownloader.INSTANCE;
    }
    
    @Override
    public InputStream fetch(final URI source) throws IOException {
        return source.toURL().openStream();
    }
    
    static {
        INSTANCE = new DefaultURIDownloader();
    }
}
