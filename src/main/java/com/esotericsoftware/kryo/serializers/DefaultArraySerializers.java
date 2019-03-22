// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;

public class DefaultArraySerializers
{
    public static class ByteArraySerializer extends Serializer<byte[]>
    {
        public ByteArraySerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final byte[] object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.length + 1, true);
            output.writeBytes(object);
        }
        
        public byte[] read(final Kryo kryo, final Input input, final Class<byte[]> type) {
            final int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            return input.readBytes(length - 1);
        }
        
        public byte[] copy(final Kryo kryo, final byte[] original) {
            final byte[] copy = new byte[original.length];
            System.arraycopy(original, 0, copy, 0, copy.length);
            return copy;
        }
    }
    
    public static class IntArraySerializer extends Serializer<int[]>
    {
        public IntArraySerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final int[] object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.length + 1, true);
            for (int i = 0, n = object.length; i < n; ++i) {
                output.writeInt(object[i], false);
            }
        }
        
        public int[] read(final Kryo kryo, final Input input, final Class<int[]> type) {
            int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            final int[] array = new int[--length];
            for (int i = 0; i < length; ++i) {
                array[i] = input.readInt(false);
            }
            return array;
        }
        
        public int[] copy(final Kryo kryo, final int[] original) {
            final int[] copy = new int[original.length];
            System.arraycopy(original, 0, copy, 0, copy.length);
            return copy;
        }
    }
    
    public static class FloatArraySerializer extends Serializer<float[]>
    {
        public FloatArraySerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final float[] object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.length + 1, true);
            for (int i = 0, n = object.length; i < n; ++i) {
                output.writeFloat(object[i]);
            }
        }
        
        public float[] read(final Kryo kryo, final Input input, final Class<float[]> type) {
            int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            final float[] array = new float[--length];
            for (int i = 0; i < length; ++i) {
                array[i] = input.readFloat();
            }
            return array;
        }
        
        public float[] copy(final Kryo kryo, final float[] original) {
            final float[] copy = new float[original.length];
            System.arraycopy(original, 0, copy, 0, copy.length);
            return copy;
        }
    }
    
    public static class LongArraySerializer extends Serializer<long[]>
    {
        public LongArraySerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final long[] object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.length + 1, true);
            for (int i = 0, n = object.length; i < n; ++i) {
                output.writeLong(object[i], false);
            }
        }
        
        public long[] read(final Kryo kryo, final Input input, final Class<long[]> type) {
            int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            final long[] array = new long[--length];
            for (int i = 0; i < length; ++i) {
                array[i] = input.readLong(false);
            }
            return array;
        }
        
        public long[] copy(final Kryo kryo, final long[] original) {
            final long[] copy = new long[original.length];
            System.arraycopy(original, 0, copy, 0, copy.length);
            return copy;
        }
    }
    
    public static class ShortArraySerializer extends Serializer<short[]>
    {
        public ShortArraySerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final short[] object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.length + 1, true);
            for (int i = 0, n = object.length; i < n; ++i) {
                output.writeShort(object[i]);
            }
        }
        
        public short[] read(final Kryo kryo, final Input input, final Class<short[]> type) {
            int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            final short[] array = new short[--length];
            for (int i = 0; i < length; ++i) {
                array[i] = input.readShort();
            }
            return array;
        }
        
        public short[] copy(final Kryo kryo, final short[] original) {
            final short[] copy = new short[original.length];
            System.arraycopy(original, 0, copy, 0, copy.length);
            return copy;
        }
    }
    
    public static class CharArraySerializer extends Serializer<char[]>
    {
        public CharArraySerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final char[] object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.length + 1, true);
            for (int i = 0, n = object.length; i < n; ++i) {
                output.writeChar(object[i]);
            }
        }
        
        public char[] read(final Kryo kryo, final Input input, final Class<char[]> type) {
            int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            final char[] array = new char[--length];
            for (int i = 0; i < length; ++i) {
                array[i] = input.readChar();
            }
            return array;
        }
        
        public char[] copy(final Kryo kryo, final char[] original) {
            final char[] copy = new char[original.length];
            System.arraycopy(original, 0, copy, 0, copy.length);
            return copy;
        }
    }
    
    public static class DoubleArraySerializer extends Serializer<double[]>
    {
        public DoubleArraySerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final double[] object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.length + 1, true);
            for (int i = 0, n = object.length; i < n; ++i) {
                output.writeDouble(object[i]);
            }
        }
        
        public double[] read(final Kryo kryo, final Input input, final Class<double[]> type) {
            int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            final double[] array = new double[--length];
            for (int i = 0; i < length; ++i) {
                array[i] = input.readDouble();
            }
            return array;
        }
        
        public double[] copy(final Kryo kryo, final double[] original) {
            final double[] copy = new double[original.length];
            System.arraycopy(original, 0, copy, 0, copy.length);
            return copy;
        }
    }
    
    public static class BooleanArraySerializer extends Serializer<boolean[]>
    {
        public BooleanArraySerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final boolean[] object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.length + 1, true);
            for (int i = 0, n = object.length; i < n; ++i) {
                output.writeBoolean(object[i]);
            }
        }
        
        public boolean[] read(final Kryo kryo, final Input input, final Class<boolean[]> type) {
            int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            final boolean[] array = new boolean[--length];
            for (int i = 0; i < length; ++i) {
                array[i] = input.readBoolean();
            }
            return array;
        }
        
        public boolean[] copy(final Kryo kryo, final boolean[] original) {
            final boolean[] copy = new boolean[original.length];
            System.arraycopy(original, 0, copy, 0, copy.length);
            return copy;
        }
    }
    
    public static class StringArraySerializer extends Serializer<String[]>
    {
        public StringArraySerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final String[] object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.length + 1, true);
            for (int i = 0, n = object.length; i < n; ++i) {
                output.writeString(object[i]);
            }
        }
        
        public String[] read(final Kryo kryo, final Input input, final Class<String[]> type) {
            int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            final String[] array = new String[--length];
            for (int i = 0; i < length; ++i) {
                array[i] = input.readString();
            }
            return array;
        }
        
        public String[] copy(final Kryo kryo, final String[] original) {
            final String[] copy = new String[original.length];
            System.arraycopy(original, 0, copy, 0, copy.length);
            return copy;
        }
    }
    
    public static class ObjectArraySerializer extends Serializer<Object[]>
    {
        private boolean elementsAreSameType;
        private boolean elementsCanBeNull;
        
        public ObjectArraySerializer() {
            this.setAcceptsNull(this.elementsCanBeNull = true);
        }
        
        public void write(final Kryo kryo, final Output output, final Object[] object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.length + 1, true);
            final Class elementClass = object.getClass().getComponentType();
            if (this.elementsAreSameType || Modifier.isFinal(elementClass.getModifiers())) {
                final Serializer elementSerializer = kryo.getSerializer(elementClass);
                for (int i = 0, n = object.length; i < n; ++i) {
                    if (this.elementsCanBeNull) {
                        kryo.writeObjectOrNull(output, object[i], elementSerializer);
                    }
                    else {
                        kryo.writeObject(output, object[i], elementSerializer);
                    }
                }
            }
            else {
                for (int j = 0, n2 = object.length; j < n2; ++j) {
                    kryo.writeClassAndObject(output, object[j]);
                }
            }
        }
        
        public Object[] read(final Kryo kryo, final Input input, final Class<Object[]> type) {
            final int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            final Object[] object = (Object[])Array.newInstance(type.getComponentType(), length - 1);
            kryo.reference(object);
            final Class elementClass = object.getClass().getComponentType();
            if (this.elementsAreSameType || Modifier.isFinal(elementClass.getModifiers())) {
                final Serializer elementSerializer = kryo.getSerializer(elementClass);
                for (int i = 0, n = object.length; i < n; ++i) {
                    if (this.elementsCanBeNull) {
                        object[i] = kryo.readObjectOrNull(input, (Class<Object>)elementClass, elementSerializer);
                    }
                    else {
                        object[i] = kryo.readObject(input, (Class<Object>)elementClass, elementSerializer);
                    }
                }
            }
            else {
                for (int j = 0, n2 = object.length; j < n2; ++j) {
                    object[j] = kryo.readClassAndObject(input);
                }
            }
            return object;
        }
        
        public Object[] copy(final Kryo kryo, final Object[] original) {
            final Object[] copy = (Object[])Array.newInstance(original.getClass().getComponentType(), original.length);
            System.arraycopy(original, 0, copy, 0, copy.length);
            return copy;
        }
        
        public void setElementsCanBeNull(final boolean elementsCanBeNull) {
            this.elementsCanBeNull = elementsCanBeNull;
        }
        
        public void setElementsAreSameType(final boolean elementsAreSameType) {
            this.elementsAreSameType = elementsAreSameType;
        }
    }
}
