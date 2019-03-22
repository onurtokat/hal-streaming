// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson;

import java.io.StringReader;
import java.io.Reader;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.io.InputStream;
import com.google.common.io.Closer;
import java.io.IOException;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.fasterxml.jackson.databind.JsonNode;
import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public final class JsonLoader
{
    private static final Pattern INITIAL_SLASH;
    private static final JsonNodeReader READER;
    
    public static JsonNode fromResource(@Nonnull final String resource) throws IOException {
        Preconditions.checkNotNull(resource);
        Preconditions.checkArgument(resource.startsWith("/"), (Object)"resource path does not start with a '/'");
        URL url = JsonLoader.class.getResource(resource);
        if (url == null) {
            final ClassLoader classLoader = Objects.firstNonNull(Thread.currentThread().getContextClassLoader(), JsonLoader.class.getClassLoader());
            final String s = JsonLoader.INITIAL_SLASH.matcher(resource).replaceFirst("");
            url = classLoader.getResource(s);
        }
        if (url == null) {
            throw new IOException("resource " + resource + " not found");
        }
        final Closer closer = Closer.create();
        JsonNode ret;
        try {
            final InputStream in = closer.register(url.openStream());
            ret = JsonLoader.READER.fromInputStream(in);
        }
        finally {
            closer.close();
        }
        return ret;
    }
    
    public static JsonNode fromURL(final URL url) throws IOException {
        return JsonLoader.READER.fromInputStream(url.openStream());
    }
    
    public static JsonNode fromPath(final String path) throws IOException {
        final Closer closer = Closer.create();
        JsonNode ret;
        try {
            final FileInputStream in = closer.register(new FileInputStream(path));
            ret = JsonLoader.READER.fromInputStream(in);
        }
        finally {
            closer.close();
        }
        return ret;
    }
    
    public static JsonNode fromFile(final File file) throws IOException {
        final Closer closer = Closer.create();
        JsonNode ret;
        try {
            final FileInputStream in = closer.register(new FileInputStream(file));
            ret = JsonLoader.READER.fromInputStream(in);
        }
        finally {
            closer.close();
        }
        return ret;
    }
    
    public static JsonNode fromReader(final Reader reader) throws IOException {
        return JsonLoader.READER.fromReader(reader);
    }
    
    public static JsonNode fromString(final String json) throws IOException {
        return fromReader(new StringReader(json));
    }
    
    static {
        INITIAL_SLASH = Pattern.compile("^/+");
        READER = new JsonNodeReader();
    }
}
