// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load.uri;

import java.util.Iterator;
import com.github.fge.jsonschema.core.ref.JsonRef;
import java.net.URISyntaxException;
import com.github.fge.jsonschema.core.util.URIUtils;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.net.URI;

public final class URITranslator
{
    private final URI namespace;
    private final Map<URI, URI> pathRedirects;
    private final Map<URI, URI> schemaRedirects;
    
    public URITranslator(final URITranslatorConfiguration cfg) {
        this.namespace = cfg.namespace;
        this.pathRedirects = (Map<URI, URI>)ImmutableMap.copyOf((Map<?, ?>)cfg.pathRedirects);
        this.schemaRedirects = (Map<URI, URI>)ImmutableMap.copyOf((Map<?, ?>)cfg.schemaRedirects);
    }
    
    public URI translate(final URI source) {
        URI uri = URIUtils.normalizeURI(this.namespace.resolve(source));
        final String fragment = uri.getFragment();
        try {
            uri = new URI(uri.getScheme(), uri.getSchemeSpecificPart(), null);
        }
        catch (URISyntaxException e) {
            throw new IllegalStateException("How did I get there??", e);
        }
        for (final Map.Entry<URI, URI> entry : this.pathRedirects.entrySet()) {
            final URI relative = entry.getKey().relativize(uri);
            if (!relative.equals(uri)) {
                uri = entry.getValue().resolve(relative);
            }
        }
        uri = JsonRef.fromURI(uri).getLocator();
        if (this.schemaRedirects.containsKey(uri)) {
            uri = this.schemaRedirects.get(uri);
        }
        try {
            return new URI(uri.getScheme(), uri.getSchemeSpecificPart(), fragment);
        }
        catch (URISyntaxException e) {
            throw new IllegalStateException("How did I get there??", e);
        }
    }
}
