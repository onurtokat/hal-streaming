// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple.provider;

import java.util.concurrent.ScheduledExecutorService;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.TimeUnit;
import com.github.fge.msgsimple.source.MessageSource;
import java.util.concurrent.ExecutorService;
import com.github.fge.msgsimple.InternalBundle;
import java.util.concurrent.ThreadFactory;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class LoadingMessageSourceProvider implements MessageSourceProvider
{
    private static final ThreadFactory THREAD_FACTORY;
    private static final InternalBundle BUNDLE;
    private static final int NTHREADS = 3;
    private final ExecutorService service;
    private final MessageSourceLoader loader;
    private final MessageSource defaultSource;
    private final long timeoutDuration;
    private final TimeUnit timeoutUnit;
    private final AtomicBoolean expiryEnabled;
    private final long expiryDuration;
    private final TimeUnit expiryUnit;
    private final Map<Locale, FutureTask<MessageSource>> sources;
    
    private LoadingMessageSourceProvider(final Builder builder) {
        this.service = Executors.newFixedThreadPool(3, LoadingMessageSourceProvider.THREAD_FACTORY);
        this.sources = new HashMap<Locale, FutureTask<MessageSource>>();
        this.loader = builder.loader;
        this.defaultSource = builder.defaultSource;
        this.timeoutDuration = builder.timeoutDuration;
        this.timeoutUnit = builder.timeoutUnit;
        this.expiryDuration = builder.expiryDuration;
        this.expiryUnit = builder.expiryUnit;
        this.expiryEnabled = new AtomicBoolean(this.expiryDuration == 0L);
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    @Override
    public MessageSource getMessageSource(final Locale locale) {
        if (!this.expiryEnabled.getAndSet(true)) {
            this.setupExpiry(this.expiryDuration, this.expiryUnit);
        }
        FutureTask<MessageSource> task;
        synchronized (this.sources) {
            task = this.sources.get(locale);
            if (task == null) {
                task = this.loadingTask(locale);
                this.sources.put(locale, task);
                this.service.execute(task);
            }
        }
        try {
            final MessageSource source = task.get(this.timeoutDuration, this.timeoutUnit);
            return (source == null) ? this.defaultSource : source;
        }
        catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
            return this.defaultSource;
        }
        catch (ExecutionException ignored2) {
            return this.defaultSource;
        }
        catch (TimeoutException ignored3) {
            return this.defaultSource;
        }
        catch (CancellationException ignored4) {
            return this.defaultSource;
        }
    }
    
    private FutureTask<MessageSource> loadingTask(final Locale locale) {
        return new FutureTask<MessageSource>(new Callable<MessageSource>() {
            @Override
            public MessageSource call() throws IOException {
                return LoadingMessageSourceProvider.this.loader.load(locale);
            }
        });
    }
    
    private void setupExpiry(final long duration, final TimeUnit unit) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<FutureTask<MessageSource>> tasks;
                synchronized (LoadingMessageSourceProvider.this.sources) {
                    tasks = new ArrayList<FutureTask<MessageSource>>(LoadingMessageSourceProvider.this.sources.values());
                    LoadingMessageSourceProvider.this.sources.clear();
                }
                for (final FutureTask<MessageSource> task : tasks) {
                    task.cancel(true);
                }
            }
        };
        final ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1, LoadingMessageSourceProvider.THREAD_FACTORY);
        scheduled.scheduleAtFixedRate(runnable, duration, duration, unit);
    }
    
    static {
        THREAD_FACTORY = new ThreadFactory() {
            private final ThreadFactory factory = Executors.defaultThreadFactory();
            
            @Override
            public Thread newThread(final Runnable r) {
                final Thread ret = this.factory.newThread(r);
                ret.setDaemon(true);
                return ret;
            }
        };
        BUNDLE = InternalBundle.getInstance();
    }
    
    public static final class Builder
    {
        private MessageSourceLoader loader;
        private MessageSource defaultSource;
        private long timeoutDuration;
        private TimeUnit timeoutUnit;
        private long expiryDuration;
        private TimeUnit expiryUnit;
        
        private Builder() {
            this.timeoutDuration = 1L;
            this.timeoutUnit = TimeUnit.SECONDS;
            this.expiryDuration = 10L;
            this.expiryUnit = TimeUnit.MINUTES;
        }
        
        public Builder setLoader(final MessageSourceLoader loader) {
            LoadingMessageSourceProvider.BUNDLE.checkNotNull(loader, "cfg.nullLoader");
            this.loader = loader;
            return this;
        }
        
        public Builder setDefaultSource(final MessageSource defaultSource) {
            LoadingMessageSourceProvider.BUNDLE.checkNotNull(defaultSource, "cfg.nullDefaultSource");
            this.defaultSource = defaultSource;
            return this;
        }
        
        public Builder setLoadTimeout(final long duration, final TimeUnit unit) {
            LoadingMessageSourceProvider.BUNDLE.checkArgument(duration > 0L, "cfg.nonPositiveDuration");
            LoadingMessageSourceProvider.BUNDLE.checkNotNull(unit, "cfg.nullTimeUnit");
            this.timeoutDuration = duration;
            this.timeoutUnit = unit;
            return this;
        }
        
        public Builder setExpiryTime(final long duration, final TimeUnit unit) {
            LoadingMessageSourceProvider.BUNDLE.checkArgument(duration > 0L, "cfg.nonPositiveDuration");
            LoadingMessageSourceProvider.BUNDLE.checkNotNull(unit, "cfg.nullTimeUnit");
            this.expiryDuration = duration;
            this.expiryUnit = unit;
            return this;
        }
        
        public Builder neverExpires() {
            this.expiryDuration = 0L;
            return this;
        }
        
        public MessageSourceProvider build() {
            LoadingMessageSourceProvider.BUNDLE.checkArgument(this.loader != null, "cfg.noLoader");
            return new LoadingMessageSourceProvider(this, null);
        }
    }
}
