// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.Constructor;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import java.lang.reflect.Modifier;
import org.objenesis.instantiator.ObjectInstantiator;
import java.util.ConcurrentModificationException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.kryo.io.Output;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import com.esotericsoftware.kryo.util.Util;
import java.util.Calendar;
import java.util.TimeZone;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import java.util.Map;
import java.util.TreeMap;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.EnumSet;
import java.util.Date;
import java.math.BigDecimal;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import java.math.BigInteger;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.esotericsoftware.kryo.util.MapReferenceResolver;
import com.esotericsoftware.kryo.util.DefaultClassResolver;
import com.esotericsoftware.kryo.util.IdentityMap;
import com.esotericsoftware.kryo.util.IntArray;
import com.esotericsoftware.kryo.util.ObjectMap;
import org.objenesis.strategy.InstantiatorStrategy;
import java.util.ArrayList;

public class Kryo
{
    public static final byte NULL = 0;
    public static final byte NOT_NULL = 1;
    private static final int REF = -1;
    private static final int NO_REF = -2;
    private Class<? extends Serializer> defaultSerializer;
    private final ArrayList<DefaultSerializerEntry> defaultSerializers;
    private final int lowPriorityDefaultSerializerCount;
    private final ClassResolver classResolver;
    private int nextRegisterID;
    private ClassLoader classLoader;
    private InstantiatorStrategy strategy;
    private boolean registrationRequired;
    private int depth;
    private int maxDepth;
    private boolean autoReset;
    private volatile Thread thread;
    private ObjectMap context;
    private ObjectMap graphContext;
    private ReferenceResolver referenceResolver;
    private final IntArray readReferenceIds;
    private boolean references;
    private Object readObject;
    private int copyDepth;
    private boolean copyShallow;
    private IdentityMap originalToCopy;
    private Object needsCopyReference;
    
    public Kryo() {
        this(new DefaultClassResolver(), new MapReferenceResolver());
    }
    
    public Kryo(final ReferenceResolver referenceResolver) {
        this(new DefaultClassResolver(), referenceResolver);
    }
    
    public Kryo(final ClassResolver classResolver, final ReferenceResolver referenceResolver) {
        this.defaultSerializer = FieldSerializer.class;
        this.defaultSerializers = new ArrayList<DefaultSerializerEntry>(32);
        this.classLoader = this.getClass().getClassLoader();
        this.maxDepth = Integer.MAX_VALUE;
        this.autoReset = true;
        this.readReferenceIds = new IntArray(0);
        if (classResolver == null) {
            throw new IllegalArgumentException("classResolver cannot be null.");
        }
        (this.classResolver = classResolver).setKryo(this);
        if ((this.referenceResolver = referenceResolver) != null) {
            referenceResolver.setKryo(this);
            this.references = true;
        }
        this.addDefaultSerializer(byte[].class, DefaultArraySerializers.ByteArraySerializer.class);
        this.addDefaultSerializer(char[].class, DefaultArraySerializers.CharArraySerializer.class);
        this.addDefaultSerializer(short[].class, DefaultArraySerializers.ShortArraySerializer.class);
        this.addDefaultSerializer(int[].class, DefaultArraySerializers.IntArraySerializer.class);
        this.addDefaultSerializer(long[].class, DefaultArraySerializers.LongArraySerializer.class);
        this.addDefaultSerializer(float[].class, DefaultArraySerializers.FloatArraySerializer.class);
        this.addDefaultSerializer(double[].class, DefaultArraySerializers.DoubleArraySerializer.class);
        this.addDefaultSerializer(boolean[].class, DefaultArraySerializers.BooleanArraySerializer.class);
        this.addDefaultSerializer(String[].class, DefaultArraySerializers.StringArraySerializer.class);
        this.addDefaultSerializer(Object[].class, DefaultArraySerializers.ObjectArraySerializer.class);
        this.addDefaultSerializer(BigInteger.class, DefaultSerializers.BigIntegerSerializer.class);
        this.addDefaultSerializer(BigDecimal.class, DefaultSerializers.BigDecimalSerializer.class);
        this.addDefaultSerializer(Class.class, DefaultSerializers.ClassSerializer.class);
        this.addDefaultSerializer(Date.class, DefaultSerializers.DateSerializer.class);
        this.addDefaultSerializer(Enum.class, DefaultSerializers.EnumSerializer.class);
        this.addDefaultSerializer(EnumSet.class, DefaultSerializers.EnumSetSerializer.class);
        this.addDefaultSerializer(Currency.class, DefaultSerializers.CurrencySerializer.class);
        this.addDefaultSerializer(StringBuffer.class, DefaultSerializers.StringBufferSerializer.class);
        this.addDefaultSerializer(StringBuilder.class, DefaultSerializers.StringBuilderSerializer.class);
        this.addDefaultSerializer(Collections.EMPTY_LIST.getClass(), DefaultSerializers.CollectionsEmptyListSerializer.class);
        this.addDefaultSerializer(Collections.EMPTY_MAP.getClass(), DefaultSerializers.CollectionsEmptyMapSerializer.class);
        this.addDefaultSerializer(Collections.EMPTY_SET.getClass(), DefaultSerializers.CollectionsEmptySetSerializer.class);
        this.addDefaultSerializer(Collections.singletonList((Object)null).getClass(), DefaultSerializers.CollectionsSingletonListSerializer.class);
        this.addDefaultSerializer(Collections.singletonMap((Object)null, (Object)null).getClass(), DefaultSerializers.CollectionsSingletonMapSerializer.class);
        this.addDefaultSerializer(Collections.singleton((Object)null).getClass(), DefaultSerializers.CollectionsSingletonSetSerializer.class);
        this.addDefaultSerializer(Collection.class, CollectionSerializer.class);
        this.addDefaultSerializer(TreeMap.class, DefaultSerializers.TreeMapSerializer.class);
        this.addDefaultSerializer(Map.class, MapSerializer.class);
        this.addDefaultSerializer(KryoSerializable.class, DefaultSerializers.KryoSerializableSerializer.class);
        this.addDefaultSerializer(TimeZone.class, DefaultSerializers.TimeZoneSerializer.class);
        this.addDefaultSerializer(Calendar.class, DefaultSerializers.CalendarSerializer.class);
        this.lowPriorityDefaultSerializerCount = this.defaultSerializers.size();
        this.register(Integer.TYPE, new DefaultSerializers.IntSerializer());
        this.register(String.class, new DefaultSerializers.StringSerializer());
        this.register(Float.TYPE, new DefaultSerializers.FloatSerializer());
        this.register(Boolean.TYPE, new DefaultSerializers.BooleanSerializer());
        this.register(Byte.TYPE, new DefaultSerializers.ByteSerializer());
        this.register(Character.TYPE, new DefaultSerializers.CharSerializer());
        this.register(Short.TYPE, new DefaultSerializers.ShortSerializer());
        this.register(Long.TYPE, new DefaultSerializers.LongSerializer());
        this.register(Double.TYPE, new DefaultSerializers.DoubleSerializer());
    }
    
    public void setDefaultSerializer(final Class<? extends Serializer> serializer) {
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.defaultSerializer = serializer;
    }
    
    public void addDefaultSerializer(final Class type, final Serializer serializer) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        final DefaultSerializerEntry entry = new DefaultSerializerEntry();
        entry.type = type;
        entry.serializer = serializer;
        this.defaultSerializers.add(this.defaultSerializers.size() - this.lowPriorityDefaultSerializerCount, entry);
    }
    
    public void addDefaultSerializer(final Class type, final Class<? extends Serializer> serializerClass) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (serializerClass == null) {
            throw new IllegalArgumentException("serializerClass cannot be null.");
        }
        final DefaultSerializerEntry entry = new DefaultSerializerEntry();
        entry.type = type;
        entry.serializerClass = serializerClass;
        this.defaultSerializers.add(this.defaultSerializers.size() - this.lowPriorityDefaultSerializerCount, entry);
    }
    
    public Serializer getDefaultSerializer(final Class type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (type.isAnnotationPresent(DefaultSerializer.class)) {
            return this.newSerializer(type.getAnnotation(DefaultSerializer.class).value(), type);
        }
        int i = 0;
        final int n = this.defaultSerializers.size();
        while (i < n) {
            final DefaultSerializerEntry entry = this.defaultSerializers.get(i);
            if (entry.type.isAssignableFrom(type)) {
                if (entry.serializer != null) {
                    return entry.serializer;
                }
                return this.newSerializer(entry.serializerClass, type);
            }
            else {
                ++i;
            }
        }
        return this.newDefaultSerializer(type);
    }
    
    protected Serializer newDefaultSerializer(final Class type) {
        return this.newSerializer(this.defaultSerializer, type);
    }
    
    public Serializer newSerializer(final Class<? extends Serializer> serializerClass, final Class type) {
        try {
            try {
                return (Serializer)serializerClass.getConstructor(Kryo.class, Class.class).newInstance(this, type);
            }
            catch (NoSuchMethodException ex2) {
                try {
                    return (Serializer)serializerClass.getConstructor(Kryo.class).newInstance(this);
                }
                catch (NoSuchMethodException ex3) {
                    try {
                        return (Serializer)serializerClass.getConstructor(Class.class).newInstance(type);
                    }
                    catch (NoSuchMethodException ex4) {
                        return (Serializer)serializerClass.newInstance();
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Unable to create serializer \"" + serializerClass.getName() + "\" for class: " + Util.className(type), ex);
        }
    }
    
    public Registration register(final Class type) {
        final Registration registration = this.classResolver.getRegistration(type);
        if (registration != null) {
            return registration;
        }
        return this.register(type, this.getDefaultSerializer(type));
    }
    
    public Registration register(final Class type, final int id) {
        final Registration registration = this.classResolver.getRegistration(type);
        if (registration != null) {
            return registration;
        }
        return this.register(type, this.getDefaultSerializer(type), id);
    }
    
    public Registration register(final Class type, final Serializer serializer) {
        final Registration registration = this.classResolver.getRegistration(type);
        if (registration != null) {
            registration.setSerializer(serializer);
            return registration;
        }
        return this.classResolver.register(new Registration(type, serializer, this.getNextRegistrationId()));
    }
    
    public Registration register(final Class type, final Serializer serializer, final int id) {
        if (id < 0) {
            throw new IllegalArgumentException("id must be >= 0: " + id);
        }
        return this.register(new Registration(type, serializer, id));
    }
    
    public Registration register(final Registration registration) {
        final int id = registration.getId();
        if (id < 0) {
            throw new IllegalArgumentException("id must be > 0: " + id);
        }
        final Registration existing = this.getRegistration(registration.getId());
        if (existing != null && existing.getType() != registration.getType()) {
            throw new KryoException("An existing registration with a different type already uses ID: " + registration.getId() + "\nExisting registration: " + existing + "\nUnable to set registration: " + registration);
        }
        return this.classResolver.register(registration);
    }
    
    public int getNextRegistrationId() {
        while (this.nextRegisterID != -2) {
            if (this.classResolver.getRegistration(this.nextRegisterID) == null) {
                return this.nextRegisterID;
            }
            ++this.nextRegisterID;
        }
        throw new KryoException("No registration IDs are available.");
    }
    
    public Registration getRegistration(final Class type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        Registration registration = this.classResolver.getRegistration(type);
        if (registration == null) {
            if (Proxy.isProxyClass(type)) {
                registration = this.getRegistration(InvocationHandler.class);
            }
            else if (!type.isEnum() && Enum.class.isAssignableFrom(type)) {
                registration = this.getRegistration(type.getEnclosingClass());
            }
            else if (EnumSet.class.isAssignableFrom(type)) {
                registration = this.classResolver.getRegistration(EnumSet.class);
            }
            if (registration == null) {
                if (this.registrationRequired) {
                    throw new IllegalArgumentException("Class is not registered: " + Util.className(type) + "\nNote: To register this class use: kryo.register(" + Util.className(type) + ".class);");
                }
                registration = this.classResolver.registerImplicit(type);
            }
        }
        return registration;
    }
    
    public Registration getRegistration(final int classID) {
        return this.classResolver.getRegistration(classID);
    }
    
    public Serializer getSerializer(final Class type) {
        return this.getRegistration(type).getSerializer();
    }
    
    public Registration writeClass(final Output output, final Class type) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        try {
            return this.classResolver.writeClass(output, type);
        }
        finally {
            if (this.depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    public void writeObject(final Output output, final Object object) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }
        this.beginObject();
        try {
            if (this.references && this.writeReferenceOrNull(output, object, false)) {
                return;
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", object);
            }
            this.getRegistration(object.getClass()).getSerializer().write(this, output, object);
        }
        finally {
            final int depth = this.depth - 1;
            this.depth = depth;
            if (depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    public void writeObject(final Output output, final Object object, final Serializer serializer) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.beginObject();
        try {
            if (this.references && this.writeReferenceOrNull(output, object, false)) {
                return;
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", object);
            }
            serializer.write(this, output, object);
        }
        finally {
            final int depth = this.depth - 1;
            this.depth = depth;
            if (depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    public void writeObjectOrNull(final Output output, final Object object, final Class type) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        this.beginObject();
        try {
            final Serializer serializer = this.getRegistration(type).getSerializer();
            if (this.references) {
                if (this.writeReferenceOrNull(output, object, true)) {
                    return;
                }
            }
            else if (!serializer.getAcceptsNull()) {
                if (object == null) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Write", object);
                    }
                    output.writeByte((byte)0);
                    return;
                }
                output.writeByte((byte)1);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", object);
            }
            serializer.write(this, output, object);
        }
        finally {
            final int depth = this.depth - 1;
            this.depth = depth;
            if (depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    public void writeObjectOrNull(final Output output, final Object object, final Serializer serializer) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.beginObject();
        try {
            if (this.references) {
                if (this.writeReferenceOrNull(output, object, true)) {
                    return;
                }
            }
            else if (!serializer.getAcceptsNull()) {
                if (object == null) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Write", null);
                    }
                    output.writeByte((byte)0);
                    return;
                }
                output.writeByte((byte)1);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", object);
            }
            serializer.write(this, output, object);
        }
        finally {
            final int depth = this.depth - 1;
            this.depth = depth;
            if (depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    public void writeClassAndObject(final Output output, final Object object) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        }
        this.beginObject();
        try {
            if (object == null) {
                this.writeClass(output, null);
                return;
            }
            final Registration registration = this.writeClass(output, object.getClass());
            if (this.references && this.writeReferenceOrNull(output, object, false)) {
                return;
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", object);
            }
            registration.getSerializer().write(this, output, object);
        }
        finally {
            final int depth = this.depth - 1;
            this.depth = depth;
            if (depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    boolean writeReferenceOrNull(final Output output, final Object object, final boolean mayBeNull) {
        if (object == null) {
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", null);
            }
            output.writeByte((byte)0);
            return true;
        }
        if (!this.referenceResolver.useReferences(object.getClass())) {
            if (mayBeNull) {
                output.writeByte((byte)1);
            }
            return false;
        }
        int id = this.referenceResolver.getWrittenId(object);
        if (id != -1) {
            if (Log.DEBUG) {
                Log.debug("kryo", "Write object reference " + id + ": " + Util.string(object));
            }
            output.writeInt(id + 2, true);
            return true;
        }
        id = this.referenceResolver.addWrittenObject(object);
        output.writeByte((byte)1);
        if (Log.TRACE) {
            Log.trace("kryo", "Write initial object reference " + id + ": " + Util.string(object));
        }
        return false;
    }
    
    public Registration readClass(final Input input) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        try {
            return this.classResolver.readClass(input);
        }
        finally {
            if (this.depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    public <T> T readObject(final Input input, final Class<T> type) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        this.beginObject();
        try {
            T object;
            if (this.references) {
                final int stackSize = this.readReferenceOrNull(input, type, false);
                if (stackSize == -1) {
                    return (T)this.readObject;
                }
                object = this.getRegistration(type).getSerializer().read(this, input, type);
                if (stackSize == this.readReferenceIds.size) {
                    this.reference(object);
                }
            }
            else {
                object = this.getRegistration(type).getSerializer().read(this, input, type);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Read", object);
            }
            return object;
        }
        finally {
            final int depth = this.depth - 1;
            this.depth = depth;
            if (depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    public <T> T readObject(final Input input, final Class<T> type, final Serializer serializer) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.beginObject();
        try {
            T object;
            if (this.references) {
                final int stackSize = this.readReferenceOrNull(input, type, false);
                if (stackSize == -1) {
                    return (T)this.readObject;
                }
                object = serializer.read(this, input, type);
                if (stackSize == this.readReferenceIds.size) {
                    this.reference(object);
                }
            }
            else {
                object = serializer.read(this, input, type);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Read", object);
            }
            return object;
        }
        finally {
            final int depth = this.depth - 1;
            this.depth = depth;
            if (depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    public <T> T readObjectOrNull(final Input input, final Class<T> type) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        this.beginObject();
        try {
            T object;
            if (this.references) {
                final int stackSize = this.readReferenceOrNull(input, type, true);
                if (stackSize == -1) {
                    return (T)this.readObject;
                }
                object = this.getRegistration(type).getSerializer().read(this, input, type);
                if (stackSize == this.readReferenceIds.size) {
                    this.reference(object);
                }
            }
            else {
                final Serializer serializer = this.getRegistration(type).getSerializer();
                if (!serializer.getAcceptsNull() && input.readByte() == 0) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Read", null);
                    }
                    return null;
                }
                object = serializer.read(this, input, type);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Read", object);
            }
            return object;
        }
        finally {
            final int depth = this.depth - 1;
            this.depth = depth;
            if (depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    public <T> T readObjectOrNull(final Input input, final Class<T> type, final Serializer serializer) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.beginObject();
        try {
            T object;
            if (this.references) {
                final int stackSize = this.readReferenceOrNull(input, type, true);
                if (stackSize == -1) {
                    return (T)this.readObject;
                }
                object = serializer.read(this, input, type);
                if (stackSize == this.readReferenceIds.size) {
                    this.reference(object);
                }
            }
            else {
                if (!serializer.getAcceptsNull() && input.readByte() == 0) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Read", null);
                    }
                    return null;
                }
                object = serializer.read(this, input, type);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Read", object);
            }
            return object;
        }
        finally {
            final int depth = this.depth - 1;
            this.depth = depth;
            if (depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    public Object readClassAndObject(final Input input) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        this.beginObject();
        try {
            final Registration registration = this.readClass(input);
            if (registration == null) {
                return null;
            }
            final Class type = registration.getType();
            Object object;
            if (this.references) {
                final int stackSize = this.readReferenceOrNull(input, type, false);
                if (stackSize == -1) {
                    return this.readObject;
                }
                object = registration.getSerializer().read(this, input, type);
                if (stackSize == this.readReferenceIds.size) {
                    this.reference(object);
                }
            }
            else {
                object = registration.getSerializer().read(this, input, type);
            }
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Read", object);
            }
            return object;
        }
        finally {
            final int depth = this.depth - 1;
            this.depth = depth;
            if (depth == 0 && this.autoReset) {
                this.reset();
            }
        }
    }
    
    int readReferenceOrNull(final Input input, Class type, final boolean mayBeNull) {
        if (type.isPrimitive()) {
            type = Util.getWrapperClass(type);
        }
        final boolean referencesSupported = this.referenceResolver.useReferences(type);
        int id;
        if (mayBeNull) {
            id = input.readInt(true);
            if (id == 0) {
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log("Read", null);
                }
                this.readObject = null;
                return -1;
            }
            if (!referencesSupported) {
                this.readReferenceIds.add(-2);
                return this.readReferenceIds.size;
            }
        }
        else {
            if (!referencesSupported) {
                this.readReferenceIds.add(-2);
                return this.readReferenceIds.size;
            }
            id = input.readInt(true);
        }
        if (id == 1) {
            id = this.referenceResolver.nextReadId(type);
            if (Log.TRACE) {
                Log.trace("kryo", "Read initial object reference " + id + ": " + Util.className(type));
            }
            this.readReferenceIds.add(id);
            return this.readReferenceIds.size;
        }
        id -= 2;
        this.readObject = this.referenceResolver.getReadObject(type, id);
        if (Log.DEBUG) {
            Log.debug("kryo", "Read object reference " + id + ": " + Util.string(this.readObject));
        }
        return -1;
    }
    
    public void reference(final Object object) {
        if (this.copyDepth > 0) {
            if (this.needsCopyReference != null) {
                if (object == null) {
                    throw new IllegalArgumentException("object cannot be null.");
                }
                this.originalToCopy.put(this.needsCopyReference, object);
                this.needsCopyReference = null;
            }
        }
        else if (this.references && object != null) {
            final int id = this.readReferenceIds.pop();
            if (id != -2) {
                this.referenceResolver.setReadObject(id, object);
            }
        }
    }
    
    public void reset() {
        this.depth = 0;
        if (this.graphContext != null) {
            this.graphContext.clear();
        }
        this.classResolver.reset();
        if (this.references) {
            this.referenceResolver.reset();
            this.readObject = null;
        }
        this.copyDepth = 0;
        if (this.originalToCopy != null) {
            this.originalToCopy.clear();
        }
        if (Log.TRACE) {
            Log.trace("kryo", "Object graph complete.");
        }
    }
    
    public <T> T copy(final T object) {
        if (object == null) {
            return null;
        }
        if (this.copyShallow) {
            return object;
        }
        ++this.copyDepth;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            final Object existingCopy = this.originalToCopy.get(object);
            if (existingCopy != null) {
                return (T)existingCopy;
            }
            this.needsCopyReference = object;
            Object copy;
            if (object instanceof KryoCopyable) {
                copy = ((KryoCopyable)object).copy(this);
            }
            else {
                copy = this.getSerializer(object.getClass()).copy(this, object);
            }
            if (this.needsCopyReference != null) {
                this.reference(copy);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Copy", copy);
            }
            return (T)copy;
        }
        finally {
            final int copyDepth = this.copyDepth - 1;
            this.copyDepth = copyDepth;
            if (copyDepth == 0) {
                this.reset();
            }
        }
    }
    
    public <T> T copy(final T object, final Serializer serializer) {
        if (object == null) {
            return null;
        }
        if (this.copyShallow) {
            return object;
        }
        ++this.copyDepth;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            final Object existingCopy = this.originalToCopy.get(object);
            if (existingCopy != null) {
                return (T)existingCopy;
            }
            this.needsCopyReference = object;
            Object copy;
            if (object instanceof KryoCopyable) {
                copy = ((KryoCopyable)object).copy(this);
            }
            else {
                copy = serializer.copy(this, object);
            }
            if (this.needsCopyReference != null) {
                this.reference(copy);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Copy", copy);
            }
            return (T)copy;
        }
        finally {
            final int copyDepth = this.copyDepth - 1;
            this.copyDepth = copyDepth;
            if (copyDepth == 0) {
                this.reset();
            }
        }
    }
    
    public <T> T copyShallow(final T object) {
        if (object == null) {
            return null;
        }
        ++this.copyDepth;
        this.copyShallow = true;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            final Object existingCopy = this.originalToCopy.get(object);
            if (existingCopy != null) {
                return (T)existingCopy;
            }
            this.needsCopyReference = object;
            Object copy;
            if (object instanceof KryoCopyable) {
                copy = ((KryoCopyable)object).copy(this);
            }
            else {
                copy = this.getSerializer(object.getClass()).copy(this, object);
            }
            if (this.needsCopyReference != null) {
                this.reference(copy);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Shallow copy", copy);
            }
            return (T)copy;
        }
        finally {
            this.copyShallow = false;
            final int copyDepth = this.copyDepth - 1;
            this.copyDepth = copyDepth;
            if (copyDepth == 0) {
                this.reset();
            }
        }
    }
    
    public <T> T copyShallow(final T object, final Serializer serializer) {
        if (object == null) {
            return null;
        }
        ++this.copyDepth;
        this.copyShallow = true;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            final Object existingCopy = this.originalToCopy.get(object);
            if (existingCopy != null) {
                return (T)existingCopy;
            }
            this.needsCopyReference = object;
            Object copy;
            if (object instanceof KryoCopyable) {
                copy = ((KryoCopyable)object).copy(this);
            }
            else {
                copy = serializer.copy(this, object);
            }
            if (this.needsCopyReference != null) {
                this.reference(copy);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Shallow copy", copy);
            }
            return (T)copy;
        }
        finally {
            this.copyShallow = false;
            final int copyDepth = this.copyDepth - 1;
            this.copyDepth = copyDepth;
            if (copyDepth == 0) {
                this.reset();
            }
        }
    }
    
    private void beginObject() {
        if (Log.DEBUG) {
            if (this.depth == 0) {
                this.thread = Thread.currentThread();
            }
            else if (this.thread != Thread.currentThread()) {
                throw new ConcurrentModificationException("Kryo must not be accessed concurrently by multiple threads.");
            }
        }
        if (this.depth == this.maxDepth) {
            throw new KryoException("Max depth exceeded: " + this.depth);
        }
        ++this.depth;
    }
    
    public ClassResolver getClassResolver() {
        return this.classResolver;
    }
    
    public ReferenceResolver getReferenceResolver() {
        return this.referenceResolver;
    }
    
    public void setClassLoader(final ClassLoader classLoader) {
        if (classLoader == null) {
            throw new IllegalArgumentException("classLoader cannot be null.");
        }
        this.classLoader = classLoader;
    }
    
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }
    
    public void setRegistrationRequired(final boolean registrationRequired) {
        this.registrationRequired = registrationRequired;
        if (Log.TRACE) {
            Log.trace("kryo", "Registration required: " + registrationRequired);
        }
    }
    
    public boolean isRegistrationRequired() {
        return this.registrationRequired;
    }
    
    public boolean setReferences(final boolean references) {
        if (references == this.references) {
            return references;
        }
        this.references = references;
        if (references && this.referenceResolver == null) {
            this.referenceResolver = new MapReferenceResolver();
        }
        if (Log.TRACE) {
            Log.trace("kryo", "References: " + references);
        }
        return !references;
    }
    
    public void setReferenceResolver(final ReferenceResolver referenceResolver) {
        if (referenceResolver == null) {
            throw new IllegalArgumentException("referenceResolver cannot be null.");
        }
        this.references = true;
        this.referenceResolver = referenceResolver;
        if (Log.TRACE) {
            Log.trace("kryo", "Reference resolver: " + referenceResolver.getClass().getName());
        }
    }
    
    public boolean getReferences() {
        return this.references;
    }
    
    public void setInstantiatorStrategy(final InstantiatorStrategy strategy) {
        this.strategy = strategy;
    }
    
    protected ObjectInstantiator newInstantiator(final Class type) {
        Label_0062: {
            if (Util.isAndroid) {
                break Label_0062;
            }
            final Class enclosingType = type.getEnclosingClass();
            final boolean isNonStaticMemberClass = enclosingType != null && type.isMemberClass() && !Modifier.isStatic(type.getModifiers());
            if (isNonStaticMemberClass) {
                break Label_0062;
            }
            try {
                final ConstructorAccess access = ConstructorAccess.get((Class<Object>)type);
                return new ObjectInstantiator() {
                    public Object newInstance() {
                        try {
                            return access.newInstance();
                        }
                        catch (Exception ex) {
                            throw new KryoException("Error constructing instance of class: " + Util.className(type), ex);
                        }
                    }
                };
            }
            catch (Exception ex2) {}
            try {
                Constructor ctor;
                try {
                    ctor = type.getConstructor((Class<?>[])null);
                }
                catch (Exception ex) {
                    ctor = type.getDeclaredConstructor((Class<?>[])null);
                    ctor.setAccessible(true);
                }
                final Constructor constructor = ctor;
                return new ObjectInstantiator() {
                    public Object newInstance() {
                        try {
                            return constructor.newInstance(new Object[0]);
                        }
                        catch (Exception ex) {
                            throw new KryoException("Error constructing instance of class: " + Util.className(type), ex);
                        }
                    }
                };
            }
            catch (Exception ignored) {
                if (this.strategy != null) {
                    return this.strategy.newInstantiatorOf(type);
                }
                if (type.isMemberClass() && !Modifier.isStatic(type.getModifiers())) {
                    throw new KryoException("Class cannot be created (non-static member class): " + Util.className(type));
                }
                throw new KryoException("Class cannot be created (missing no-arg constructor): " + Util.className(type));
            }
        }
    }
    
    public <T> T newInstance(final Class<T> type) {
        final Registration registration = this.getRegistration(type);
        ObjectInstantiator instantiator = registration.getInstantiator();
        if (instantiator == null) {
            instantiator = this.newInstantiator(type);
            registration.setInstantiator(instantiator);
        }
        return (T)instantiator.newInstance();
    }
    
    public ObjectMap getContext() {
        if (this.context == null) {
            this.context = new ObjectMap();
        }
        return this.context;
    }
    
    public ObjectMap getGraphContext() {
        if (this.graphContext == null) {
            this.graphContext = new ObjectMap();
        }
        return this.graphContext;
    }
    
    public int getDepth() {
        return this.depth;
    }
    
    public void setAutoReset(final boolean autoReset) {
        this.autoReset = autoReset;
    }
    
    public void setMaxDepth(final int maxDepth) {
        if (maxDepth <= 0) {
            throw new IllegalArgumentException("maxDepth must be > 0.");
        }
        this.maxDepth = maxDepth;
    }
    
    public boolean isFinal(final Class type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (type.isArray()) {
            return Modifier.isFinal(Util.getElementClass(type).getModifiers());
        }
        return Modifier.isFinal(type.getModifiers());
    }
    
    public static Class[] getGenerics(final Type genericType) {
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        final Type[] actualTypes = ((ParameterizedType)genericType).getActualTypeArguments();
        final Class[] generics = new Class[actualTypes.length];
        int count = 0;
        for (int i = 0, n = actualTypes.length; i < n; ++i) {
            final Type actualType = actualTypes[i];
            if (actualType instanceof Class) {
                generics[i] = (Class)actualType;
            }
            else {
                if (!(actualType instanceof ParameterizedType)) {
                    continue;
                }
                generics[i] = (Class)((ParameterizedType)actualType).getRawType();
            }
            ++count;
        }
        if (count == 0) {
            return null;
        }
        return generics;
    }
    
    static final class DefaultSerializerEntry
    {
        Class type;
        Serializer serializer;
        Class<? extends Serializer> serializerClass;
    }
}
