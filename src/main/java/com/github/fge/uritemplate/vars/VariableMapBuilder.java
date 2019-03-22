// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.vars;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.Frozen;
import com.github.fge.uritemplate.vars.values.MapValue;
import com.github.fge.uritemplate.vars.values.ListValue;
import com.github.fge.uritemplate.vars.values.ScalarValue;
import com.google.common.collect.Maps;
import com.github.fge.uritemplate.vars.values.VariableValue;
import java.util.Map;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.NotThreadSafe;
import com.github.fge.Thawed;

@NotThreadSafe
public final class VariableMapBuilder implements Thawed<VariableMap>
{
    private static final MessageBundle BUNDLE;
    final Map<String, VariableValue> vars;
    
    VariableMapBuilder() {
        this.vars = (Map<String, VariableValue>)Maps.newHashMap();
    }
    
    VariableMapBuilder(final VariableMap variableMap) {
        (this.vars = (Map<String, VariableValue>)Maps.newHashMap()).putAll(variableMap.vars);
    }
    
    public VariableMapBuilder addValue(final String varname, final VariableValue value) {
        this.vars.put(VariableMapBuilder.BUNDLE.checkNotNull(varname, "varmap.nullVarName"), VariableMapBuilder.BUNDLE.checkNotNull(value, "varmap.nullValue"));
        return this;
    }
    
    public VariableMapBuilder addScalarValue(final String varname, final Object value) {
        return this.addValue(varname, new ScalarValue(value));
    }
    
    public <T> VariableMapBuilder addListValue(final String varname, final Iterable<T> iterable) {
        return this.addValue(varname, ListValue.copyOf(iterable));
    }
    
    public VariableMapBuilder addListValue(final String varname, final Object first, final Object... other) {
        return this.addValue(varname, ListValue.of(first, other));
    }
    
    public <T> VariableMapBuilder addMapValue(final String varname, final Map<String, T> map) {
        return this.addValue(varname, MapValue.copyOf(map));
    }
    
    public VariableMapBuilder addVariableMap(final VariableMap other) {
        VariableMapBuilder.BUNDLE.checkNotNull(other, "varmap.nullInput");
        this.vars.putAll(other.vars);
        return this;
    }
    
    @Override
    public VariableMap freeze() {
        return new VariableMap(this);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(URITemplateMessageBundle.class);
    }
}
