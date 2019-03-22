// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util.equivalence;

import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.google.common.base.Equivalence;

@Deprecated
public final class SchemaTreeEquivalence extends Equivalence<SchemaTree>
{
    private static final Equivalence<SchemaTree> INSTANCE;
    
    public static Equivalence<SchemaTree> getInstance() {
        return SchemaTreeEquivalence.INSTANCE;
    }
    
    @Override
    protected boolean doEquivalent(final SchemaTree a, final SchemaTree b) {
        return a.getLoadingRef().equals(b.getLoadingRef()) && a.getContext().equals(b.getContext()) && a.getPointer().equals(b.getPointer()) && a.getBaseNode().equals(b.getBaseNode());
    }
    
    @Override
    protected int doHash(final SchemaTree t) {
        int ret = t.getLoadingRef().hashCode();
        ret = 31 * ret + t.getContext().hashCode();
        ret = 31 * ret + t.getPointer().hashCode();
        ret = 31 * ret + t.getBaseNode().hashCode();
        return ret;
    }
    
    static {
        INSTANCE = new SchemaTreeEquivalence();
    }
}
