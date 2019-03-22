// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson.jsonpointer;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.ImmutableList;
import java.util.List;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.ThreadSafe;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.core.TreeNode;

@JsonSerialize(using = ToStringSerializer.class)
@ThreadSafe
public abstract class TreePointer<T extends TreeNode> implements Iterable<TokenResolver<T>>
{
    protected static final MessageBundle BUNDLE;
    private static final char SLASH = '/';
    private final T missing;
    protected final List<TokenResolver<T>> tokenResolvers;
    
    protected TreePointer(final T missing, final List<TokenResolver<T>> tokenResolvers) {
        this.missing = missing;
        this.tokenResolvers = (List<TokenResolver<T>>)ImmutableList.copyOf((Collection<?>)tokenResolvers);
    }
    
    protected TreePointer(final List<TokenResolver<T>> tokenResolvers) {
        this(null, (List<TokenResolver<TreeNode>>)tokenResolvers);
    }
    
    public final T get(final T node) {
        T ret = node;
        for (final TokenResolver<T> tokenResolver : this.tokenResolvers) {
            if (ret == null) {
                break;
            }
            ret = tokenResolver.get(ret);
        }
        return ret;
    }
    
    public final T path(final T node) {
        final T ret = this.get(node);
        return (ret == null) ? this.missing : ret;
    }
    
    public final boolean isEmpty() {
        return this.tokenResolvers.isEmpty();
    }
    
    @Override
    public final Iterator<TokenResolver<T>> iterator() {
        return this.tokenResolvers.iterator();
    }
    
    @Override
    public final int hashCode() {
        return this.tokenResolvers.hashCode();
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
        final TreePointer<?> other = (TreePointer<?>)obj;
        return this.tokenResolvers.equals(other.tokenResolvers);
    }
    
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final TokenResolver<T> tokenResolver : this.tokenResolvers) {
            sb.append('/').append(tokenResolver);
        }
        return sb.toString();
    }
    
    protected static List<ReferenceToken> tokensFromInput(final String input) throws JsonPointerException {
        String s = TreePointer.BUNDLE.checkNotNull(input, "nullInput");
        final List<ReferenceToken> ret = (List<ReferenceToken>)Lists.newArrayList();
        while (!s.isEmpty()) {
            final char c = s.charAt(0);
            if (c != '/') {
                throw new JsonPointerException(TreePointer.BUNDLE.getMessage("notSlash"));
            }
            s = s.substring(1);
            final int index = s.indexOf(47);
            final String cooked = (index == -1) ? s : s.substring(0, index);
            ret.add(ReferenceToken.fromCooked(cooked));
            if (index == -1) {
                break;
            }
            s = s.substring(index);
        }
        return ret;
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonPointerMessages.class);
    }
}
