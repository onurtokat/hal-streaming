// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.io.Input;
import java.lang.reflect.InvocationTargetException;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.kryo.io.Output;
import java.lang.reflect.Method;
import java.beans.BeanInfo;
import com.esotericsoftware.reflectasm.MethodAccess;
import java.util.ArrayList;
import java.util.Arrays;
import java.beans.PropertyDescriptor;
import java.util.Comparator;
import java.beans.IntrospectionException;
import com.esotericsoftware.kryo.KryoException;
import java.beans.Introspector;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;

public class BeanSerializer<T> extends Serializer<T>
{
    static final Object[] noArgs;
    private final Kryo kryo;
    private CachedProperty[] properties;
    Object access;
    
    public BeanSerializer(final Kryo kryo, final Class type) {
        this.kryo = kryo;
        BeanInfo info;
        try {
            info = Introspector.getBeanInfo(type);
        }
        catch (IntrospectionException ex) {
            throw new KryoException("Error getting bean info.", ex);
        }
        final PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
        Arrays.sort(descriptors, new Comparator<PropertyDescriptor>() {
            public int compare(final PropertyDescriptor o1, final PropertyDescriptor o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        final ArrayList<CachedProperty> cachedProperties = new ArrayList<CachedProperty>(descriptors.length);
        for (int i = 0, n = descriptors.length; i < n; ++i) {
            final PropertyDescriptor property = descriptors[i];
            final String name = property.getName();
            if (!name.equals("class")) {
                final Method getMethod = property.getReadMethod();
                final Method setMethod = property.getWriteMethod();
                if (getMethod != null) {
                    if (setMethod != null) {
                        Serializer serializer = null;
                        final Class returnType = getMethod.getReturnType();
                        if (kryo.isFinal(returnType)) {
                            serializer = kryo.getRegistration(returnType).getSerializer();
                        }
                        final CachedProperty cachedProperty = new CachedProperty();
                        cachedProperty.name = name;
                        cachedProperty.getMethod = getMethod;
                        cachedProperty.setMethod = setMethod;
                        cachedProperty.serializer = serializer;
                        cachedProperty.setMethodType = setMethod.getParameterTypes()[0];
                        cachedProperties.add(cachedProperty);
                    }
                }
            }
        }
        this.properties = cachedProperties.toArray(new CachedProperty[cachedProperties.size()]);
        try {
            this.access = MethodAccess.get(type);
            for (int i = 0, n = this.properties.length; i < n; ++i) {
                final CachedProperty property2 = this.properties[i];
                property2.getterAccessIndex = ((MethodAccess)this.access).getIndex(property2.getMethod.getName());
                property2.setterAccessIndex = ((MethodAccess)this.access).getIndex(property2.setMethod.getName());
            }
        }
        catch (Throwable t) {}
    }
    
    public void write(final Kryo kryo, final Output output, final T object) {
        final Class type = object.getClass();
        for (int i = 0, n = this.properties.length; i < n; ++i) {
            final CachedProperty property = this.properties[i];
            try {
                if (Log.TRACE) {
                    Log.trace("kryo", "Write property: " + property + " (" + type.getName() + ")");
                }
                final Object value = property.get(object);
                final Serializer serializer = property.serializer;
                if (serializer != null) {
                    kryo.writeObjectOrNull(output, value, serializer);
                }
                else {
                    kryo.writeClassAndObject(output, value);
                }
            }
            catch (IllegalAccessException ex) {
                throw new KryoException("Error accessing getter method: " + property + " (" + type.getName() + ")", ex);
            }
            catch (InvocationTargetException ex2) {
                throw new KryoException("Error invoking getter method: " + property + " (" + type.getName() + ")", ex2);
            }
            catch (KryoException ex3) {
                ex3.addTrace(property + " (" + type.getName() + ")");
                throw ex3;
            }
            catch (RuntimeException runtimeEx) {
                final KryoException ex4 = new KryoException(runtimeEx);
                ex4.addTrace(property + " (" + type.getName() + ")");
                throw ex4;
            }
        }
    }
    
    public T read(final Kryo kryo, final Input input, final Class<T> type) {
        final T object = kryo.newInstance(type);
        kryo.reference(object);
        for (int i = 0, n = this.properties.length; i < n; ++i) {
            final CachedProperty property = this.properties[i];
            try {
                if (Log.TRACE) {
                    Log.trace("kryo", "Read property: " + property + " (" + object.getClass() + ")");
                }
                final Serializer serializer = property.serializer;
                Object value;
                if (serializer != null) {
                    value = kryo.readObjectOrNull(input, (Class<Object>)property.setMethodType, serializer);
                }
                else {
                    value = kryo.readClassAndObject(input);
                }
                property.set(object, value);
            }
            catch (IllegalAccessException ex) {
                throw new KryoException("Error accessing setter method: " + property + " (" + object.getClass().getName() + ")", ex);
            }
            catch (InvocationTargetException ex2) {
                throw new KryoException("Error invoking setter method: " + property + " (" + object.getClass().getName() + ")", ex2);
            }
            catch (KryoException ex3) {
                ex3.addTrace(property + " (" + object.getClass().getName() + ")");
                throw ex3;
            }
            catch (RuntimeException runtimeEx) {
                final KryoException ex4 = new KryoException(runtimeEx);
                ex4.addTrace(property + " (" + object.getClass().getName() + ")");
                throw ex4;
            }
        }
        return object;
    }
    
    public T copy(final Kryo kryo, final T original) {
        final T copy = kryo.newInstance(original.getClass());
        for (int i = 0, n = this.properties.length; i < n; ++i) {
            final CachedProperty property = this.properties[i];
            try {
                final Object value = property.get(original);
                property.set(copy, value);
            }
            catch (KryoException ex) {
                ex.addTrace(property + " (" + copy.getClass().getName() + ")");
                throw ex;
            }
            catch (RuntimeException runtimeEx) {
                final KryoException ex2 = new KryoException(runtimeEx);
                ex2.addTrace(property + " (" + copy.getClass().getName() + ")");
                throw ex2;
            }
            catch (Exception ex3) {
                throw new KryoException("Error copying bean property: " + property + " (" + copy.getClass().getName() + ")", ex3);
            }
        }
        return copy;
    }
    
    static {
        noArgs = new Object[0];
    }
    
    class CachedProperty<X>
    {
        String name;
        Method getMethod;
        Method setMethod;
        Class setMethodType;
        Serializer serializer;
        int getterAccessIndex;
        int setterAccessIndex;
        
        public String toString() {
            return this.name;
        }
        
        Object get(final Object object) throws IllegalAccessException, InvocationTargetException {
            if (BeanSerializer.this.access != null) {
                return ((MethodAccess)BeanSerializer.this.access).invoke(object, this.getterAccessIndex, new Object[0]);
            }
            return this.getMethod.invoke(object, BeanSerializer.noArgs);
        }
        
        void set(final Object object, final Object value) throws IllegalAccessException, InvocationTargetException {
            if (BeanSerializer.this.access != null) {
                ((MethodAccess)BeanSerializer.this.access).invoke(object, this.setterAccessIndex, value);
                return;
            }
            this.setMethod.invoke(object, value);
        }
    }
}
