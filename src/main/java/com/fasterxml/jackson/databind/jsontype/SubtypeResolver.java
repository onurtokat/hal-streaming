// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import java.util.Collection;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;

public abstract class SubtypeResolver
{
    public abstract void registerSubtypes(final NamedType... p0);
    
    public abstract void registerSubtypes(final Class<?>... p0);
    
    public abstract Collection<NamedType> collectAndResolveSubtypes(final AnnotatedMember p0, final MapperConfig<?> p1, final AnnotationIntrospector p2, final JavaType p3);
    
    public abstract Collection<NamedType> collectAndResolveSubtypes(final AnnotatedClass p0, final MapperConfig<?> p1, final AnnotationIntrospector p2);
}
