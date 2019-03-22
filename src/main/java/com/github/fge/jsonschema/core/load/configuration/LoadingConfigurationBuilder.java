// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load.configuration;

import com.github.fge.jsonschema.core.util.Registry;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.Frozen;
import com.github.fge.jsonschema.core.exceptions.JsonReferenceException;
import com.github.fge.jsonschema.core.ref.JsonRef;
import com.github.fge.jsonschema.core.load.download.URIDownloader;
import com.github.fge.jsonschema.SchemaVersion;
import com.google.common.collect.Maps;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.util.Map;
import com.github.fge.jsonschema.core.load.Dereferencing;
import com.github.fge.jsonschema.core.load.uri.URITranslatorConfiguration;
import com.fasterxml.jackson.core.JsonParser;
import java.util.EnumSet;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.Thawed;

public final class LoadingConfigurationBuilder implements Thawed<LoadingConfiguration>
{
    private static final MessageBundle BUNDLE;
    private static final EnumSet<JsonParser.Feature> DEFAULT_PARSER_FEATURES;
    final URIDownloadersRegistry downloaders;
    URITranslatorConfiguration translatorCfg;
    boolean enableCache;
    Dereferencing dereferencing;
    final Map<URI, JsonNode> preloadedSchemas;
    final EnumSet<JsonParser.Feature> parserFeatures;
    
    LoadingConfigurationBuilder() {
        this.downloaders = new URIDownloadersRegistry();
        this.enableCache = true;
        this.translatorCfg = URITranslatorConfiguration.byDefault();
        this.dereferencing = Dereferencing.CANONICAL;
        this.preloadedSchemas = (Map<URI, JsonNode>)Maps.newHashMap();
        for (final SchemaVersion version : SchemaVersion.values()) {
            this.preloadedSchemas.put(version.getLocation(), version.getSchema());
        }
        this.parserFeatures = EnumSet.copyOf(LoadingConfigurationBuilder.DEFAULT_PARSER_FEATURES);
    }
    
    LoadingConfigurationBuilder(final LoadingConfiguration cfg) {
        this.downloaders = new URIDownloadersRegistry();
        this.enableCache = true;
        this.downloaders.putAll(cfg.downloaders);
        this.translatorCfg = cfg.translatorCfg;
        this.dereferencing = cfg.dereferencing;
        this.preloadedSchemas = (Map<URI, JsonNode>)Maps.newHashMap((Map<?, ?>)cfg.preloadedSchemas);
        this.parserFeatures = EnumSet.copyOf(cfg.parserFeatures);
        this.enableCache = cfg.enableCache;
    }
    
    public LoadingConfigurationBuilder setEnableCache(final boolean enableCache) {
        this.enableCache = enableCache;
        return this;
    }
    
    public LoadingConfigurationBuilder addScheme(final String scheme, final URIDownloader downloader) {
        this.downloaders.put(scheme, downloader);
        return this;
    }
    
    public LoadingConfigurationBuilder removeScheme(final String scheme) {
        ((Registry<String, Object>)this.downloaders).remove(scheme);
        return this;
    }
    
    public LoadingConfigurationBuilder setURITranslatorConfiguration(final URITranslatorConfiguration translatorCfg) {
        this.translatorCfg = translatorCfg;
        return this;
    }
    
    public LoadingConfigurationBuilder dereferencing(final Dereferencing dereferencing) {
        LoadingConfigurationBuilder.BUNDLE.checkNotNull(dereferencing, "loadingCfg.nullDereferencingMode");
        this.dereferencing = dereferencing;
        return this;
    }
    
    public LoadingConfigurationBuilder preloadSchema(final String uri, final JsonNode schema) {
        LoadingConfigurationBuilder.BUNDLE.checkNotNull(schema, "loadingCfg.nullSchema");
        final URI key = getLocator(uri);
        LoadingConfigurationBuilder.BUNDLE.checkArgumentPrintf(this.preloadedSchemas.put(key, schema) == null, "loadingCfg.duplicateURI", key);
        return this;
    }
    
    public LoadingConfigurationBuilder preloadSchema(final JsonNode schema) {
        final JsonNode node = schema.path("id");
        LoadingConfigurationBuilder.BUNDLE.checkArgument(node.isTextual(), "loadingCfg.noIDInSchema");
        return this.preloadSchema(node.textValue(), schema);
    }
    
    public LoadingConfigurationBuilder addParserFeature(final JsonParser.Feature feature) {
        LoadingConfigurationBuilder.BUNDLE.checkNotNull(feature, "loadingCfg.nullJsonParserFeature");
        this.parserFeatures.add(feature);
        return this;
    }
    
    public LoadingConfigurationBuilder removeParserFeature(final JsonParser.Feature feature) {
        LoadingConfigurationBuilder.BUNDLE.checkNotNull(feature, "loadingCfg.nullJsonParserFeature");
        if (feature != JsonParser.Feature.AUTO_CLOSE_SOURCE) {
            this.parserFeatures.remove(feature);
        }
        return this;
    }
    
    @Override
    public LoadingConfiguration freeze() {
        return new LoadingConfiguration(this);
    }
    
    private static URI getLocator(final String input) {
        JsonRef ref;
        try {
            ref = JsonRef.fromString(input);
        }
        catch (JsonReferenceException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        LoadingConfigurationBuilder.BUNDLE.checkArgumentPrintf(ref.isAbsolute(), "jsonRef.notAbsolute", ref);
        return ref.getLocator();
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
        DEFAULT_PARSER_FEATURES = EnumSet.noneOf(JsonParser.Feature.class);
        for (final JsonParser.Feature feature : JsonParser.Feature.values()) {
            if (feature.enabledByDefault()) {
                LoadingConfigurationBuilder.DEFAULT_PARSER_FEATURES.add(feature);
            }
        }
    }
}
