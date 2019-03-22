// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.vars.values;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class ScalarValue extends VariableValue
{
    private final String value;
    
    public ScalarValue(final Object value) {
        super(ValueType.SCALAR);
        ScalarValue.BUNDLE.checkNotNull(value, "scalar.nullValue");
        this.value = value.toString();
    }
    
    @Override
    public String getScalarValue() {
        return this.value;
    }
    
    @Override
    public boolean isEmpty() {
        return this.value.isEmpty();
    }
}
