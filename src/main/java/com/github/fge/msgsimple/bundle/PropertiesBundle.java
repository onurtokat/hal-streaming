// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple.bundle;

import com.github.fge.msgsimple.provider.MessageSourceProvider;
import com.github.fge.msgsimple.provider.LoadingMessageSourceProvider;
import java.io.IOException;
import com.github.fge.msgsimple.source.PropertiesMessageSource;
import com.github.fge.msgsimple.source.MessageSource;
import java.util.Locale;
import com.github.fge.msgsimple.provider.MessageSourceLoader;
import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.nio.charset.Charset;
import com.github.fge.msgsimple.InternalBundle;

public final class PropertiesBundle
{
    private static final InternalBundle BUNDLE;
    private static final Charset UTF8;
    private static final Charset ISO;
    private static final Pattern SUFFIX;
    
    public static MessageBundle forPath(final String resourcePath) {
        return createBundle(resourcePath, PropertiesBundle.UTF8, 0L, null);
    }
    
    public static MessageBundle forPath(final String resourcePath, final long duration, final TimeUnit timeUnit) {
        return createBundle(resourcePath, PropertiesBundle.UTF8, duration, timeUnit);
    }
    
    public static MessageBundle forPath(final String resourcePath, final Charset charset, final long duration, final TimeUnit unit) {
        return createBundle(resourcePath, charset, duration, unit);
    }
    
    public static MessageBundle legacyResourceBundle(final String resourcePath) {
        return createBundle(resourcePath, PropertiesBundle.ISO, 0L, null);
    }
    
    private static MessageBundle createBundle(final String resourcePath, final Charset charset, final long duration, @Nullable final TimeUnit unit) {
        PropertiesBundle.BUNDLE.checkNotNull(resourcePath, "cfg.nullResourcePath");
        PropertiesBundle.BUNDLE.checkNotNull(charset, "cfg.nullCharset");
        final String s = resourcePath.startsWith("/") ? resourcePath : ('/' + resourcePath);
        final String realPath = PropertiesBundle.SUFFIX.matcher(s).replaceFirst("");
        final MessageSourceLoader loader = new MessageSourceLoader() {
            @Override
            public MessageSource load(final Locale locale) throws IOException {
                final StringBuilder sb = new StringBuilder(realPath);
                if (!locale.equals(Locale.ROOT)) {
                    sb.append('_').append(locale.toString());
                }
                sb.append(".properties");
                return PropertiesMessageSource.fromResource(sb.toString(), charset);
            }
        };
        final LoadingMessageSourceProvider.Builder builder = LoadingMessageSourceProvider.newBuilder().setLoader(loader);
        if (duration == 0L) {
            builder.neverExpires();
        }
        else {
            builder.setLoadTimeout(duration, unit);
        }
        final MessageSourceProvider provider = builder.build();
        return MessageBundle.newBuilder().appendProvider(provider).freeze();
    }
    
    static {
        BUNDLE = InternalBundle.getInstance();
        UTF8 = Charset.forName("UTF-8");
        ISO = Charset.forName("ISO-8859-1");
        SUFFIX = Pattern.compile("\\.properties$");
    }
}
