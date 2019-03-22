// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.impl.cdi;

import java.util.Collections;
import java.util.Iterator;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.inject.AmbiguousResolutionException;
import java.util.Set;
import java.lang.reflect.Type;
import java.lang.annotation.Annotation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

public class Utils
{
    public static Bean<?> getBean(final BeanManager bm, final Class<?> c) {
        final Set<Bean<?>> bs = (Set<Bean<?>>)bm.getBeans((Type)c, new Annotation[0]);
        if (bs.isEmpty()) {
            return null;
        }
        try {
            return (Bean<?>)bm.resolve((Set)bs);
        }
        catch (AmbiguousResolutionException ex) {
            if (isSharedBaseClass(c, bs)) {
                try {
                    return (Bean<?>)bm.resolve((Set)getBaseClassSubSet(c, bs));
                }
                catch (AmbiguousResolutionException ex2) {
                    return null;
                }
            }
            return null;
        }
    }
    
    public static <T> T getInstance(final BeanManager bm, final Class<T> c) {
        final Bean<?> b = getBean(bm, c);
        if (b == null) {
            return null;
        }
        final CreationalContext<?> cc = (CreationalContext<?>)bm.createCreationalContext((Contextual)b);
        return c.cast(bm.getReference((Bean)b, (Type)c, (CreationalContext)cc));
    }
    
    private static boolean isSharedBaseClass(final Class<?> c, final Set<Bean<?>> bs) {
        for (final Bean<?> b : bs) {
            if (!c.isAssignableFrom(b.getBeanClass())) {
                return false;
            }
        }
        return true;
    }
    
    private static Set<Bean<?>> getBaseClassSubSet(final Class<?> c, final Set<Bean<?>> bs) {
        for (final Bean<?> b : bs) {
            if (c == b.getBeanClass()) {
                return Collections.singleton(b);
            }
        }
        return bs;
    }
}
