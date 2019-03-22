// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple.source;

import java.util.Iterator;
import java.util.HashMap;
import java.io.Reader;
import java.util.Properties;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.nio.charset.Charset;
import com.github.fge.msgsimple.InternalBundle;

public final class PropertiesMessageSource implements MessageSource
{
    private static final InternalBundle BUNDLE;
    private static final Charset UTF8;
    private final Map<String, String> messages;
    
    public static MessageSource fromResource(final String resourcePath) throws IOException {
        return fromResource(resourcePath, PropertiesMessageSource.UTF8);
    }
    
    public static MessageSource fromResource(final String resourcePath, final Charset charset) throws IOException {
        PropertiesMessageSource.BUNDLE.checkNotNull(resourcePath, "cfg.nullResourcePath");
        final URL url = PropertiesMessageSource.class.getResource(resourcePath);
        if (url == null) {
            throw new IOException(PropertiesMessageSource.BUNDLE.printf("properties.resource.notFound", resourcePath));
        }
        final InputStream in = url.openStream();
        try {
            return fromInputStream(in, charset);
        }
        finally {
            closeQuietly(in);
        }
    }
    
    public static MessageSource fromFile(final File file) throws IOException {
        return fromFile(file, PropertiesMessageSource.UTF8);
    }
    
    public static MessageSource fromPath(final String path) throws IOException {
        PropertiesMessageSource.BUNDLE.checkNotNull(path, "cfg.nullPath");
        return fromFile(new File(path), PropertiesMessageSource.UTF8);
    }
    
    public static MessageSource fromFile(final File file, final Charset charset) throws IOException {
        PropertiesMessageSource.BUNDLE.checkNotNull(file, "cfg.nullFile");
        final FileInputStream in = new FileInputStream(file);
        try {
            return fromInputStream(in, charset);
        }
        finally {
            closeQuietly(in);
        }
    }
    
    public static MessageSource fromPath(final String path, final Charset charset) throws IOException {
        PropertiesMessageSource.BUNDLE.checkNotNull(path, "cfg.nullPath");
        return fromFile(new File(path), charset);
    }
    
    private static MessageSource fromInputStream(final InputStream in, final Charset charset) throws IOException {
        PropertiesMessageSource.BUNDLE.checkNotNull(in, "cfg.nullInputStream");
        final Reader reader = new InputStreamReader(in, charset);
        try {
            final Properties properties = new Properties();
            properties.load(reader);
            return new PropertiesMessageSource(properties);
        }
        finally {
            closeQuietly(reader);
        }
    }
    
    private PropertiesMessageSource(final Properties properties) {
        this.messages = new HashMap<String, String>();
        for (final String key : properties.stringPropertyNames()) {
            this.messages.put(key, properties.getProperty(key));
        }
    }
    
    @Override
    public String getKey(final String key) {
        return this.messages.get(key);
    }
    
    private static void closeQuietly(final Closeable closeable) {
        try {
            closeable.close();
        }
        catch (IOException ex) {}
    }
    
    static {
        BUNDLE = InternalBundle.getInstance();
        UTF8 = Charset.forName("UTF-8");
    }
}
