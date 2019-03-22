// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.expression;

import com.github.fge.uritemplate.URITemplateException;
import com.github.fge.uritemplate.vars.VariableMap;

public interface URITemplateExpression
{
    String expand(final VariableMap p0) throws URITemplateException;
}
