// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.vars.specs;

public abstract class VariableSpec
{
    private final VariableSpecType type;
    protected final String name;
    
    protected VariableSpec(final VariableSpecType type, final String name) {
        this.type = type;
        this.name = name;
    }
    
    public final VariableSpecType getType() {
        return this.type;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public abstract boolean isExploded();
    
    public abstract int getPrefixLength();
    
    @Override
    public abstract int hashCode();
    
    @Override
    public abstract boolean equals(final Object p0);
    
    @Override
    public abstract String toString();
}
