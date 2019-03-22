// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple.bundle;

import com.github.fge.Thawed;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.IllegalFormatException;
import java.util.Iterator;
import com.github.fge.msgsimple.locale.LocaleUtils;
import java.util.Locale;
import java.util.Collection;
import java.util.ArrayList;
import com.github.fge.msgsimple.provider.StaticMessageSourceProvider;
import com.github.fge.msgsimple.source.MessageSource;
import com.github.fge.msgsimple.provider.MessageSourceProvider;
import java.util.List;
import com.github.fge.msgsimple.InternalBundle;
import javax.annotation.concurrent.ThreadSafe;
import com.github.fge.Frozen;

@ThreadSafe
public final class MessageBundle implements Frozen<MessageBundleBuilder>
{
    private static final InternalBundle BUNDLE;
    final List<MessageSourceProvider> providers;
    
    public static MessageBundleBuilder newBuilder() {
        return new MessageBundleBuilder();
    }
    
    public static MessageBundle withSingleSource(final MessageSource source) {
        final MessageSourceProvider provider = StaticMessageSourceProvider.withSingleSource(source);
        return newBuilder().appendProvider(provider).freeze();
    }
    
    MessageBundle(final MessageBundleBuilder builder) {
        (this.providers = new ArrayList<MessageSourceProvider>()).addAll(builder.providers);
    }
    
    public String getMessage(final Locale locale, final String key) {
        MessageBundle.BUNDLE.checkNotNull(key, "query.nullKey");
        MessageBundle.BUNDLE.checkNotNull(locale, "query.nullLocale");
        for (final Locale l : LocaleUtils.getApplicable(locale)) {
            for (final MessageSourceProvider provider : this.providers) {
                final MessageSource source = provider.getMessageSource(l);
                if (source == null) {
                    continue;
                }
                final String ret = source.getKey(key);
                if (ret != null) {
                    return ret;
                }
            }
        }
        return key;
    }
    
    public String getMessage(final String key) {
        return this.getMessage(Locale.getDefault(), key);
    }
    
    public String printf(final Locale locale, final String key, final Object... params) {
        final String format = this.getMessage(locale, key);
        try {
            return String.format(locale, format, params);
        }
        catch (IllegalFormatException ignored) {
            return format;
        }
    }
    
    public String printf(final String key, final Object... params) {
        return this.printf(Locale.getDefault(), key, params);
    }
    
    public String format(final Locale locale, final String key, final Object... params) {
        final String pattern = this.getMessage(locale, key);
        try {
            return new MessageFormat(pattern, locale).format(params, new StringBuffer(pattern.length()), null).toString();
        }
        catch (IllegalArgumentException ignored) {
            return pattern;
        }
    }
    
    public String format(final String key, final Object... params) {
        return this.format(Locale.getDefault(), key, params);
    }
    
    public <T> T checkNotNull(final T reference, final Locale locale, final String key) {
        if (reference == null) {
            throw new NullPointerException(this.getMessage(locale, key));
        }
        return reference;
    }
    
    public <T> T checkNotNull(final T reference, final String key) {
        return this.checkNotNull(reference, Locale.getDefault(), key);
    }
    
    public void checkArgument(final boolean condition, final Locale locale, final String key) {
        if (!condition) {
            throw new IllegalArgumentException(this.getMessage(locale, key));
        }
    }
    
    public void checkArgument(final boolean condition, final String key) {
        this.checkArgument(condition, Locale.getDefault(), key);
    }
    
    public <T> T checkNotNullPrintf(final T reference, final Locale locale, final String key, final Object... params) {
        if (reference == null) {
            throw new NullPointerException(this.printf(locale, key, params));
        }
        return reference;
    }
    
    public <T> T checkNotNullPrintf(final T reference, final String key, final Object... params) {
        return this.checkNotNullPrintf(reference, Locale.getDefault(), key, params);
    }
    
    public void checkArgumentPrintf(final boolean condition, final Locale locale, final String key, final Object... params) {
        if (!condition) {
            throw new IllegalArgumentException(this.printf(locale, key, params));
        }
    }
    
    public void checkArgumentPrintf(final boolean condition, final String key, final Object... params) {
        this.checkArgumentPrintf(condition, Locale.getDefault(), key, params);
    }
    
    public <T> T checkNotNullFormat(final T reference, final Locale locale, final String key, final Object... params) {
        if (reference == null) {
            throw new NullPointerException(this.format(locale, key, params));
        }
        return reference;
    }
    
    public <T> T checkNotNullFormat(final T reference, final String key, final Object... params) {
        return this.checkNotNullFormat(reference, Locale.getDefault(), key, params);
    }
    
    public void checkArgumentFormat(final boolean condition, final Locale locale, final String key, final Object... params) {
        if (!condition) {
            throw new IllegalArgumentException(this.format(locale, key, params));
        }
    }
    
    public void checkArgumentFormat(final boolean condition, final String key, final Object... params) {
        this.checkArgumentFormat(condition, Locale.getDefault(), key, params);
    }
    
    @Override
    public MessageBundleBuilder thaw() {
        return new MessageBundleBuilder(this);
    }
    
    static {
        BUNDLE = InternalBundle.getInstance();
    }
}
