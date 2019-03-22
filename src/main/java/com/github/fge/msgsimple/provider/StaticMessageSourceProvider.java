// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple.provider;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import com.github.fge.msgsimple.source.MessageSource;
import com.github.fge.msgsimple.InternalBundle;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class StaticMessageSourceProvider implements MessageSourceProvider
{
    private static final InternalBundle BUNDLE;
    private final MessageSource defaultSource;
    private final Map<Locale, MessageSource> sources;
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static MessageSourceProvider withSingleSource(final MessageSource source) {
        return new Builder().setDefaultSource(source).build();
    }
    
    public static MessageSourceProvider withSingleSource(final Locale locale, final MessageSource source) {
        return new Builder().addSource(locale, source).build();
    }
    
    private StaticMessageSourceProvider(final Builder builder) {
        this.defaultSource = builder.defaultSource;
        this.sources = new HashMap<Locale, MessageSource>(builder.sources);
    }
    
    @Override
    public MessageSource getMessageSource(final Locale locale) {
        return this.sources.containsKey(locale) ? this.sources.get(locale) : this.defaultSource;
    }
    
    static {
        BUNDLE = InternalBundle.getInstance();
    }
    
    @NotThreadSafe
    public static final class Builder
    {
        private MessageSource defaultSource;
        private final Map<Locale, MessageSource> sources;
        
        private Builder() {
            this.sources = new HashMap<Locale, MessageSource>();
        }
        
        public Builder addSource(final Locale locale, final MessageSource source) {
            StaticMessageSourceProvider.BUNDLE.checkNotNull(locale, "cfg.nullKey");
            StaticMessageSourceProvider.BUNDLE.checkNotNull(source, "cfg.nullSource");
            this.sources.put(locale, source);
            return this;
        }
        
        public Builder setDefaultSource(final MessageSource source) {
            StaticMessageSourceProvider.BUNDLE.checkNotNull(source, "cfg.nullDefaultSource");
            this.defaultSource = source;
            return this;
        }
        
        public MessageSourceProvider build() {
            return new StaticMessageSourceProvider(this, null);
        }
    }
}
