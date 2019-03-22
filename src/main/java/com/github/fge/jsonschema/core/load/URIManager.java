// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.InputStream;
import com.google.common.io.Closer;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.load.download.URIDownloader;
import java.util.Map;
import com.github.fge.msgsimple.bundle.MessageBundle;

public final class URIManager
{
    private static final MessageBundle BUNDLE;
    private final Map<String, URIDownloader> downloaders;
    private final JsonNodeReader reader;
    
    public URIManager() {
        this(LoadingConfiguration.byDefault());
    }
    
    public URIManager(final LoadingConfiguration cfg) {
        this.downloaders = cfg.getDownloaderMap();
        this.reader = cfg.getReader();
    }
    
    public JsonNode getContent(final URI uri) throws ProcessingException {
        URIManager.BUNDLE.checkNotNull(uri, "jsonRef.nullURI");
        if (!uri.isAbsolute()) {
            throw new ProcessingException(new ProcessingMessage().setMessage(URIManager.BUNDLE.getMessage("refProcessing.uriNotAbsolute")).put("uri", uri));
        }
        final String scheme = uri.getScheme();
        final URIDownloader downloader = this.downloaders.get(scheme);
        if (downloader == null) {
            throw new ProcessingException(new ProcessingMessage().setMessage(URIManager.BUNDLE.getMessage("refProcessing.unhandledScheme")).putArgument("scheme", scheme).putArgument("uri", uri));
        }
        final Closer closer = Closer.create();
        try {
            final InputStream in = closer.register(downloader.fetch(uri));
            return this.reader.fromInputStream(in);
        }
        catch (JsonMappingException e) {
            throw new ProcessingException(new ProcessingMessage().setMessage(e.getOriginalMessage()).put("uri", uri));
        }
        catch (JsonParseException e2) {
            throw new ProcessingException(new ProcessingMessage().setMessage(URIManager.BUNDLE.getMessage("uriManager.uriNotJson")).putArgument("uri", uri).put("parsingMessage", e2.getOriginalMessage()));
        }
        catch (IOException e3) {
            throw new ProcessingException(new ProcessingMessage().setMessage(URIManager.BUNDLE.getMessage("uriManager.uriIOError")).putArgument("uri", uri).put("exceptionMessage", e3.getMessage()));
        }
        finally {
            try {
                closer.close();
            }
            catch (IOException ignored) {
                throw new IllegalStateException();
            }
        }
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
