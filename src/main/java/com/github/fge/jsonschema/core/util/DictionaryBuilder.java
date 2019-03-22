// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.Frozen;
import com.google.common.collect.Maps;
import java.util.Map;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.NotThreadSafe;
import com.github.fge.Thawed;

@NotThreadSafe
public final class DictionaryBuilder<T> implements Thawed<Dictionary<T>>
{
    private static final MessageBundle BUNDLE;
    final Map<String, T> entries;
    
    DictionaryBuilder() {
        this.entries = (Map<String, T>)Maps.newHashMap();
    }
    
    DictionaryBuilder(final Dictionary<T> dict) {
        (this.entries = (Map<String, T>)Maps.newHashMap()).putAll((Map<? extends String, ? extends T>)dict.entries);
    }
    
    public DictionaryBuilder<T> addEntry(final String key, final T value) {
        DictionaryBuilder.BUNDLE.checkNotNull(key, "dictionary.nullKey");
        DictionaryBuilder.BUNDLE.checkNotNull(value, "dictionary.nullValue");
        this.entries.put(key, value);
        return this;
    }
    
    public DictionaryBuilder<T> addAll(final Dictionary<T> other) {
        DictionaryBuilder.BUNDLE.checkNotNull(other, "dictionary.nullDict");
        this.entries.putAll((Map<? extends String, ? extends T>)other.entries);
        return this;
    }
    
    public DictionaryBuilder<T> removeEntry(final String key) {
        this.entries.remove(key);
        return this;
    }
    
    @Override
    public Dictionary<T> freeze() {
        return new Dictionary<T>(this);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
