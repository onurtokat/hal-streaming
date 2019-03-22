// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.core.osgi;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.Callable;
import java.util.List;
import java.util.Map;

public class OsgiLocator
{
    private static Map<String, List<Callable<List<Class>>>> factories;
    private static ReadWriteLock lock;
    
    public static void unregister(final String id, final Callable<List<Class>> factory) {
        OsgiLocator.lock.writeLock().lock();
        try {
            final List<Callable<List<Class>>> l = OsgiLocator.factories.get(id);
            if (l != null) {
                l.remove(factory);
            }
        }
        finally {
            OsgiLocator.lock.writeLock().unlock();
        }
    }
    
    public static void register(final String id, final Callable<List<Class>> factory) {
        OsgiLocator.lock.writeLock().lock();
        try {
            List<Callable<List<Class>>> l = OsgiLocator.factories.get(id);
            if (l == null) {
                l = new ArrayList<Callable<List<Class>>>();
                OsgiLocator.factories.put(id, l);
            }
            l.add(factory);
        }
        finally {
            OsgiLocator.lock.writeLock().unlock();
        }
    }
    
    public static synchronized Class locate(final String factoryId) {
        OsgiLocator.lock.readLock().lock();
        try {
            final List<Callable<List<Class>>> l = OsgiLocator.factories.get(factoryId);
            if (l == null || l.isEmpty()) {
                return null;
            }
            final Callable<List<Class>> c = l.get(l.size() - 1);
            List<Class> classes;
            try {
                classes = c.call();
            }
            catch (Exception e) {
                return null;
            }
            return classes.get(0);
        }
        finally {
            OsgiLocator.lock.readLock().unlock();
        }
    }
    
    public static synchronized List<Class> locateAll(final String factoryId) {
        final List<Class> classes = new ArrayList<Class>();
        final List<Callable<List<Class>>> l = OsgiLocator.factories.get(factoryId);
        if (l == null) {
            return classes;
        }
        for (final Callable<List<Class>> c : l) {
            try {
                classes.addAll(c.call());
            }
            catch (Exception ex) {}
        }
        return classes;
    }
    
    static {
        OsgiLocator.factories = new HashMap<String, List<Callable<List<Class>>>>();
        OsgiLocator.lock = new ReentrantReadWriteLock();
    }
}
