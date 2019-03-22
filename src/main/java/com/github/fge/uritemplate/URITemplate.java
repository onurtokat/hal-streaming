// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.URI;
import java.util.Iterator;
import com.github.fge.uritemplate.vars.VariableMap;
import com.github.fge.uritemplate.parse.URITemplateParser;
import com.github.fge.uritemplate.expression.URITemplateExpression;
import java.util.List;

public final class URITemplate
{
    private final List<URITemplateExpression> expressions;
    
    public URITemplate(final String input) throws URITemplateParseException {
        this.expressions = URITemplateParser.parse(input);
    }
    
    public String toString(final VariableMap vars) throws URITemplateException {
        final StringBuilder sb = new StringBuilder();
        for (final URITemplateExpression expression : this.expressions) {
            sb.append(expression.expand(vars));
        }
        return sb.toString();
    }
    
    public URI toURI(final VariableMap vars) throws URITemplateException, URISyntaxException {
        return new URI(this.toString(vars));
    }
    
    public URL toURL(final VariableMap vars) throws URITemplateException, MalformedURLException {
        return new URL(this.toString(vars));
    }
}
