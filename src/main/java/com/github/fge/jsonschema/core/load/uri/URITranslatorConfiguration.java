// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load.uri;

import com.github.fge.Thawed;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.net.URI;
import com.github.fge.Frozen;

public final class URITranslatorConfiguration implements Frozen<URITranslatorConfigurationBuilder>
{
    final URI namespace;
    final Map<URI, URI> pathRedirects;
    final Map<URI, URI> schemaRedirects;
    
    public static URITranslatorConfigurationBuilder newBuilder() {
        return new URITranslatorConfigurationBuilder();
    }
    
    public static URITranslatorConfiguration byDefault() {
        return newBuilder().freeze();
    }
    
    URITranslatorConfiguration(final URITranslatorConfigurationBuilder builder) {
        this.namespace = builder.namespace;
        this.pathRedirects = (Map<URI, URI>)ImmutableMap.copyOf(builder.pathRedirects.build());
        this.schemaRedirects = (Map<URI, URI>)ImmutableMap.copyOf(builder.schemaRedirects.build());
    }
    
    @Override
    public URITranslatorConfigurationBuilder thaw() {
        return new URITranslatorConfigurationBuilder(this);
    }
}
