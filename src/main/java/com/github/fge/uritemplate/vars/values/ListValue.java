// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.vars.values;

import java.util.Iterator;
import com.google.common.collect.Lists;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class ListValue extends VariableValue
{
    private final List<String> list;
    
    private ListValue(final Builder builder) {
        super(ValueType.ARRAY);
        this.list = (List<String>)ImmutableList.copyOf((Collection<?>)builder.list);
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static <T> VariableValue copyOf(final Iterable<T> iterable) {
        return new Builder().addAll(iterable).build();
    }
    
    public static VariableValue of(final Object first, final Object... other) {
        return new Builder().add(first, other).build();
    }
    
    @Override
    public List<String> getListValue() {
        return this.list;
    }
    
    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    @NotThreadSafe
    public static final class Builder
    {
        private final List<String> list;
        
        private Builder() {
            this.list = (List<String>)Lists.newArrayList();
        }
        
        public Builder add(final Object first, final Object... other) {
            VariableValue.BUNDLE.checkNotNull(first, "listValue.nullElement");
            this.list.add(first.toString());
            for (final Object o : other) {
                VariableValue.BUNDLE.checkNotNull(o, "listValue.nullElement");
                this.list.add(o.toString());
            }
            return this;
        }
        
        public <T> Builder addAll(final Iterable<T> iterable) {
            VariableValue.BUNDLE.checkNotNull(iterable, "listValue.nullIterable");
            for (final T element : iterable) {
                VariableValue.BUNDLE.checkNotNull(element, "listValue.nullElement");
                this.list.add(element.toString());
            }
            return this;
        }
        
        public VariableValue build() {
            return new ListValue(this, null);
        }
    }
}
