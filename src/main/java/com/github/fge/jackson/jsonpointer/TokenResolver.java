// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson.jsonpointer;

import javax.annotation.concurrent.ThreadSafe;
import com.fasterxml.jackson.core.TreeNode;

@ThreadSafe
public abstract class TokenResolver<T extends TreeNode>
{
    protected final ReferenceToken token;
    
    protected TokenResolver(final ReferenceToken token) {
        this.token = token;
    }
    
    public abstract T get(final T p0);
    
    public final ReferenceToken getToken() {
        return this.token;
    }
    
    @Override
    public final int hashCode() {
        return this.token.hashCode();
    }
    
    @Override
    public final boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final TokenResolver<?> other = (TokenResolver<?>)obj;
        return this.token.equals(other.token);
    }
    
    @Override
    public final String toString() {
        return this.token.toString();
    }
}
