// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.lang.annotation.Annotation;
import com.esotericsoftware.kryo.NotNull;
import com.esotericsoftware.kryo.util.ObjectMap;
import java.util.List;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.kryo.util.Util;
import java.security.AccessControlException;
import java.lang.reflect.Modifier;
import com.esotericsoftware.kryo.util.IntArray;
import java.util.Collection;
import java.util.Collections;
import java.lang.reflect.Field;
import java.util.ArrayList;
import com.esotericsoftware.kryo.Kryo;
import java.util.Comparator;
import com.esotericsoftware.kryo.Serializer;

public class FieldSerializer<T> extends Serializer<T> implements Comparator<CachedField>
{
    final Kryo kryo;
    final Class type;
    private CachedField[] fields;
    Object access;
    private boolean fieldsCanBeNull;
    private boolean setFieldsAsAccessible;
    private boolean ignoreSyntheticFields;
    private boolean fixedFieldTypes;
    
    public FieldSerializer(final Kryo kryo, final Class type) {
        this.fields = new CachedField[0];
        this.fieldsCanBeNull = true;
        this.setFieldsAsAccessible = true;
        this.ignoreSyntheticFields = true;
        this.kryo = kryo;
        this.type = type;
        this.rebuildCachedFields();
    }
    
    private void rebuildCachedFields() {
        if (this.type.isInterface()) {
            this.fields = new CachedField[0];
            return;
        }
        final ArrayList<Field> allFields = new ArrayList<Field>();
        for (Class nextClass = this.type; nextClass != Object.class; nextClass = nextClass.getSuperclass()) {
            Collections.addAll(allFields, nextClass.getDeclaredFields());
        }
        final ObjectMap context = this.kryo.getContext();
        final IntArray useAsm = new IntArray();
        final ArrayList<Field> validFields = new ArrayList<Field>(allFields.size());
        for (int i = 0, n = allFields.size(); i < n; ++i) {
            final Field field = allFields.get(i);
            final int modifiers = field.getModifiers();
            if (!Modifier.isTransient(modifiers)) {
                if (!Modifier.isStatic(modifiers)) {
                    if (!field.isSynthetic() || !this.ignoreSyntheticFields) {
                        if (!field.isAccessible()) {
                            if (!this.setFieldsAsAccessible) {
                                continue;
                            }
                            try {
                                field.setAccessible(true);
                            }
                            catch (AccessControlException ex) {
                                continue;
                            }
                        }
                        final Optional optional = field.getAnnotation(Optional.class);
                        if (optional == null || context.containsKey(optional.value())) {
                            validFields.add(field);
                            useAsm.add((!Modifier.isFinal(modifiers) && Modifier.isPublic(modifiers) && Modifier.isPublic(field.getType().getModifiers())) ? 1 : 0);
                        }
                    }
                }
            }
        }
        if (!Util.isAndroid && Modifier.isPublic(this.type.getModifiers()) && useAsm.indexOf(1) != -1) {
            try {
                this.access = FieldAccess.get(this.type);
            }
            catch (RuntimeException ex2) {}
        }
        final ArrayList<CachedField> cachedFields = new ArrayList<CachedField>(validFields.size());
        for (int j = 0, n2 = validFields.size(); j < n2; ++j) {
            final Field field2 = validFields.get(j);
            int accessIndex = -1;
            if (this.access != null && useAsm.get(j) == 1) {
                accessIndex = ((FieldAccess)this.access).getIndex(field2.getName());
            }
            cachedFields.add(this.newCachedField(field2, cachedFields.size(), accessIndex));
        }
        Collections.sort(cachedFields, this);
        this.fields = cachedFields.toArray(new CachedField[cachedFields.size()]);
        this.initializeCachedFields();
    }
    
    protected void initializeCachedFields() {
    }
    
    private CachedField newCachedField(final Field field, final int fieldIndex, final int accessIndex) {
        final Class fieldClass = field.getType();
        CachedField cachedField;
        if (accessIndex != -1) {
            if (fieldClass.isPrimitive()) {
                if (fieldClass == Boolean.TYPE) {
                    cachedField = new BooleanField();
                }
                else if (fieldClass == Byte.TYPE) {
                    cachedField = new ByteField();
                }
                else if (fieldClass == Character.TYPE) {
                    cachedField = new CharField();
                }
                else if (fieldClass == Short.TYPE) {
                    cachedField = new ShortField();
                }
                else if (fieldClass == Integer.TYPE) {
                    cachedField = new IntField();
                }
                else if (fieldClass == Long.TYPE) {
                    cachedField = new LongField();
                }
                else if (fieldClass == Float.TYPE) {
                    cachedField = new FloatField();
                }
                else if (fieldClass == Double.TYPE) {
                    cachedField = new DoubleField();
                }
                else {
                    cachedField = new ObjectField();
                }
            }
            else if (fieldClass == String.class && (!this.kryo.getReferences() || !this.kryo.getReferenceResolver().useReferences(String.class))) {
                cachedField = new StringField();
            }
            else {
                cachedField = new ObjectField();
            }
        }
        else {
            cachedField = new ObjectField();
            ((ObjectField)cachedField).generics = Kryo.getGenerics(field.getGenericType());
        }
        cachedField.field = field;
        cachedField.accessIndex = accessIndex;
        cachedField.canBeNull = (this.fieldsCanBeNull && !fieldClass.isPrimitive() && !field.isAnnotationPresent(NotNull.class));
        if (this.kryo.isFinal(fieldClass) || this.fixedFieldTypes) {
            cachedField.valueClass = fieldClass;
        }
        return cachedField;
    }
    
    public int compare(final CachedField o1, final CachedField o2) {
        return o1.field.getName().compareTo(o2.field.getName());
    }
    
    public void setFieldsCanBeNull(final boolean fieldsCanBeNull) {
        this.fieldsCanBeNull = fieldsCanBeNull;
        this.rebuildCachedFields();
    }
    
    public void setFieldsAsAccessible(final boolean setFieldsAsAccessible) {
        this.setFieldsAsAccessible = setFieldsAsAccessible;
        this.rebuildCachedFields();
    }
    
    public void setIgnoreSyntheticFields(final boolean ignoreSyntheticFields) {
        this.ignoreSyntheticFields = ignoreSyntheticFields;
        this.rebuildCachedFields();
    }
    
    public void setFixedFieldTypes(final boolean fixedFieldTypes) {
        this.fixedFieldTypes = fixedFieldTypes;
        this.rebuildCachedFields();
    }
    
    public void write(final Kryo kryo, final Output output, final T object) {
        final CachedField[] fields = this.fields;
        for (int i = 0, n = fields.length; i < n; ++i) {
            fields[i].write(output, object);
        }
    }
    
    public T read(final Kryo kryo, final Input input, final Class<T> type) {
        final T object = this.create(kryo, input, type);
        kryo.reference(object);
        final CachedField[] fields = this.fields;
        for (int i = 0, n = fields.length; i < n; ++i) {
            fields[i].read(input, object);
        }
        return object;
    }
    
    protected T create(final Kryo kryo, final Input input, final Class<T> type) {
        return kryo.newInstance(type);
    }
    
    public CachedField getField(final String fieldName) {
        for (final CachedField cachedField : this.fields) {
            if (cachedField.field.getName().equals(fieldName)) {
                return cachedField;
            }
        }
        throw new IllegalArgumentException("Field \"" + fieldName + "\" not found on class: " + this.type.getName());
    }
    
    public void removeField(final String fieldName) {
        for (int i = 0; i < this.fields.length; ++i) {
            final CachedField cachedField = this.fields[i];
            if (cachedField.field.getName().equals(fieldName)) {
                final CachedField[] newFields = new CachedField[this.fields.length - 1];
                System.arraycopy(this.fields, 0, newFields, 0, i);
                System.arraycopy(this.fields, i + 1, newFields, i, newFields.length - i);
                this.fields = newFields;
                return;
            }
        }
        throw new IllegalArgumentException("Field \"" + fieldName + "\" not found on class: " + this.type.getName());
    }
    
    public CachedField[] getFields() {
        return this.fields;
    }
    
    public Class getType() {
        return this.type;
    }
    
    protected T createCopy(final Kryo kryo, final T original) {
        return kryo.newInstance(original.getClass());
    }
    
    public T copy(final Kryo kryo, final T original) {
        final T copy = this.createCopy(kryo, original);
        kryo.reference(copy);
        for (int i = 0, n = this.fields.length; i < n; ++i) {
            this.fields[i].copy(original, copy);
        }
        return copy;
    }
    
    public abstract class CachedField<X>
    {
        Field field;
        Class valueClass;
        Serializer serializer;
        boolean canBeNull;
        int accessIndex;
        
        public CachedField() {
            this.accessIndex = -1;
        }
        
        public void setClass(final Class valueClass) {
            this.valueClass = valueClass;
            this.serializer = null;
        }
        
        public void setClass(final Class valueClass, final Serializer serializer) {
            this.valueClass = valueClass;
            this.serializer = serializer;
        }
        
        public void setSerializer(final Serializer serializer) {
            this.serializer = serializer;
        }
        
        public void setCanBeNull(final boolean canBeNull) {
            this.canBeNull = canBeNull;
        }
        
        public Field getField() {
            return this.field;
        }
        
        public String toString() {
            return this.field.getName();
        }
        
        public abstract void write(final Output p0, final Object p1);
        
        public abstract void read(final Input p0, final Object p1);
        
        public abstract void copy(final Object p0, final Object p1);
    }
    
    abstract class AsmCachedField extends CachedField
    {
        FieldAccess access;
        
        AsmCachedField() {
            this.access = (FieldAccess)FieldSerializer.this.access;
        }
    }
    
    class IntField extends AsmCachedField
    {
        public void write(final Output output, final Object object) {
            if (Log.TRACE) {
                Log.trace("kryo", "Write field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Write int: " + this.access.getInt(object, this.accessIndex));
            }
            output.writeInt(this.access.getInt(object, this.accessIndex), false);
        }
        
        public void read(final Input input, final Object object) {
            if (Log.TRACE) {
                final int value = input.readInt(false);
                this.access.setInt(object, this.accessIndex, value);
                Log.trace("kryo", "Read field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Read int: " + value);
            }
            else {
                this.access.setInt(object, this.accessIndex, input.readInt(false));
            }
        }
        
        public void copy(final Object original, final Object copy) {
            this.access.setInt(copy, this.accessIndex, this.access.getInt(original, this.accessIndex));
        }
    }
    
    class FloatField extends AsmCachedField
    {
        public void write(final Output output, final Object object) {
            if (Log.TRACE) {
                Log.trace("kryo", "Write field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Write float: " + this.access.getFloat(object, this.accessIndex));
            }
            output.writeFloat(this.access.getFloat(object, this.accessIndex));
        }
        
        public void read(final Input input, final Object object) {
            if (Log.TRACE) {
                final float value = input.readFloat();
                this.access.setFloat(object, this.accessIndex, value);
                Log.trace("kryo", "Read field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Read float: " + value);
            }
            else {
                this.access.setFloat(object, this.accessIndex, input.readFloat());
            }
        }
        
        public void copy(final Object original, final Object copy) {
            this.access.setFloat(copy, this.accessIndex, this.access.getFloat(original, this.accessIndex));
        }
    }
    
    class ShortField extends AsmCachedField
    {
        public void write(final Output output, final Object object) {
            if (Log.TRACE) {
                Log.trace("kryo", "Write field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Write short: " + this.access.getShort(object, this.accessIndex));
            }
            output.writeShort(this.access.getShort(object, this.accessIndex));
        }
        
        public void read(final Input input, final Object object) {
            if (Log.TRACE) {
                final short value = input.readShort();
                this.access.setShort(object, this.accessIndex, value);
                Log.trace("kryo", "Read field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Read short: " + value);
            }
            else {
                this.access.setShort(object, this.accessIndex, input.readShort());
            }
        }
        
        public void copy(final Object original, final Object copy) {
            this.access.setShort(copy, this.accessIndex, this.access.getShort(original, this.accessIndex));
        }
    }
    
    class ByteField extends AsmCachedField
    {
        public void write(final Output output, final Object object) {
            if (Log.TRACE) {
                Log.trace("kryo", "Write field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Write byte: " + this.access.getByte(object, this.accessIndex));
            }
            output.writeByte(this.access.getByte(object, this.accessIndex));
        }
        
        public void read(final Input input, final Object object) {
            if (Log.TRACE) {
                final byte value = input.readByte();
                this.access.setByte(object, this.accessIndex, value);
                Log.trace("kryo", "Read field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Read byte: " + value);
            }
            else {
                this.access.setByte(object, this.accessIndex, input.readByte());
            }
        }
        
        public void copy(final Object original, final Object copy) {
            this.access.setByte(copy, this.accessIndex, this.access.getByte(original, this.accessIndex));
        }
    }
    
    class BooleanField extends AsmCachedField
    {
        public void write(final Output output, final Object object) {
            if (Log.TRACE) {
                Log.trace("kryo", "Write field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Write boolean: " + this.access.getBoolean(object, this.accessIndex));
            }
            output.writeBoolean(this.access.getBoolean(object, this.accessIndex));
        }
        
        public void read(final Input input, final Object object) {
            if (Log.TRACE) {
                final boolean value = input.readBoolean();
                this.access.setBoolean(object, this.accessIndex, value);
                Log.trace("kryo", "Read field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Read boolean: " + value);
            }
            else {
                this.access.setBoolean(object, this.accessIndex, input.readBoolean());
            }
        }
        
        public void copy(final Object original, final Object copy) {
            this.access.setBoolean(copy, this.accessIndex, this.access.getBoolean(original, this.accessIndex));
        }
    }
    
    class CharField extends AsmCachedField
    {
        public void write(final Output output, final Object object) {
            if (Log.TRACE) {
                Log.trace("kryo", "Write field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Write char: " + this.access.getChar(object, this.accessIndex));
            }
            output.writeChar(this.access.getChar(object, this.accessIndex));
        }
        
        public void read(final Input input, final Object object) {
            if (Log.TRACE) {
                final char value = input.readChar();
                this.access.setChar(object, this.accessIndex, value);
                Log.trace("kryo", "Read field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Read char: " + value);
            }
            else {
                this.access.setChar(object, this.accessIndex, input.readChar());
            }
        }
        
        public void copy(final Object original, final Object copy) {
            this.access.setChar(copy, this.accessIndex, this.access.getChar(original, this.accessIndex));
        }
    }
    
    class LongField extends AsmCachedField
    {
        public void write(final Output output, final Object object) {
            if (Log.TRACE) {
                Log.trace("kryo", "Write field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Write long: " + this.access.getLong(object, this.accessIndex));
            }
            output.writeLong(this.access.getLong(object, this.accessIndex), false);
        }
        
        public void read(final Input input, final Object object) {
            if (Log.TRACE) {
                final long value = input.readLong(false);
                this.access.setLong(object, this.accessIndex, value);
                Log.trace("kryo", "Read field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Read long: " + value);
            }
            else {
                this.access.setLong(object, this.accessIndex, input.readLong(false));
            }
        }
        
        public void copy(final Object original, final Object copy) {
            this.access.setLong(copy, this.accessIndex, this.access.getLong(original, this.accessIndex));
        }
    }
    
    class DoubleField extends AsmCachedField
    {
        public void write(final Output output, final Object object) {
            if (Log.TRACE) {
                Log.trace("kryo", "Write field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Write double: " + this.access.getDouble(object, this.accessIndex));
            }
            output.writeDouble(this.access.getDouble(object, this.accessIndex));
        }
        
        public void read(final Input input, final Object object) {
            if (Log.TRACE) {
                final double value = input.readDouble();
                this.access.setDouble(object, this.accessIndex, value);
                Log.trace("kryo", "Read field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Read double: " + value);
            }
            else {
                this.access.setDouble(object, this.accessIndex, input.readDouble());
            }
        }
        
        public void copy(final Object original, final Object copy) {
            this.access.setDouble(copy, this.accessIndex, this.access.getDouble(original, this.accessIndex));
        }
    }
    
    class StringField extends AsmCachedField
    {
        public void write(final Output output, final Object object) {
            if (Log.TRACE) {
                Log.trace("kryo", "Write field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Write string: " + this.access.getString(object, this.accessIndex));
            }
            output.writeString(this.access.getString(object, this.accessIndex));
        }
        
        public void read(final Input input, final Object object) {
            if (Log.TRACE) {
                final String value = input.readString();
                this.access.set(object, this.accessIndex, value);
                Log.trace("kryo", "Read field: " + this + " (" + object.getClass().getName() + ")");
                Log.trace("kryo", "Read string: " + value);
            }
            else {
                this.access.set(object, this.accessIndex, input.readString());
            }
        }
        
        public void copy(final Object original, final Object copy) {
            this.access.set(copy, this.accessIndex, this.access.getString(original, this.accessIndex));
        }
    }
    
    class ObjectField extends CachedField
    {
        Class[] generics;
        
        public void write(final Output output, final Object object) {
            try {
                if (Log.TRACE) {
                    Log.trace("kryo", "Write field: " + this + " (" + object.getClass().getName() + ")");
                }
                Object value;
                if (this.accessIndex != -1) {
                    value = ((FieldAccess)FieldSerializer.this.access).get(object, this.accessIndex);
                }
                else {
                    value = this.field.get(object);
                }
                Serializer serializer = this.serializer;
                if (this.valueClass == null) {
                    if (value == null) {
                        FieldSerializer.this.kryo.writeClass(output, null);
                        return;
                    }
                    final Registration registration = FieldSerializer.this.kryo.writeClass(output, value.getClass());
                    if (serializer == null) {
                        serializer = registration.getSerializer();
                    }
                    if (this.generics != null) {
                        serializer.setGenerics(FieldSerializer.this.kryo, this.generics);
                    }
                    FieldSerializer.this.kryo.writeObject(output, value, serializer);
                }
                else {
                    if (serializer == null) {
                        serializer = (this.serializer = FieldSerializer.this.kryo.getSerializer(this.valueClass));
                    }
                    if (this.generics != null) {
                        serializer.setGenerics(FieldSerializer.this.kryo, this.generics);
                    }
                    if (this.canBeNull) {
                        FieldSerializer.this.kryo.writeObjectOrNull(output, value, serializer);
                    }
                    else {
                        if (value == null) {
                            throw new KryoException("Field value is null but canBeNull is false: " + this + " (" + object.getClass().getName() + ")");
                        }
                        FieldSerializer.this.kryo.writeObject(output, value, serializer);
                    }
                }
            }
            catch (IllegalAccessException ex) {
                throw new KryoException("Error accessing field: " + this + " (" + object.getClass().getName() + ")", ex);
            }
            catch (KryoException ex2) {
                ex2.addTrace(this + " (" + object.getClass().getName() + ")");
                throw ex2;
            }
            catch (RuntimeException runtimeEx) {
                final KryoException ex3 = new KryoException(runtimeEx);
                ex3.addTrace(this + " (" + object.getClass().getName() + ")");
                throw ex3;
            }
        }
        
        public void read(final Input input, final Object object) {
            try {
                if (Log.TRACE) {
                    Log.trace("kryo", "Read field: " + this + " (" + FieldSerializer.this.type.getName() + ")");
                }
                final Class concreteType = this.valueClass;
                Serializer serializer = this.serializer;
                Object value;
                if (concreteType == null) {
                    final Registration registration = FieldSerializer.this.kryo.readClass(input);
                    if (registration == null) {
                        value = null;
                    }
                    else {
                        if (serializer == null) {
                            serializer = registration.getSerializer();
                        }
                        if (this.generics != null) {
                            serializer.setGenerics(FieldSerializer.this.kryo, this.generics);
                        }
                        value = FieldSerializer.this.kryo.readObject(input, (Class<Object>)registration.getType(), serializer);
                    }
                }
                else {
                    if (serializer == null) {
                        serializer = (this.serializer = FieldSerializer.this.kryo.getSerializer(this.valueClass));
                    }
                    if (this.generics != null) {
                        serializer.setGenerics(FieldSerializer.this.kryo, this.generics);
                    }
                    if (this.canBeNull) {
                        value = FieldSerializer.this.kryo.readObjectOrNull(input, (Class<Object>)concreteType, serializer);
                    }
                    else {
                        value = FieldSerializer.this.kryo.readObject(input, (Class<Object>)concreteType, serializer);
                    }
                }
                if (this.accessIndex != -1) {
                    ((FieldAccess)FieldSerializer.this.access).set(object, this.accessIndex, value);
                }
                else {
                    this.field.set(object, value);
                }
            }
            catch (IllegalAccessException ex) {
                throw new KryoException("Error accessing field: " + this + " (" + FieldSerializer.this.type.getName() + ")", ex);
            }
            catch (KryoException ex2) {
                ex2.addTrace(this + " (" + FieldSerializer.this.type.getName() + ")");
                throw ex2;
            }
            catch (RuntimeException runtimeEx) {
                final KryoException ex3 = new KryoException(runtimeEx);
                ex3.addTrace(this + " (" + FieldSerializer.this.type.getName() + ")");
                throw ex3;
            }
        }
        
        public void copy(final Object original, final Object copy) {
            try {
                if (this.accessIndex != -1) {
                    final FieldAccess access = (FieldAccess)FieldSerializer.this.access;
                    access.set(copy, this.accessIndex, FieldSerializer.this.kryo.copy(access.get(original, this.accessIndex)));
                }
                else {
                    this.field.set(copy, FieldSerializer.this.kryo.copy(this.field.get(original)));
                }
            }
            catch (IllegalAccessException ex) {
                throw new KryoException("Error accessing field: " + this + " (" + FieldSerializer.this.type.getName() + ")", ex);
            }
            catch (KryoException ex2) {
                ex2.addTrace(this + " (" + FieldSerializer.this.type.getName() + ")");
                throw ex2;
            }
            catch (RuntimeException runtimeEx) {
                final KryoException ex3 = new KryoException(runtimeEx);
                ex3.addTrace(this + " (" + FieldSerializer.this.type.getName() + ")");
                throw ex3;
            }
        }
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD })
    public @interface Optional {
        String value();
    }
}
