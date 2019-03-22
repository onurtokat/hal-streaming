// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.render;

import com.github.fge.uritemplate.URITemplateException;
import java.util.List;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.google.common.net.PercentEscaper;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.google.common.escape.Escaper;

public abstract class ValueRenderer
{
    protected final boolean named;
    protected final String ifEmpty;
    private final Escaper escaper;
    
    protected ValueRenderer(final ExpressionType type) {
        this.named = type.isNamed();
        this.ifEmpty = type.getIfEmpty();
        final String escaped = type.isRawExpand() ? "-._~:/?#[]@!$&'()*+,;=" : "-._~";
        this.escaper = new PercentEscaper(escaped, false);
    }
    
    public abstract List<String> render(final VariableSpec p0, final VariableValue p1) throws URITemplateException;
    
    protected final String pctEncode(final String s) {
        return this.escaper.escape(s);
    }
}
