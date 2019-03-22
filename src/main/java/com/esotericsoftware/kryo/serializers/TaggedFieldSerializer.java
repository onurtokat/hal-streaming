// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.lang.reflect.Field;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.kryo.Kryo;

public class TaggedFieldSerializer<T> extends FieldSerializer<T>
{
    private int[] tags;
    private int writeFieldCount;
    private boolean[] deprecated;
    
    public TaggedFieldSerializer(final Kryo kryo, final Class type) {
        super(kryo, type);
    }
    
    protected void initializeCachedFields() {
        CachedField[] fields = this.getFields();
        for (int i = 0, n = fields.length; i < n; ++i) {
            final Field field = fields[i].getField();
            if (field.getAnnotation(Tag.class) == null) {
                if (Log.TRACE) {
                    Log.trace("kryo", "Ignoring field without tag: " + fields[i]);
                }
                super.removeField(field.getName());
            }
        }
        fields = this.getFields();
        this.tags = new int[fields.length];
        this.deprecated = new boolean[fields.length];
        this.writeFieldCount = fields.length;
        for (int i = 0, n = fields.length; i < n; ++i) {
            final Field field = fields[i].getField();
            this.tags[i] = field.getAnnotation(Tag.class).value();
            if (field.getAnnotation(Deprecated.class) != null) {
                this.deprecated[i] = true;
                --this.writeFieldCount;
            }
        }
    }
    
    public void removeField(final String fieldName) {
        super.removeField(fieldName);
        this.initializeCachedFields();
    }
    
    public void write(final Kryo kryo, final Output output, final T object) {
        final CachedField[] fields = this.getFields();
        output.writeInt(this.writeFieldCount, true);
        for (int i = 0, n = fields.length; i < n; ++i) {
            if (!this.deprecated[i]) {
                output.writeInt(this.tags[i], true);
                fields[i].write(output, object);
            }
        }
    }
    
    public T read(final Kryo kryo, final Input input, final Class<T> type) {
        final T object = this.create(kryo, input, type);
        kryo.reference(object);
        final int fieldCount = input.readInt(true);
        final int[] tags = this.tags;
        final CachedField[] fields = this.getFields();
        for (int i = 0, n = fieldCount; i < n; ++i) {
            final int tag = input.readInt(true);
            CachedField cachedField = null;
            for (int ii = 0, nn = tags.length; ii < nn; ++ii) {
                if (tags[ii] == tag) {
                    cachedField = fields[ii];
                    break;
                }
            }
            if (cachedField == null) {
                throw new KryoException("Unknown field tag: " + tag + " (" + this.getType().getName() + ")");
            }
            cachedField.read(input, object);
        }
        return object;
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD })
    public @interface Tag {
        int value();
    }
}
