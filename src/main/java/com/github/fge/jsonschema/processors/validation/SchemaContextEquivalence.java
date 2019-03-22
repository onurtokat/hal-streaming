// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.validation;

import com.github.fge.jsonschema.processors.data.SchemaContext;
import com.google.common.base.Equivalence;

public final class SchemaContextEquivalence extends Equivalence<SchemaContext>
{
    private static final Equivalence<SchemaContext> INSTANCE;
    
    public static Equivalence<SchemaContext> getInstance() {
        return SchemaContextEquivalence.INSTANCE;
    }
    
    @Override
    protected boolean doEquivalent(final SchemaContext a, final SchemaContext b) {
        return a.getSchema().equals(b.getSchema()) && a.getInstanceType() == b.getInstanceType();
    }
    
    @Override
    protected int doHash(final SchemaContext t) {
        return t.getSchema().hashCode() ^ t.getInstanceType().hashCode();
    }
    
    static {
        INSTANCE = new SchemaContextEquivalence();
    }
}
