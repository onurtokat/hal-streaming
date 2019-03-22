// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple.bundle;

import com.github.fge.Frozen;
import java.util.Locale;
import com.github.fge.msgsimple.provider.StaticMessageSourceProvider;
import com.github.fge.msgsimple.source.MessageSource;
import java.util.Collection;
import java.util.ArrayList;
import com.github.fge.msgsimple.provider.MessageSourceProvider;
import java.util.List;
import com.github.fge.msgsimple.InternalBundle;
import com.github.fge.Thawed;

public final class MessageBundleBuilder implements Thawed<MessageBundle>
{
    private static final InternalBundle BUNDLE;
    final List<MessageSourceProvider> providers;
    
    MessageBundleBuilder() {
        this.providers = new ArrayList<MessageSourceProvider>();
    }
    
    MessageBundleBuilder(final MessageBundle bundle) {
        (this.providers = new ArrayList<MessageSourceProvider>()).addAll(bundle.providers);
    }
    
    public MessageBundleBuilder appendProvider(final MessageSourceProvider provider) {
        MessageBundleBuilder.BUNDLE.checkNotNull(provider, "cfg.nullProvider");
        this.providers.add(provider);
        return this;
    }
    
    public MessageBundleBuilder prependProvider(final MessageSourceProvider provider) {
        MessageBundleBuilder.BUNDLE.checkNotNull(provider, "cfg.nullProvider");
        this.providers.add(0, provider);
        return this;
    }
    
    public MessageBundleBuilder appendSource(final MessageSource source) {
        final MessageSourceProvider provider = StaticMessageSourceProvider.withSingleSource(source);
        this.providers.add(provider);
        return this;
    }
    
    public MessageBundleBuilder prependSource(final MessageSource source) {
        final MessageSourceProvider provider = StaticMessageSourceProvider.withSingleSource(source);
        this.providers.add(0, provider);
        return this;
    }
    
    public MessageBundleBuilder appendSource(final Locale locale, final MessageSource source) {
        final MessageSourceProvider provider = StaticMessageSourceProvider.withSingleSource(locale, source);
        this.providers.add(provider);
        return this;
    }
    
    public MessageBundleBuilder prependSource(final Locale locale, final MessageSource source) {
        final MessageSourceProvider provider = StaticMessageSourceProvider.withSingleSource(locale, source);
        this.providers.add(0, provider);
        return this;
    }
    
    public MessageBundleBuilder appendBundle(final MessageBundle bundle) {
        MessageBundleBuilder.BUNDLE.checkNotNull(bundle, "cfg.nullBundle");
        this.providers.addAll(bundle.providers);
        return this;
    }
    
    public MessageBundleBuilder prependBundle(final MessageBundle bundle) {
        MessageBundleBuilder.BUNDLE.checkNotNull(bundle, "cfg.nullBundle");
        final List<MessageSourceProvider> list = new ArrayList<MessageSourceProvider>();
        list.addAll(bundle.providers);
        list.addAll(this.providers);
        this.providers.clear();
        this.providers.addAll(list);
        return this;
    }
    
    @Override
    public MessageBundle freeze() {
        return new MessageBundle(this);
    }
    
    static {
        BUNDLE = InternalBundle.getInstance();
    }
}
