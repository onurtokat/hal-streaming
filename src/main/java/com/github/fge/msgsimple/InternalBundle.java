// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public final class InternalBundle
{
    private static final InternalBundle INSTANCE;
    private final Map<String, String> messages;
    
    private InternalBundle() {
        (this.messages = new HashMap<String, String>()).put("query.nullKey", "cannot query null keys");
        this.messages.put("query.nullLocale", "cannot query null locale");
        this.messages.put("cfg.nullProvider", "cannot append null message source provider");
        this.messages.put("cfg.nullResourcePath", "resource path cannot be null");
        this.messages.put("cfg.noLoader", "no loader has been provided");
        this.messages.put("cfg.nullLoader", "loader cannot be null");
        this.messages.put("cfg.nullDefaultSource", "when provided, the default message source must not be null");
        this.messages.put("cfg.nonPositiveDuration", "timeout must be greater than 0");
        this.messages.put("cfg.nullTimeUnit", "time unit must not be null");
        this.messages.put("cfg.nullKey", "null keys are not allowed");
        this.messages.put("cfg.nullSource", "null sources are not allowed");
        this.messages.put("cfg.nullMap", "null map is not allowed");
        this.messages.put("cfg.nullFile", "file cannot be null");
        this.messages.put("cfg.nullPath", "file path cannot be null");
        this.messages.put("cfg.nullInputStream", "provided InputStream is null");
        this.messages.put("cfg.map.nullKey", "null keys not allowed in map");
        this.messages.put("cfg.map.nullValue", "null values not allowed in map");
        this.messages.put("properties.resource.notFound", "resource \"%s\" not found");
        this.messages.put("cfg.nullCharset", "charset cannot be null");
        this.messages.put("cfg.nullBundle", "bundle cannot be null");
        this.messages.put("factory.noConstructor", "bundle provider does not have a no-arg constructor");
        this.messages.put("factory.cannotInstantiate", "cannot instantiate bundle provider");
        this.messages.put("factory.illegalProvider", "bundle provider returns null");
    }
    
    public static InternalBundle getInstance() {
        return InternalBundle.INSTANCE;
    }
    
    public String getMessage(final String key) {
        return this.messages.get(key);
    }
    
    public String printf(final String key, final Object... params) {
        return new Formatter().format(this.getMessage(key), params).toString();
    }
    
    public <T> T checkNotNull(final T reference, final String key) {
        if (reference == null) {
            throw new NullPointerException(this.messages.get(key));
        }
        return reference;
    }
    
    public void checkArgument(final boolean condition, final String key) {
        if (!condition) {
            throw new IllegalArgumentException(this.messages.get(key));
        }
    }
    
    static {
        INSTANCE = new InternalBundle();
    }
}
