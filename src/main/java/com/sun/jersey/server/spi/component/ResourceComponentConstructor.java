// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.spi.component;

import java.util.Collections;
import java.util.SortedSet;
import java.lang.reflect.AccessibleObject;
import com.sun.jersey.api.model.AbstractResourceConstructor;
import java.util.Comparator;
import java.util.TreeSet;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.spi.inject.Injectable;
import java.util.Collection;
import com.sun.jersey.spi.inject.Errors;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.ServerInjectableProviderContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import java.lang.reflect.Method;
import java.util.List;
import java.lang.reflect.Constructor;

public class ResourceComponentConstructor
{
    private final Class c;
    private final ResourceComponentInjector rci;
    private final Constructor constructor;
    private final List<Method> postConstructs;
    private final List<AbstractHttpContextInjectable> injectables;
    
    public ResourceComponentConstructor(final ServerInjectableProviderContext sipc, final ComponentScope scope, final AbstractResource ar) {
        this.postConstructs = new ArrayList<Method>();
        this.c = ar.getResourceClass();
        final int modifiers = this.c.getModifiers();
        if (!Modifier.isPublic(modifiers)) {
            Errors.nonPublicClass(this.c);
        }
        if (Modifier.isAbstract(modifiers)) {
            if (Modifier.isInterface(modifiers)) {
                Errors.interfaceClass(this.c);
            }
            else {
                Errors.abstractClass(this.c);
            }
        }
        if (this.c.getEnclosingClass() != null && !Modifier.isStatic(modifiers)) {
            Errors.innerClass(this.c);
        }
        if (Modifier.isPublic(modifiers) && !Modifier.isAbstract(modifiers) && this.c.getConstructors().length == 0) {
            Errors.nonPublicConstructor(this.c);
        }
        this.rci = new ResourceComponentInjector(sipc, scope, ar);
        this.postConstructs.addAll(ar.getPostConstructMethods());
        final ConstructorInjectablePair cip = this.getConstructor(sipc, scope, ar);
        if (cip == null) {
            this.constructor = null;
            this.injectables = null;
        }
        else if (cip.is.isEmpty()) {
            this.constructor = cip.con;
            this.injectables = null;
        }
        else {
            if (cip.is.contains(null)) {
                for (int i = 0; i < cip.is.size(); ++i) {
                    if (cip.is.get(i) == null) {
                        Errors.missingDependency(cip.con, i);
                    }
                }
            }
            this.constructor = cip.con;
            this.injectables = (List<AbstractHttpContextInjectable>)AbstractHttpContextInjectable.transform(cip.is);
        }
    }
    
    public Class getResourceClass() {
        return this.c;
    }
    
    public Object construct(final HttpContext hc) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Object o = this._construct(hc);
        this.rci.inject(hc, o);
        for (final Method postConstruct : this.postConstructs) {
            postConstruct.invoke(o, new Object[0]);
        }
        return o;
    }
    
    private Object _construct(final HttpContext hc) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.injectables == null) {
            return (this.constructor != null) ? this.constructor.newInstance(new Object[0]) : this.c.newInstance();
        }
        final Object[] params = new Object[this.injectables.size()];
        int i = 0;
        for (final AbstractHttpContextInjectable injectable : this.injectables) {
            params[i++] = ((injectable != null) ? injectable.getValue(hc) : null);
        }
        return this.constructor.newInstance(params);
    }
    
    private <T> ConstructorInjectablePair getConstructor(final ServerInjectableProviderContext sipc, final ComponentScope scope, final AbstractResource ar) {
        if (ar.getConstructors().isEmpty()) {
            return null;
        }
        final SortedSet<ConstructorInjectablePair> cs = new TreeSet<ConstructorInjectablePair>(new ConstructorComparator<Object>());
        for (final AbstractResourceConstructor arc : ar.getConstructors()) {
            final List<Injectable> is = sipc.getInjectable(arc.getCtor(), arc.getParameters(), scope);
            cs.add(new ConstructorInjectablePair(arc.getCtor(), (List)is));
        }
        return cs.first();
    }
    
    private static class ConstructorInjectablePair
    {
        private final Constructor con;
        private final List<Injectable> is;
        
        private ConstructorInjectablePair(final Constructor con, final List<Injectable> is) {
            this.con = con;
            this.is = is;
        }
    }
    
    private static class ConstructorComparator<T> implements Comparator<ConstructorInjectablePair>
    {
        @Override
        public int compare(final ConstructorInjectablePair o1, final ConstructorInjectablePair o2) {
            final int p = Collections.frequency(o1.is, null) - Collections.frequency(o2.is, null);
            if (p != 0) {
                return p;
            }
            return o2.con.getParameterTypes().length - o1.con.getParameterTypes().length;
        }
    }
}
