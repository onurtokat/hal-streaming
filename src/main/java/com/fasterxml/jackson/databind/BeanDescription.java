// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import java.util.Set;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.util.Map;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import java.util.List;
import com.fasterxml.jackson.databind.util.Annotations;
import java.lang.reflect.Type;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;

public abstract class BeanDescription
{
    protected final JavaType _type;
    
    protected BeanDescription(final JavaType type) {
        this._type = type;
    }
    
    public JavaType getType() {
        return this._type;
    }
    
    public Class<?> getBeanClass() {
        return this._type.getRawClass();
    }
    
    public abstract AnnotatedClass getClassInfo();
    
    public abstract ObjectIdInfo getObjectIdInfo();
    
    public abstract boolean hasKnownClassAnnotations();
    
    public abstract TypeBindings bindingsForBeanType();
    
    public abstract JavaType resolveType(final Type p0);
    
    public abstract Annotations getClassAnnotations();
    
    public abstract List<BeanPropertyDefinition> findProperties();
    
    public abstract Map<String, AnnotatedMember> findBackReferenceProperties();
    
    public abstract Set<String> getIgnoredPropertyNames();
    
    public abstract List<AnnotatedConstructor> getConstructors();
    
    public abstract List<AnnotatedMethod> getFactoryMethods();
    
    public abstract AnnotatedConstructor findDefaultConstructor();
    
    public abstract Constructor<?> findSingleArgConstructor(final Class<?>... p0);
    
    public abstract Method findFactoryMethod(final Class<?>... p0);
    
    public abstract AnnotatedMember findAnyGetter();
    
    public abstract AnnotatedMethod findAnySetter();
    
    public abstract AnnotatedMethod findJsonValueMethod();
    
    public abstract AnnotatedMethod findMethod(final String p0, final Class<?>[] p1);
    
    public abstract JsonInclude.Include findSerializationInclusion(final JsonInclude.Include p0);
    
    public abstract JsonFormat.Value findExpectedFormat(final JsonFormat.Value p0);
    
    public abstract Converter<Object, Object> findSerializationConverter();
    
    public abstract Converter<Object, Object> findDeserializationConverter();
    
    public abstract Map<Object, AnnotatedMember> findInjectables();
    
    public abstract Class<?> findPOJOBuilder();
    
    public abstract JsonPOJOBuilder.Value findPOJOBuilderConfig();
    
    public abstract Object instantiateBean(final boolean p0);
}
