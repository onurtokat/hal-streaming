// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.jsontype.impl;

import java.util.Iterator;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import java.util.HashMap;
import java.util.Collection;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import java.util.LinkedHashSet;
import java.io.Serializable;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;

public class StdSubtypeResolver extends SubtypeResolver implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected LinkedHashSet<NamedType> _registeredSubtypes;
    
    @Override
    public void registerSubtypes(final NamedType... types) {
        if (this._registeredSubtypes == null) {
            this._registeredSubtypes = new LinkedHashSet<NamedType>();
        }
        for (final NamedType type : types) {
            this._registeredSubtypes.add(type);
        }
    }
    
    @Override
    public void registerSubtypes(final Class<?>... classes) {
        final NamedType[] types = new NamedType[classes.length];
        for (int i = 0, len = classes.length; i < len; ++i) {
            types[i] = new NamedType(classes[i]);
        }
        this.registerSubtypes(types);
    }
    
    @Override
    public Collection<NamedType> collectAndResolveSubtypes(final AnnotatedMember property, final MapperConfig<?> config, final AnnotationIntrospector ai, final JavaType baseType) {
        final Class<?> rawBase = (baseType == null) ? property.getRawType() : baseType.getRawClass();
        final HashMap<NamedType, NamedType> collected = new HashMap<NamedType, NamedType>();
        if (this._registeredSubtypes != null) {
            for (final NamedType subtype : this._registeredSubtypes) {
                if (rawBase.isAssignableFrom(subtype.getType())) {
                    final AnnotatedClass curr = AnnotatedClass.constructWithoutSuperTypes(subtype.getType(), ai, config);
                    this._collectAndResolve(curr, subtype, config, ai, collected);
                }
            }
        }
        final Collection<NamedType> st = ai.findSubtypes(property);
        if (st != null) {
            for (final NamedType nt : st) {
                final AnnotatedClass ac = AnnotatedClass.constructWithoutSuperTypes(nt.getType(), ai, config);
                this._collectAndResolve(ac, nt, config, ai, collected);
            }
        }
        final NamedType rootType = new NamedType(rawBase, null);
        final AnnotatedClass ac2 = AnnotatedClass.constructWithoutSuperTypes(rawBase, ai, config);
        this._collectAndResolve(ac2, rootType, config, ai, collected);
        return new ArrayList<NamedType>(collected.values());
    }
    
    @Override
    public Collection<NamedType> collectAndResolveSubtypes(final AnnotatedClass type, final MapperConfig<?> config, final AnnotationIntrospector ai) {
        final HashMap<NamedType, NamedType> subtypes = new HashMap<NamedType, NamedType>();
        if (this._registeredSubtypes != null) {
            final Class<?> rawBase = type.getRawType();
            for (final NamedType subtype : this._registeredSubtypes) {
                if (rawBase.isAssignableFrom(subtype.getType())) {
                    final AnnotatedClass curr = AnnotatedClass.constructWithoutSuperTypes(subtype.getType(), ai, config);
                    this._collectAndResolve(curr, subtype, config, ai, subtypes);
                }
            }
        }
        final NamedType rootType = new NamedType(type.getRawType(), null);
        this._collectAndResolve(type, rootType, config, ai, subtypes);
        return new ArrayList<NamedType>(subtypes.values());
    }
    
    protected void _collectAndResolve(final AnnotatedClass annotatedType, NamedType namedType, final MapperConfig<?> config, final AnnotationIntrospector ai, final HashMap<NamedType, NamedType> collectedSubtypes) {
        if (!namedType.hasName()) {
            final String name = ai.findTypeName(annotatedType);
            if (name != null) {
                namedType = new NamedType(namedType.getType(), name);
            }
        }
        if (collectedSubtypes.containsKey(namedType)) {
            if (namedType.hasName()) {
                final NamedType prev = collectedSubtypes.get(namedType);
                if (!prev.hasName()) {
                    collectedSubtypes.put(namedType, namedType);
                }
            }
            return;
        }
        collectedSubtypes.put(namedType, namedType);
        final Collection<NamedType> st = ai.findSubtypes(annotatedType);
        if (st != null && !st.isEmpty()) {
            for (NamedType subtype : st) {
                final AnnotatedClass subtypeClass = AnnotatedClass.constructWithoutSuperTypes(subtype.getType(), ai, config);
                if (!subtype.hasName()) {
                    subtype = new NamedType(subtype.getType(), ai.findTypeName(subtypeClass));
                }
                this._collectAndResolve(subtypeClass, subtype, config, ai, collectedSubtypes);
            }
        }
    }
}
