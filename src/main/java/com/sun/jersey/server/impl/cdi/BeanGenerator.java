// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.impl.cdi;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ClassWriter;
import java.security.PrivilegedActionException;
import java.util.logging.Level;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class BeanGenerator
{
    private static final Logger LOGGER;
    private String prefix;
    private Method defineClassMethod;
    private int generatedClassCounter;
    
    BeanGenerator(final String prefix) {
        this.generatedClassCounter = 0;
        this.prefix = prefix;
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            this.defineClassMethod = AccessController.doPrivileged((PrivilegedExceptionAction<Method>)new PrivilegedExceptionAction<Method>() {
                @Override
                public Method run() throws Exception {
                    final Class classLoaderClass = Class.forName("java.lang.ClassLoader");
                    final Method method = classLoaderClass.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                    method.setAccessible(true);
                    return method;
                }
            });
        }
        catch (PrivilegedActionException e) {
            BeanGenerator.LOGGER.log(Level.SEVERE, "failed to access method ClassLoader.defineClass", e);
            throw new RuntimeException(e);
        }
    }
    
    Class<?> createBeanClass() {
        final ClassWriter writer = new ClassWriter(0);
        final String name = this.prefix + Integer.toString(this.generatedClassCounter++);
        writer.visit(50, 1, name, null, "java/lang/Object", null);
        final MethodVisitor methodVisitor = writer.visitMethod(1, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitMethodInsn(183, "java/lang/Object", "<init>", "()V");
        methodVisitor.visitInsn(177);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
        writer.visitEnd();
        final byte[] bytecode = writer.toByteArray();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Class<?> result = (Class<?>)this.defineClassMethod.invoke(classLoader, name.replace("/", "."), bytecode, 0, bytecode.length);
            BeanGenerator.LOGGER.fine("Created class " + result.getName());
            return result;
        }
        catch (Throwable t) {
            BeanGenerator.LOGGER.log(Level.SEVERE, "error calling ClassLoader.defineClass", t);
            return null;
        }
    }
    
    static {
        LOGGER = Logger.getLogger(CDIExtension.class.getName());
    }
}
