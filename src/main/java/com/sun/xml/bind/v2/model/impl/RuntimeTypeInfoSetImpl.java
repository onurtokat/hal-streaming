// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.xml.bind.v2.model.impl;

import com.sun.xml.bind.v2.model.runtime.RuntimeElementInfo;
import com.sun.xml.bind.v2.model.core.ElementInfo;
import com.sun.xml.bind.v2.model.core.NonElement;
import javax.xml.namespace.QName;
import com.sun.xml.bind.v2.model.nav.ReflectionNavigator;
import com.sun.xml.bind.v2.model.runtime.RuntimeNonElement;
import java.util.Map;
import com.sun.xml.bind.v2.model.nav.Navigator;
import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfoSet;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

final class RuntimeTypeInfoSetImpl extends TypeInfoSetImpl<Type, Class, Field, Method> implements RuntimeTypeInfoSet
{
    public RuntimeTypeInfoSetImpl(final AnnotationReader<Type, Class, Field, Method> reader) {
        super(Navigator.REFLECTION, reader, RuntimeBuiltinLeafInfoImpl.LEAVES);
    }
    
    protected RuntimeNonElement createAnyType() {
        return RuntimeAnyTypeImpl.theInstance;
    }
    
    public ReflectionNavigator getNavigator() {
        return (ReflectionNavigator)super.getNavigator();
    }
    
    public RuntimeNonElement getTypeInfo(final Type type) {
        return (RuntimeNonElement)super.getTypeInfo(type);
    }
    
    public RuntimeNonElement getAnyTypeInfo() {
        return (RuntimeNonElement)super.getAnyTypeInfo();
    }
    
    public RuntimeNonElement getClassInfo(final Class clazz) {
        return (RuntimeNonElement)super.getClassInfo(clazz);
    }
    
    public Map<Class, RuntimeClassInfoImpl> beans() {
        return (Map<Class, RuntimeClassInfoImpl>)super.beans();
    }
    
    public Map<Type, RuntimeBuiltinLeafInfoImpl<?>> builtins() {
        return (Map<Type, RuntimeBuiltinLeafInfoImpl<?>>)super.builtins();
    }
    
    public Map<Class, RuntimeEnumLeafInfoImpl<?, ?>> enums() {
        return (Map<Class, RuntimeEnumLeafInfoImpl<?, ?>>)super.enums();
    }
    
    public Map<Class, RuntimeArrayInfoImpl> arrays() {
        return (Map<Class, RuntimeArrayInfoImpl>)super.arrays();
    }
    
    public RuntimeElementInfoImpl getElementInfo(final Class scope, final QName name) {
        return (RuntimeElementInfoImpl)super.getElementInfo(scope, name);
    }
    
    public Map<QName, RuntimeElementInfoImpl> getElementMappings(final Class scope) {
        return (Map<QName, RuntimeElementInfoImpl>)super.getElementMappings(scope);
    }
    
    public Iterable<RuntimeElementInfoImpl> getAllElements() {
        return (Iterable<RuntimeElementInfoImpl>)super.getAllElements();
    }
}
