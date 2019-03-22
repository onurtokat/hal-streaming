// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.reflectasm;

import com.esotericsoftware.reflectasm.shaded.org.objectweb.asm.MethodVisitor;
import com.esotericsoftware.reflectasm.shaded.org.objectweb.asm.ClassWriter;
import java.lang.reflect.Modifier;

public abstract class ConstructorAccess<T>
{
    boolean isNonStaticMemberClass;
    
    public boolean isNonStaticMemberClass() {
        return this.isNonStaticMemberClass;
    }
    
    public abstract T newInstance();
    
    public abstract T newInstance(final Object p0);
    
    public static <T> ConstructorAccess<T> get(final Class<T> type) {
        final Class enclosingType = type.getEnclosingClass();
        final boolean isNonStaticMemberClass = enclosingType != null && type.isMemberClass() && !Modifier.isStatic(type.getModifiers());
        final String className = type.getName();
        String accessClassName = className + "ConstructorAccess";
        if (accessClassName.startsWith("java.")) {
            accessClassName = "reflectasm." + accessClassName;
        }
        Class accessClass = null;
        final AccessClassLoader loader = AccessClassLoader.get(type);
        synchronized (loader) {
            try {
                accessClass = loader.loadClass(accessClassName);
            }
            catch (ClassNotFoundException ignored) {
                final String accessClassNameInternal = accessClassName.replace('.', '/');
                final String classNameInternal = className.replace('.', '/');
                String enclosingClassNameInternal = null;
                Label_0252: {
                    if (!isNonStaticMemberClass) {
                        enclosingClassNameInternal = null;
                        try {
                            type.getConstructor((Class<?>[])null);
                            break Label_0252;
                        }
                        catch (Exception ex2) {
                            throw new RuntimeException("Class cannot be created (missing no-arg constructor): " + type.getName());
                        }
                    }
                    enclosingClassNameInternal = enclosingType.getName().replace('.', '/');
                    try {
                        type.getConstructor(enclosingType);
                    }
                    catch (Exception ex2) {
                        throw new RuntimeException("Non-static member class cannot be created (missing enclosing class constructor): " + type.getName());
                    }
                }
                final ClassWriter cw = new ClassWriter(0);
                cw.visit(196653, 33, accessClassNameInternal, null, "com/esotericsoftware/reflectasm/ConstructorAccess", null);
                insertConstructor(cw);
                insertNewInstance(cw, classNameInternal);
                insertNewInstanceInner(cw, classNameInternal, enclosingClassNameInternal);
                cw.visitEnd();
                accessClass = loader.defineClass(accessClassName, cw.toByteArray());
            }
        }
        try {
            final ConstructorAccess<T> access = accessClass.newInstance();
            access.isNonStaticMemberClass = isNonStaticMemberClass;
            return access;
        }
        catch (Exception ex) {
            throw new RuntimeException("Error constructing constructor access class: " + accessClassName, ex);
        }
    }
    
    private static void insertConstructor(final ClassWriter cw) {
        final MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, "com/esotericsoftware/reflectasm/ConstructorAccess", "<init>", "()V");
        mv.visitInsn(177);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }
    
    static void insertNewInstance(final ClassWriter cw, final String classNameInternal) {
        final MethodVisitor mv = cw.visitMethod(1, "newInstance", "()Ljava/lang/Object;", null, null);
        mv.visitCode();
        mv.visitTypeInsn(187, classNameInternal);
        mv.visitInsn(89);
        mv.visitMethodInsn(183, classNameInternal, "<init>", "()V");
        mv.visitInsn(176);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }
    
    static void insertNewInstanceInner(final ClassWriter cw, final String classNameInternal, final String enclosingClassNameInternal) {
        final MethodVisitor mv = cw.visitMethod(1, "newInstance", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        mv.visitCode();
        if (enclosingClassNameInternal != null) {
            mv.visitTypeInsn(187, classNameInternal);
            mv.visitInsn(89);
            mv.visitVarInsn(25, 1);
            mv.visitTypeInsn(192, enclosingClassNameInternal);
            mv.visitInsn(89);
            mv.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            mv.visitInsn(87);
            mv.visitMethodInsn(183, classNameInternal, "<init>", "(L" + enclosingClassNameInternal + ";)V");
            mv.visitInsn(176);
            mv.visitMaxs(4, 2);
        }
        else {
            mv.visitTypeInsn(187, "java/lang/UnsupportedOperationException");
            mv.visitInsn(89);
            mv.visitLdcInsn("Not an inner class.");
            mv.visitMethodInsn(183, "java/lang/UnsupportedOperationException", "<init>", "(Ljava/lang/String;)V");
            mv.visitInsn(191);
            mv.visitMaxs(3, 2);
        }
        mv.visitEnd();
    }
}
