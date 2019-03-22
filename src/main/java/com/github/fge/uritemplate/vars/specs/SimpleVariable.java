// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.vars.specs;

public final class SimpleVariable extends VariableSpec
{
    public SimpleVariable(final String name) {
        super(VariableSpecType.SIMPLE, name);
    }
    
    @Override
    public boolean isExploded() {
        return false;
    }
    
    @Override
    public int getPrefixLength() {
        return -1;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
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
        final SimpleVariable other = (SimpleVariable)obj;
        return this.name.equals(other.name);
    }
    
    @Override
    public String toString() {
        return this.name + " (simple)";
    }
}
