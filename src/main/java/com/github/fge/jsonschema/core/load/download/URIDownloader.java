// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public interface URIDownloader
{
    InputStream fetch(final URI p0) throws IOException;
}
