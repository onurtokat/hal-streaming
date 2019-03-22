// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.reflectasm.shaded.org.objectweb.asm;

public abstract class FieldVisitor
{
    protected final int api;
    protected FieldVisitor fv;
    
    public FieldVisitor(final int n) {
        this(n, null);
    }
    
    public FieldVisitor(final int api, final FieldVisitor fv) {
        this.api = api;
        this.fv = fv;
    }
    
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        if (this.fv != null) {
            return this.fv.visitAnnotation(s, b);
        }
        return null;
    }
    
    public void visitAttribute(final Attribute attribute) {
        if (this.fv != null) {
            this.fv.visitAttribute(attribute);
        }
    }
    
    public void visitEnd() {
        if (this.fv != null) {
            this.fv.visitEnd();
        }
    }
}
