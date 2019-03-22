// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.reflectasm.shaded.org.objectweb.asm;

public abstract class MethodVisitor
{
    protected final int api;
    protected MethodVisitor mv;
    
    public MethodVisitor(final int n) {
        this(n, null);
    }
    
    public MethodVisitor(final int api, final MethodVisitor mv) {
        this.api = api;
        this.mv = mv;
    }
    
    public AnnotationVisitor visitAnnotationDefault() {
        if (this.mv != null) {
            return this.mv.visitAnnotationDefault();
        }
        return null;
    }
    
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        if (this.mv != null) {
            return this.mv.visitAnnotation(s, b);
        }
        return null;
    }
    
    public AnnotationVisitor visitParameterAnnotation(final int n, final String s, final boolean b) {
        if (this.mv != null) {
            return this.mv.visitParameterAnnotation(n, s, b);
        }
        return null;
    }
    
    public void visitAttribute(final Attribute attribute) {
        if (this.mv != null) {
            this.mv.visitAttribute(attribute);
        }
    }
    
    public void visitCode() {
        if (this.mv != null) {
            this.mv.visitCode();
        }
    }
    
    public void visitFrame(final int n, final int n2, final Object[] array, final int n3, final Object[] array2) {
        if (this.mv != null) {
            this.mv.visitFrame(n, n2, array, n3, array2);
        }
    }
    
    public void visitInsn(final int n) {
        if (this.mv != null) {
            this.mv.visitInsn(n);
        }
    }
    
    public void visitIntInsn(final int n, final int n2) {
        if (this.mv != null) {
            this.mv.visitIntInsn(n, n2);
        }
    }
    
    public void visitVarInsn(final int n, final int n2) {
        if (this.mv != null) {
            this.mv.visitVarInsn(n, n2);
        }
    }
    
    public void visitTypeInsn(final int n, final String s) {
        if (this.mv != null) {
            this.mv.visitTypeInsn(n, s);
        }
    }
    
    public void visitFieldInsn(final int n, final String s, final String s2, final String s3) {
        if (this.mv != null) {
            this.mv.visitFieldInsn(n, s, s2, s3);
        }
    }
    
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3) {
        if (this.mv != null) {
            this.mv.visitMethodInsn(n, s, s2, s3);
        }
    }
    
    public void visitInvokeDynamicInsn(final String s, final String s2, final Handle handle, final Object... array) {
        if (this.mv != null) {
            this.mv.visitInvokeDynamicInsn(s, s2, handle, array);
        }
    }
    
    public void visitJumpInsn(final int n, final Label label) {
        if (this.mv != null) {
            this.mv.visitJumpInsn(n, label);
        }
    }
    
    public void visitLabel(final Label label) {
        if (this.mv != null) {
            this.mv.visitLabel(label);
        }
    }
    
    public void visitLdcInsn(final Object o) {
        if (this.mv != null) {
            this.mv.visitLdcInsn(o);
        }
    }
    
    public void visitIincInsn(final int n, final int n2) {
        if (this.mv != null) {
            this.mv.visitIincInsn(n, n2);
        }
    }
    
    public void visitTableSwitchInsn(final int n, final int n2, final Label label, final Label... array) {
        if (this.mv != null) {
            this.mv.visitTableSwitchInsn(n, n2, label, array);
        }
    }
    
    public void visitLookupSwitchInsn(final Label label, final int[] array, final Label[] array2) {
        if (this.mv != null) {
            this.mv.visitLookupSwitchInsn(label, array, array2);
        }
    }
    
    public void visitMultiANewArrayInsn(final String s, final int n) {
        if (this.mv != null) {
            this.mv.visitMultiANewArrayInsn(s, n);
        }
    }
    
    public void visitTryCatchBlock(final Label label, final Label label2, final Label label3, final String s) {
        if (this.mv != null) {
            this.mv.visitTryCatchBlock(label, label2, label3, s);
        }
    }
    
    public void visitLocalVariable(final String s, final String s2, final String s3, final Label label, final Label label2, final int n) {
        if (this.mv != null) {
            this.mv.visitLocalVariable(s, s2, s3, label, label2, n);
        }
    }
    
    public void visitLineNumber(final int n, final Label label) {
        if (this.mv != null) {
            this.mv.visitLineNumber(n, label);
        }
    }
    
    public void visitMaxs(final int n, final int n2) {
        if (this.mv != null) {
            this.mv.visitMaxs(n, n2);
        }
    }
    
    public void visitEnd() {
        if (this.mv != null) {
            this.mv.visitEnd();
        }
    }
}
