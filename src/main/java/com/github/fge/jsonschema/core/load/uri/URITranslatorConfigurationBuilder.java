// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load.uri;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.Frozen;
import com.github.fge.jsonschema.core.util.URIUtils;
import java.net.URI;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.Thawed;

public final class URITranslatorConfigurationBuilder implements Thawed<URITranslatorConfiguration>
{
    private static final MessageBundle BUNDLE;
    private static final URI EMPTY;
    URI namespace;
    final PathRedirectRegistry pathRedirects;
    final SchemaRedirectRegistry schemaRedirects;
    
    URITranslatorConfigurationBuilder() {
        this.namespace = URITranslatorConfigurationBuilder.EMPTY;
        this.pathRedirects = new PathRedirectRegistry();
        this.schemaRedirects = new SchemaRedirectRegistry();
    }
    
    URITranslatorConfigurationBuilder(final URITranslatorConfiguration cfg) {
        this.namespace = URITranslatorConfigurationBuilder.EMPTY;
        this.pathRedirects = new PathRedirectRegistry();
        this.schemaRedirects = new SchemaRedirectRegistry();
        this.namespace = cfg.namespace;
        this.pathRedirects.putAll(cfg.pathRedirects);
        this.schemaRedirects.putAll(cfg.schemaRedirects);
    }
    
    public URITranslatorConfigurationBuilder setNamespace(final URI uri) {
        URITranslatorConfigurationBuilder.BUNDLE.checkNotNull(uri, "uriChecks.nullInput");
        final URI normalized = URIUtils.normalizeURI(uri);
        URIUtils.checkPathURI(normalized);
        this.namespace = normalized;
        return this;
    }
    
    public URITranslatorConfigurationBuilder setNamespace(final String uri) {
        URITranslatorConfigurationBuilder.BUNDLE.checkNotNull(uri, "uriChecks.nullInput");
        return this.setNamespace(URI.create(uri));
    }
    
    public URITranslatorConfigurationBuilder addSchemaRedirect(final URI from, final URI to) {
        this.schemaRedirects.put(from, to);
        return this;
    }
    
    public URITranslatorConfigurationBuilder addSchemaRedirect(final String from, final String to) {
        URITranslatorConfigurationBuilder.BUNDLE.checkNotNull(from, "uriChecks.nullInput");
        URITranslatorConfigurationBuilder.BUNDLE.checkNotNull(to, "uriChecks.nullInput");
        return this.addSchemaRedirect(URI.create(from), URI.create(to));
    }
    
    public URITranslatorConfigurationBuilder addPathRedirect(final URI from, final URI to) {
        this.pathRedirects.put(from, to);
        return this;
    }
    
    public URITranslatorConfigurationBuilder addPathRedirect(final String from, final String to) {
        URITranslatorConfigurationBuilder.BUNDLE.checkNotNull(from, "uriChecks.nullInput");
        URITranslatorConfigurationBuilder.BUNDLE.checkNotNull(to, "uriChecks.nullInput");
        return this.addPathRedirect(URI.create(from), URI.create(to));
    }
    
    @Override
    public URITranslatorConfiguration freeze() {
        return new URITranslatorConfiguration(this);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
        EMPTY = URI.create("");
    }
}
