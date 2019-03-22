// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.render;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.github.fge.uritemplate.expression.ExpressionType;

public final class ListRenderer extends MultiValueRenderer
{
    public ListRenderer(final ExpressionType type) {
        super(type);
    }
    
    @Override
    protected List<String> renderNamedExploded(final String varname, final VariableValue value) {
        final List<String> ret = (List<String>)Lists.newArrayList();
        for (final String element : value.getListValue()) {
            ret.add(element.isEmpty() ? (varname + this.ifEmpty) : (varname + '=' + this.pctEncode(element)));
        }
        return ret;
    }
    
    @Override
    protected List<String> renderUnnamedExploded(final VariableValue value) {
        final List<String> ret = (List<String>)Lists.newArrayList();
        for (final String element : value.getListValue()) {
            ret.add(this.pctEncode(element));
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
        for (final String element : value.getListValue()) {
            elements.add(this.pctEncode(element));
        }
        ListRenderer.COMMA.appendTo(sb, (Iterable<?>)elements);
        return ImmutableList.of(sb.toString());
    }
    
    @Override
    protected List<String> renderUnnamedNormal(final VariableValue value) {
        if (value.isEmpty()) {
            return (List<String>)ImmutableList.of();
        }
        final List<String> ret = (List<String>)Lists.newArrayList();
        for (final String element : value.getListValue()) {
            ret.add(this.pctEncode(element));
        }
        return ImmutableList.of(ListRenderer.COMMA.join(ret));
    }
}
