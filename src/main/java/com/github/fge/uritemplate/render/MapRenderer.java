// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.render;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.Map;
import com.google.common.collect.Lists;
import java.util.List;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.github.fge.uritemplate.expression.ExpressionType;

public final class MapRenderer extends MultiValueRenderer
{
    public MapRenderer(final ExpressionType type) {
        super(type);
    }
    
    @Override
    protected List<String> renderNamedExploded(final String varname, final VariableValue value) {
        final List<String> ret = (List<String>)Lists.newArrayList();
        final Map<String, String> map = value.getMapValue();
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            final StringBuilder element = new StringBuilder(this.pctEncode(entry.getKey()));
            final String val = entry.getValue();
            if (val.isEmpty()) {
                element.append(this.ifEmpty);
            }
            else {
                element.append('=').append(this.pctEncode(val));
            }
            ret.add(element.toString());
        }
        return ret;
    }
    
    @Override
    protected List<String> renderUnnamedExploded(final VariableValue value) {
        final List<String> ret = (List<String>)Lists.newArrayList();
        final Map<String, String> map = value.getMapValue();
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            ret.add(this.pctEncode(entry.getKey()) + '=' + this.pctEncode(entry.getValue()));
        }
        return ret;
    }
    
    @Override
    protected List<String> renderNamedNormal(final String varname, final VariableValue value) {
        final StringBuilder sb = new StringBuilder(varname);
        if (value.isEmpty()) {
            return ImmutableList.of(sb.append(this.ifEmpty).toString());
        }
        sb.append('=');
        final List<String> elements = (List<String>)Lists.newArrayList();
        for (final String element : mapAsList(value)) {
            elements.add(this.pctEncode(element));
        }
        MapRenderer.COMMA.appendTo(sb, (Iterable<?>)elements);
        return ImmutableList.of(sb.toString());
    }
    
    @Override
    protected List<String> renderUnnamedNormal(final VariableValue value) {
        if (value.isEmpty()) {
            return (List<String>)ImmutableList.of();
        }
        final List<String> ret = (List<String>)Lists.newArrayList();
        for (final String element : mapAsList(value)) {
            ret.add(this.pctEncode(element));
        }
        return ImmutableList.of(MapRenderer.COMMA.join(ret));
    }
    
    private static List<String> mapAsList(final VariableValue value) {
        final List<String> ret = (List<String>)Lists.newArrayList();
        final Map<String, String> map = value.getMapValue();
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            ret.add(entry.getKey());
            ret.add(entry.getValue());
        }
        return ret;
    }
}
