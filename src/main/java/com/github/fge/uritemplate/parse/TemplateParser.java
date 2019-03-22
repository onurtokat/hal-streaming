// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.parse;

import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import java.nio.CharBuffer;

interface TemplateParser
{
    URITemplateExpression parse(final CharBuffer p0) throws URITemplateParseException;
}
