// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load.configuration;

import com.github.fge.jsonschema.core.load.download.ResourceURIDownloader;
import com.github.fge.jsonschema.core.load.download.DefaultURIDownloader;
import com.google.common.collect.ImmutableMap;
import com.github.fge.jsonschema.core.util.ArgumentChecker;
import com.google.common.base.Functions;
import com.github.fge.jsonschema.core.util.URIUtils;
import java.util.Map;
import com.github.fge.jsonschema.core.load.download.URIDownloader;
import com.github.fge.jsonschema.core.util.Registry;

final class URIDownloadersRegistry extends Registry<String, URIDownloader>
{
    private static final Map<String, URIDownloader> DEFAULT_DOWNLOADERS;
    
    public URIDownloadersRegistry() {
        super(URIUtils.schemeNormalizer(), URIUtils.schemeChecker(), Functions.identity(), ArgumentChecker.anythingGoes());
        this.putAll(URIDownloadersRegistry.DEFAULT_DOWNLOADERS);
    }
    
    @Override
    protected void checkEntry(final String key, final URIDownloader value) {
    }
    
    static {
        final ImmutableMap.Builder<String, URIDownloader> builder = ImmutableMap.builder();
        String scheme = "http";
        URIDownloader downloader = DefaultURIDownloader.getInstance();
        builder.put(scheme, downloader);
        scheme = "https";
        downloader = DefaultURIDownloader.getInstance();
        builder.put(scheme, downloader);
        scheme = "file";
        downloader = DefaultURIDownloader.getInstance();
        builder.put(scheme, downloader);
        scheme = "ftp";
        downloader = DefaultURIDownloader.getInstance();
        builder.put(scheme, downloader);
        scheme = "jar";
        downloader = DefaultURIDownloader.getInstance();
        builder.put(scheme, downloader);
        scheme = "resource";
        downloader = ResourceURIDownloader.getInstance();
        builder.put(scheme, downloader);
        DEFAULT_DOWNLOADERS = builder.build();
    }
}
