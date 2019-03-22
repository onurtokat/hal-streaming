// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.vars;

import com.github.fge.Thawed;
import com.google.common.collect.ImmutableMap;
import com.github.fge.uritemplate.vars.values.VariableValue;
import java.util.Map;
import javax.annotation.concurrent.Immutable;
import com.github.fge.Frozen;

@Immutable
public final class VariableMap implements Frozen<VariableMapBuilder>
{
    final Map<String, VariableValue> vars;
    
    VariableMap(final VariableMapBuilder builder) {
        this.vars = (Map<String, VariableValue>)ImmutableMap.copyOf((Map<?, ?>)builder.vars);
    }
    
    public static VariableMapBuilder newBuilder() {
        return new VariableMapBuilder();
    }
    
    public VariableValue get(final String varname) {
        return this.vars.get(varname);
    }
    
    @Override
    public VariableMapBuilder thaw() {
        return new VariableMapBuilder(this);
    }
}
