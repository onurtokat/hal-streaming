// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.vars.values;

import java.util.Iterator;
import com.google.common.collect.Maps;
import javax.annotation.concurrent.NotThreadSafe;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class MapValue extends VariableValue
{
    private final Map<String, String> map;
    
    private MapValue(final Builder builder) {
        super(ValueType.MAP);
        this.map = (Map<String, String>)ImmutableMap.copyOf((Map<?, ?>)builder.map);
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static <T> VariableValue copyOf(final Map<String, T> map) {
        return newBuilder().putAll(map).build();
    }
    
    @Override
    public Map<String, String> getMapValue() {
        return this.map;
    }
    
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    @NotThreadSafe
    public static final class Builder
    {
        private final Map<String, String> map;
        
        private Builder() {
            this.map = (Map<String, String>)Maps.newLinkedHashMap();
        }
        
        public <T> Builder put(final String key, final T value) {
            this.map.put(VariableValue.BUNDLE.checkNotNull(key, "mapValue.nullKey"), VariableValue.BUNDLE.checkNotNull(value, "mapValue.nullValue").toString());
            return this;
        }
        
        public <T> Builder putAll(final Map<String, T> map) {
            VariableValue.BUNDLE.checkNotNull(map, "mapValue.nullMap");
            for (final Map.Entry<String, T> entry : map.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
            return this;
        }
        
        public VariableValue build() {
            return new MapValue(this, null);
        }
    }
}
