// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.render;

import com.github.fge.uritemplate.URITemplateException;
import com.google.common.collect.ImmutableList;
import java.util.List;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.expression.ExpressionType;

public final class StringRenderer extends ValueRenderer
{
    public StringRenderer(final ExpressionType type) {
        super(type);
    }
    
    @Override
    public List<String> render(final VariableSpec varspec, final VariableValue value) throws URITemplateException {
        return ImmutableList.of(this.doRender(varspec, value.getScalarValue()));
    }
    
    private String doRender(final VariableSpec varspec, final String value) {
        final StringBuilder sb = new StringBuilder(value.length());
        if (this.named) {
            sb.append(varspec.getName());
            if (value.isEmpty()) {
                return sb.append(this.ifEmpty).toString();
            }
            sb.append('=');
        }
        final int prefixLen = varspec.getPrefixLength();
        if (prefixLen == -1) {
            return sb.append(this.pctEncode(value)).toString();
        }
        final int len = value.codePointCount(0, value.length());
        return (len <= prefixLen) ? sb.append(this.pctEncode(value)).toString() : sb.append(this.pctEncode(nFirstChars(value, prefixLen))).toString();
    }
    
    private static String nFirstChars(final String s, final int n) {
        int realIndex;
        for (realIndex = n; s.codePointCount(0, realIndex) != n; ++realIndex) {}
        return s.substring(0, realIndex);
    }
}
