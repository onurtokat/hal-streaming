// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.google.common.collect.ImmutableMap;
import java.util.Iterator;
import com.google.common.collect.Maps;
import com.google.common.base.Function;
import java.util.Map;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.google.common.annotations.Beta;

@Beta
public abstract class Registry<K, V>
{
    protected static final MessageBundle BUNDLE;
    private final Map<K, V> map;
    private final Function<K, K> keyNormalizer;
    private final ArgumentChecker<K> keyChecker;
    private final Function<V, V> valueNormalizer;
    private final ArgumentChecker<V> valueChecker;
    
    protected Registry(final Function<K, K> keyNormalizer, final ArgumentChecker<K> keyChecker, final Function<V, V> valueNormalizer, final ArgumentChecker<V> valueChecker) {
        this.map = (Map<K, V>)Maps.newHashMap();
        this.keyNormalizer = Registry.BUNDLE.checkNotNull(keyNormalizer, "mapBuilder.nullNormalizer");
        this.keyChecker = Registry.BUNDLE.checkNotNull(keyChecker, "mapBuilder.nullChecker");
        this.valueNormalizer = Registry.BUNDLE.checkNotNull(valueNormalizer, "mapBuilder.nullNormalizer");
        this.valueChecker = Registry.BUNDLE.checkNotNull(valueChecker, "mapBuilder.nullChecker");
    }
    
    public final Registry<K, V> put(final K key, final V value) {
        Registry.BUNDLE.checkNotNull(key, "mapBuilder.nullKey");
        Registry.BUNDLE.checkNotNull(value, "mapBuilder.nullValue");
        final K normalizedKey = this.keyNormalizer.apply(key);
        this.keyChecker.check(key);
        final V normalizedValue = this.valueNormalizer.apply(value);
        this.valueChecker.check(value);
        this.checkEntry(normalizedKey, normalizedValue);
        this.map.put(normalizedKey, normalizedValue);
        return this;
    }
    
    public final Registry<K, V> putAll(final Map<K, V> otherMap) {
        Registry.BUNDLE.checkNotNull(otherMap, "mapBuilder.nullMap");
        for (final Map.Entry<K, V> entry : otherMap.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
        return this;
    }
    
    public final Registry<K, V> remove(final K key) {
        this.map.remove(key);
        return this;
    }
    
    public final Registry<K, V> clear() {
        this.map.clear();
        return this;
    }
    
    public final Map<K, V> build() {
        return (Map<K, V>)ImmutableMap.copyOf((Map<?, ?>)this.map);
    }
    
    protected abstract void checkEntry(final K p0, final V p1);
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
