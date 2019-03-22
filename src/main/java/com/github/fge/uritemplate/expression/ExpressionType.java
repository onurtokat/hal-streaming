// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.expression;

public enum ExpressionType
{
    SIMPLE("", ',', false, "", false), 
    RESERVED("", ',', false, "", true), 
    NAME_LABELS(".", '.', false, "", false), 
    PATH_SEGMENTS("/", '/', false, "", false), 
    PATH_PARAMETERS(";", ';', true, "", false), 
    QUERY_STRING("?", '&', true, "=", false), 
    QUERY_CONT("&", '&', true, "=", false), 
    FRAGMENT("#", ',', false, "", true);
    
    private final String prefix;
    private final char separator;
    private final boolean named;
    private final String ifEmpty;
    private final boolean rawExpand;
    
    private ExpressionType(final String prefix, final char separator, final boolean named, final String ifEmpty, final boolean rawExpand) {
        this.prefix = prefix;
        this.separator = separator;
        this.named = named;
        this.ifEmpty = ifEmpty;
        this.rawExpand = rawExpand;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public boolean isNamed() {
        return this.named;
    }
    
    public String getIfEmpty() {
        return this.ifEmpty;
    }
    
    public boolean isRawExpand() {
        return this.rawExpand;
    }
    
    public char getSeparator() {
        return this.separator;
    }
}
