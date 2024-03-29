// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.introspect;

import java.lang.reflect.Type;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.JavaType;
import java.lang.reflect.TypeVariable;
import com.fasterxml.jackson.databind.type.TypeBindings;
import java.lang.annotation.Annotation;

public abstract class AnnotatedWithParams extends AnnotatedMember
{
    private static final long serialVersionUID = 1L;
    protected final AnnotationMap[] _paramAnnotations;
    
    protected AnnotatedWithParams(final AnnotationMap annotations, final AnnotationMap[] paramAnnotations) {
        super(annotations);
        this._paramAnnotations = paramAnnotations;
    }
    
    public final void addOrOverrideParam(final int paramIndex, final Annotation a) {
        AnnotationMap old = this._paramAnnotations[paramIndex];
        if (old == null) {
            old = new AnnotationMap();
            this._paramAnnotations[paramIndex] = old;
        }
        old.add(a);
    }
    
    protected AnnotatedParameter replaceParameterAnnotations(final int index, final AnnotationMap ann) {
        this._paramAnnotations[index] = ann;
        return this.getParameter(index);
    }
    
    protected JavaType getType(TypeBindings bindings, final TypeVariable<?>[] typeParams) {
        if (typeParams != null && typeParams.length > 0) {
            bindings = bindings.childInstance();
            for (final TypeVariable<?> var : typeParams) {
                final String name = var.getName();
                bindings._addPlaceholder(name);
                final Type lowerBound = var.getBounds()[0];
                final JavaType type = (lowerBound == null) ? TypeFactory.unknownType() : bindings.resolveType(lowerBound);
                bindings.addBinding(var.getName(), type);
            }
        }
        return bindings.resolveType(this.getGenericType());
    }
    
    @Override
    public final <A extends Annotation> A getAnnotation(final Class<A> acls) {
        return this._annotations.get(acls);
    }
    
    public final AnnotationMap getParameterAnnotations(final int index) {
        if (this._paramAnnotations != null && index >= 0 && index < this._paramAnnotations.length) {
            return this._paramAnnotations[index];
        }
        return null;
    }
    
    public final AnnotatedParameter getParameter(final int index) {
        return new AnnotatedParameter(this, this.getGenericParameterType(index), this.getParameterAnnotations(index), index);
    }
    
    public abstract int getParameterCount();
    
    public abstract Class<?> getRawParameterType(final int p0);
    
    public abstract Type getGenericParameterType(final int p0);
    
    public final JavaType resolveParameterType(final int index, final TypeBindings bindings) {
        return bindings.resolveType(this.getGenericParameterType(index));
    }
    
    public final int getAnnotationCount() {
        return this._annotations.size();
    }
    
    public abstract Object call() throws Exception;
    
    public abstract Object call(final Object[] p0) throws Exception;
    
    public abstract Object call1(final Object p0) throws Exception;
}
