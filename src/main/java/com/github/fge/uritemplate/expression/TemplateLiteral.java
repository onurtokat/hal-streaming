// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.vars.VariableMap;

public final class TemplateLiteral implements URITemplateExpression
{
    private final String literal;
    
    public TemplateLiteral(final String literal) {
        this.literal = literal;
    }
    
    @Override
    public String expand(final VariableMap vars) throws URITemplateException {
        return this.literal;
    }
}
