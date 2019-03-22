// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.render.ValueRenderer;
import com.github.fge.uritemplate.vars.values.VariableValue;
import java.util.Iterator;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.github.fge.uritemplate.vars.VariableMap;
import java.util.Collection;
import com.google.common.collect.ImmutableList;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import java.util.List;

public final class TemplateExpression implements URITemplateExpression
{
    private final ExpressionType expressionType;
    private final List<VariableSpec> variableSpecs;
    
    public TemplateExpression(final ExpressionType expressionType, final List<VariableSpec> variableSpecs) {
        this.expressionType = expressionType;
        this.variableSpecs = (List<VariableSpec>)ImmutableList.copyOf((Collection<?>)variableSpecs);
    }
    
    @Override
    public String expand(final VariableMap vars) throws URITemplateException {
        final List<String> expansions = (List<String>)Lists.newArrayList();
        for (final VariableSpec varspec : this.variableSpecs) {
            final VariableValue value = vars.get(varspec.getName());
            if (value == null) {
                continue;
            }
            final ValueRenderer renderer = value.getType().selectRenderer(this.expressionType);
            expansions.addAll(renderer.render(varspec, value));
        }
        if (expansions.isEmpty()) {
            return "";
        }
        final Joiner joiner = Joiner.on(this.expressionType.getSeparator());
        final StringBuilder sb = new StringBuilder(this.expressionType.getPrefix());
        joiner.appendTo(sb, (Iterable<?>)expansions);
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return 31 * this.expressionType.hashCode() + this.variableSpecs.hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final TemplateExpression other = (TemplateExpression)obj;
        return this.expressionType == other.expressionType && this.variableSpecs.equals(other.variableSpecs);
    }
}
