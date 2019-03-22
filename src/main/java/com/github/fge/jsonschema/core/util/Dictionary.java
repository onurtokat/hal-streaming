// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util;

import com.github.fge.Thawed;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.concurrent.Immutable;
import com.github.fge.Frozen;

@Immutable
public final class Dictionary<T> implements Frozen<DictionaryBuilder<T>>
{
    final Map<String, T> entries;
    
    public static <T> DictionaryBuilder<T> newBuilder() {
        return new DictionaryBuilder<T>();
    }
    
    Dictionary(final DictionaryBuilder<T> builder) {
        this.entries = (Map<String, T>)ImmutableMap.copyOf((Map<?, ?>)builder.entries);
    }
    
    public Map<String, T> entries() {
        return this.entries;
    }
    
    @Override
    public DictionaryBuilder<T> thaw() {
        return new DictionaryBuilder<T>(this);
    }
}
