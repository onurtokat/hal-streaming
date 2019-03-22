// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple.source;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import com.github.fge.msgsimple.InternalBundle;

public final class MapMessageSource implements MessageSource
{
    private static final InternalBundle BUNDLE;
    private final Map<String, String> messages;
    
    private MapMessageSource(final Builder builder) {
        this.messages = new HashMap<String, String>(builder.messages);
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    @Override
    public String getKey(final String key) {
        return this.messages.get(key);
    }
    
    static {
        BUNDLE = InternalBundle.getInstance();
    }
    
    public static final class Builder
    {
        private final Map<String, String> messages;
        
        private Builder() {
            this.messages = new HashMap<String, String>();
        }
        
        public Builder put(final String key, final String message) {
            MapMessageSource.BUNDLE.checkNotNull(key, "cfg.map.nullKey");
            MapMessageSource.BUNDLE.checkNotNull(message, "cfg.map.nullValue");
            this.messages.put(key, message);
            return this;
        }
        
        public Builder putAll(final Map<String, String> map) {
            MapMessageSource.BUNDLE.checkNotNull(map, "cfg.nullMap");
            for (final Map.Entry<String, String> entry : map.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
            return this;
        }
        
        public MessageSource build() {
            return new MapMessageSource(this, null);
        }
    }
}
