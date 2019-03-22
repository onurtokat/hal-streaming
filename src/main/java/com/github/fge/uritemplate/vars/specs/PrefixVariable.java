// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.vars.specs;

public final class PrefixVariable extends VariableSpec
{
    private final int length;
    
    public PrefixVariable(final String name, final int length) {
        super(VariableSpecType.PREFIX, name);
        this.length = length;
    }
    
    @Override
    public boolean isExploded() {
        return false;
    }
    
    @Override
    public int getPrefixLength() {
        return this.length;
    }
    
    @Override
    public int hashCode() {
        return 31 * this.name.hashCode() + this.length;
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
        final PrefixVariable other = (PrefixVariable)obj;
        return this.name.equals(other.name) && this.length == other.length;
    }
    
    @Override
    public String toString() {
        return this.name + " (prefix length: " + this.length + ')';
    }
}
