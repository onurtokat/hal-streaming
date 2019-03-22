// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load.configuration;

import com.github.fge.Thawed;
import java.util.Iterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JacksonUtils;
import com.google.common.collect.ImmutableMap;
import com.github.fge.jackson.JsonNodeReader;
import com.fasterxml.jackson.core.JsonParser;
import java.util.EnumSet;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import com.github.fge.jsonschema.core.load.Dereferencing;
import com.github.fge.jsonschema.core.load.uri.URITranslatorConfiguration;
import com.github.fge.jsonschema.core.load.download.URIDownloader;
import java.util.Map;
import com.github.fge.Frozen;

public final class LoadingConfiguration implements Frozen<LoadingConfigurationBuilder>
{
    final Map<String, URIDownloader> downloaders;
    final URITranslatorConfiguration translatorCfg;
    final boolean enableCache;
    final Dereferencing dereferencing;
    final Map<URI, JsonNode> preloadedSchemas;
    final EnumSet<JsonParser.Feature> parserFeatures;
    private final JsonNodeReader reader;
    
    public static LoadingConfigurationBuilder newBuilder() {
        return new LoadingConfigurationBuilder();
    }
    
    public static LoadingConfiguration byDefault() {
        return new LoadingConfigurationBuilder().freeze();
    }
    
    LoadingConfiguration(final LoadingConfigurationBuilder builder) {
        this.downloaders = builder.downloaders.build();
        this.translatorCfg = builder.translatorCfg;
        this.dereferencing = builder.dereferencing;
        this.preloadedSchemas = (Map<URI, JsonNode>)ImmutableMap.copyOf((Map<?, ?>)builder.preloadedSchemas);
        this.parserFeatures = EnumSet.copyOf(builder.parserFeatures);
        this.reader = this.buildReader();
        this.enableCache = builder.enableCache;
    }
    
    private JsonNodeReader buildReader() {
        final ObjectMapper mapper = JacksonUtils.newMapper();
        for (final JsonParser.Feature feature : this.parserFeatures) {
            mapper.configure(feature, true);
        }
        return new JsonNodeReader(mapper);
    }
    
    public Map<String, URIDownloader> getDownloaderMap() {
        return this.downloaders;
    }
    
    public URITranslatorConfiguration getTranslatorConfiguration() {
        return this.translatorCfg;
    }
    
    public Dereferencing getDereferencing() {
        return this.dereferencing;
    }
    
    public Map<URI, JsonNode> getPreloadedSchemas() {
        return this.preloadedSchemas;
    }
    
    public JsonNodeReader getReader() {
        return this.reader;
    }
    
    public boolean getEnableCache() {
        return this.enableCache;
    }
    
    @Override
    public LoadingConfigurationBuilder thaw() {
        return new LoadingConfigurationBuilder(this);
    }
}
