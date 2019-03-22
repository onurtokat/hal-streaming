// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import java.util.concurrent.ExecutionException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.ref.JsonRef;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import javax.annotation.Nonnull;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import com.google.common.cache.LoadingCache;
import com.github.fge.jsonschema.core.load.uri.URITranslator;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class SchemaLoader
{
    private static final MessageBundle BUNDLE;
    private final URIManager manager;
    private final URITranslator translator;
    private final LoadingCache<URI, JsonNode> cache;
    private final Dereferencing dereferencing;
    private final Map<URI, JsonNode> preloadedSchemas;
    
    public SchemaLoader(final LoadingConfiguration cfg) {
        this.translator = new URITranslator(cfg.getTranslatorConfiguration());
        this.dereferencing = cfg.getDereferencing();
        this.manager = new URIManager(cfg);
        this.preloadedSchemas = (Map<URI, JsonNode>)ImmutableMap.copyOf((Map<?, ?>)cfg.getPreloadedSchemas());
        final CacheBuilder<Object, Object> cacheBuilder = cfg.getEnableCache() ? CacheBuilder.newBuilder() : CacheBuilder.from(CacheBuilderSpec.disableCaching());
        this.cache = cacheBuilder.build((CacheLoader<? super URI, JsonNode>)new CacheLoader<URI, JsonNode>() {
            @Nonnull
            @Override
            public JsonNode load(@Nonnull final URI key) throws ProcessingException {
                return SchemaLoader.this.manager.getContent(key);
            }
        });
    }
    
    public SchemaLoader() {
        this(LoadingConfiguration.byDefault());
    }
    
    public SchemaTree load(final JsonNode schema) {
        SchemaLoader.BUNDLE.checkNotNull(schema, "loadingCfg.nullSchema");
        return this.dereferencing.newTree(schema);
    }
    
    public SchemaTree get(final URI uri) throws ProcessingException {
        final JsonRef ref = JsonRef.fromURI(this.translator.translate(uri));
        if (!ref.isAbsolute()) {
            throw new ProcessingException(new ProcessingMessage().setMessage(SchemaLoader.BUNDLE.getMessage("refProcessing.uriNotAbsolute")).putArgument("uri", ref));
        }
        final URI realURI = ref.toURI();
        try {
            JsonNode node = this.preloadedSchemas.get(realURI);
            if (node == null) {
                node = this.cache.get(realURI);
            }
            return this.dereferencing.newTree(ref, node);
        }
        catch (ExecutionException e) {
            throw (ProcessingException)e.getCause();
        }
    }
    
    @Override
    public String toString() {
        return this.cache.toString();
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
