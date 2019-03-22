// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.reflectasm;

import java.lang.reflect.Method;
import java.util.ArrayList;

class AccessClassLoader extends ClassLoader
{
    private static final ArrayList<AccessClassLoader> accessClassLoaders;
    
    static AccessClassLoader get(final Class type) {
        final ClassLoader parent = type.getClassLoader();
        synchronized (AccessClassLoader.accessClassLoaders) {
            for (int i = 0, n = AccessClassLoader.accessClassLoaders.size(); i < n; ++i) {
                final AccessClassLoader accessClassLoader = AccessClassLoader.accessClassLoaders.get(i);
                if (accessClassLoader.getParent() == parent) {
                    return accessClassLoader;
                }
            }
            final AccessClassLoader accessClassLoader2 = new AccessClassLoader(parent);
            AccessClassLoader.accessClassLoaders.add(accessClassLoader2);
            return accessClassLoader2;
        }
    }
    
    private AccessClassLoader(final ClassLoader parent) {
        super(parent);
    }
    
    protected synchronized Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
        if (name.equals(FieldAccess.class.getName())) {
            return FieldAccess.class;
        }
        if (name.equals(MethodAccess.class.getName())) {
            return MethodAccess.class;
        }
        if (name.equals(ConstructorAccess.class.getName())) {
            return ConstructorAccess.class;
        }
        return super.loadClass(name, resolve);
    }
    
    Class<?> defineClass(final String name, final byte[] bytes) throws ClassFormatError {
        try {
            final Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
            method.setAccessible(true);
            return (Class<?>)method.invoke(this.getParent(), name, bytes, 0, bytes.length);
        }
        catch (Exception ignored) {
            return this.defineClass(name, bytes, 0, bytes.length);
        }
    }
    
    static {
        accessClassLoaders = new ArrayList<AccessClassLoader>();
    }
}
