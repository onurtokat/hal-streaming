// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.reflectasm;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.esotericsoftware.reflectasm.shaded.org.objectweb.asm.MethodVisitor;
import com.esotericsoftware.reflectasm.shaded.org.objectweb.asm.Type;
import com.esotericsoftware.reflectasm.shaded.org.objectweb.asm.Label;
import com.esotericsoftware.reflectasm.shaded.org.objectweb.asm.ClassWriter;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class MethodAccess
{
    private String[] methodNames;
    private Class[][] parameterTypes;
    
    public abstract Object invoke(final Object p0, final int p1, final Object... p2);
    
    public Object invoke(final Object object, final String methodName, final Object... args) {
        return this.invoke(object, this.getIndex(methodName), args);
    }
    
    public int getIndex(final String methodName) {
        for (int i = 0, n = this.methodNames.length; i < n; ++i) {
            if (this.methodNames[i].equals(methodName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unable to find public method: " + methodName);
    }
    
    public int getIndex(final String methodName, final Class... paramTypes) {
        for (int i = 0, n = this.methodNames.length; i < n; ++i) {
            if (this.methodNames[i].equals(methodName) && Arrays.equals(paramTypes, this.parameterTypes[i])) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unable to find public method: " + methodName + " " + Arrays.toString(this.parameterTypes));
    }
    
    public String[] getMethodNames() {
        return this.methodNames;
    }
    
    public Class[][] getParameterTypes() {
        return this.parameterTypes;
    }
    
    public static MethodAccess get(final Class type) {
        final ArrayList<Method> methods = new ArrayList<Method>();
        for (Class nextClass = type; nextClass != Object.class; nextClass = nextClass.getSuperclass()) {
            final Method[] declaredMethods = nextClass.getDeclaredMethods();
            for (int i = 0, n = declaredMethods.length; i < n; ++i) {
                final Method method = declaredMethods[i];
                final int modifiers = method.getModifiers();
                if (!Modifier.isStatic(modifiers)) {
                    if (!Modifier.isPrivate(modifiers)) {
                        methods.add(method);
                    }
                }
            }
        }
        final Class[][] parameterTypes = new Class[methods.size()][];
        final String[] methodNames = new String[methods.size()];
        for (int j = 0, n2 = methodNames.length; j < n2; ++j) {
            final Method method2 = methods.get(j);
            methodNames[j] = method2.getName();
            parameterTypes[j] = method2.getParameterTypes();
        }
        final String className = type.getName();
        String accessClassName = className + "MethodAccess";
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
                final ClassWriter cw = new ClassWriter(1);
                cw.visit(196653, 33, accessClassNameInternal, null, "com/esotericsoftware/reflectasm/MethodAccess", null);
                MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", null, null);
                mv.visitCode();
                mv.visitVarInsn(25, 0);
                mv.visitMethodInsn(183, "com/esotericsoftware/reflectasm/MethodAccess", "<init>", "()V");
                mv.visitInsn(177);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
                mv = cw.visitMethod(129, "invoke", "(Ljava/lang/Object;I[Ljava/lang/Object;)Ljava/lang/Object;", null, null);
                mv.visitCode();
                if (!methods.isEmpty()) {
                    mv.visitVarInsn(25, 1);
                    mv.visitTypeInsn(192, classNameInternal);
                    mv.visitVarInsn(58, 4);
                    mv.visitVarInsn(21, 2);
                    final Label[] labels = new Label[methods.size()];
                    for (int k = 0, n3 = labels.length; k < n3; ++k) {
                        labels[k] = new Label();
                    }
                    final Label defaultLabel = new Label();
                    mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
                    final StringBuilder buffer = new StringBuilder(128);
                    for (int l = 0, n4 = labels.length; l < n4; ++l) {
                        mv.visitLabel(labels[l]);
                        if (l == 0) {
                            mv.visitFrame(1, 1, new Object[] { classNameInternal }, 0, null);
                        }
                        else {
                            mv.visitFrame(3, 0, null, 0, null);
                        }
                        mv.visitVarInsn(25, 4);
                        buffer.setLength(0);
                        buffer.append('(');
                        final Method method3 = methods.get(l);
                        final Class[] paramTypes = method3.getParameterTypes();
                        for (int paramIndex = 0; paramIndex < paramTypes.length; ++paramIndex) {
                            mv.visitVarInsn(25, 3);
                            mv.visitIntInsn(16, paramIndex);
                            mv.visitInsn(50);
                            final Type paramType = Type.getType(paramTypes[paramIndex]);
                            switch (paramType.getSort()) {
                                case 1: {
                                    mv.visitTypeInsn(192, "java/lang/Boolean");
                                    mv.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z");
                                    break;
                                }
                                case 3: {
                                    mv.visitTypeInsn(192, "java/lang/Byte");
                                    mv.visitMethodInsn(182, "java/lang/Byte", "byteValue", "()B");
                                    break;
                                }
                                case 2: {
                                    mv.visitTypeInsn(192, "java/lang/Character");
                                    mv.visitMethodInsn(182, "java/lang/Character", "charValue", "()C");
                                    break;
                                }
                                case 4: {
                                    mv.visitTypeInsn(192, "java/lang/Short");
                                    mv.visitMethodInsn(182, "java/lang/Short", "shortValue", "()S");
                                    break;
                                }
                                case 5: {
                                    mv.visitTypeInsn(192, "java/lang/Integer");
                                    mv.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I");
                                    break;
                                }
                                case 6: {
                                    mv.visitTypeInsn(192, "java/lang/Float");
                                    mv.visitMethodInsn(182, "java/lang/Float", "floatValue", "()F");
                                    break;
                                }
                                case 7: {
                                    mv.visitTypeInsn(192, "java/lang/Long");
                                    mv.visitMethodInsn(182, "java/lang/Long", "longValue", "()J");
                                    break;
                                }
                                case 8: {
                                    mv.visitTypeInsn(192, "java/lang/Double");
                                    mv.visitMethodInsn(182, "java/lang/Double", "doubleValue", "()D");
                                    break;
                                }
                                case 9: {
                                    mv.visitTypeInsn(192, paramType.getDescriptor());
                                    break;
                                }
                                case 10: {
                                    mv.visitTypeInsn(192, paramType.getInternalName());
                                    break;
                                }
                            }
                            buffer.append(paramType.getDescriptor());
                        }
                        buffer.append(')');
                        buffer.append(Type.getDescriptor(method3.getReturnType()));
                        mv.visitMethodInsn(182, classNameInternal, method3.getName(), buffer.toString());
                        switch (Type.getType(method3.getReturnType()).getSort()) {
                            case 0: {
                                mv.visitInsn(1);
                                break;
                            }
                            case 1: {
                                mv.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
                                break;
                            }
                            case 3: {
                                mv.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                                break;
                            }
                            case 2: {
                                mv.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
                                break;
                            }
                            case 4: {
                                mv.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                                break;
                            }
                            case 5: {
                                mv.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                break;
                            }
                            case 6: {
                                mv.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                                break;
                            }
                            case 7: {
                                mv.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                                break;
                            }
                            case 8: {
                                mv.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                                break;
                            }
                        }
                        mv.visitInsn(176);
                    }
                    mv.visitLabel(defaultLabel);
                    mv.visitFrame(3, 0, null, 0, null);
                }
                mv.visitTypeInsn(187, "java/lang/IllegalArgumentException");
                mv.visitInsn(89);
                mv.visitTypeInsn(187, "java/lang/StringBuilder");
                mv.visitInsn(89);
                mv.visitLdcInsn("Method not found: ");
                mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
                mv.visitVarInsn(21, 2);
                mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
                mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
                mv.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
                mv.visitInsn(191);
                mv.visitMaxs(0, 0);
                mv.visitEnd();
                cw.visitEnd();
                final byte[] data = cw.toByteArray();
                accessClass = loader.defineClass(accessClassName, data);
            }
        }
        try {
            final MethodAccess access = accessClass.newInstance();
            access.methodNames = methodNames;
            access.parameterTypes = parameterTypes;
            return access;
        }
        catch (Exception ex) {
            throw new RuntimeException("Error constructing method access class: " + accessClassName, ex);
        }
    }
}
