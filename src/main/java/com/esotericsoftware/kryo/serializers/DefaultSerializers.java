// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.serializers;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.Collections;
import com.esotericsoftware.kryo.KryoSerializable;
import java.util.Currency;
import com.esotericsoftware.kryo.Registration;
import java.util.Iterator;
import java.util.EnumSet;
import com.esotericsoftware.kryo.KryoException;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;

public class DefaultSerializers
{
    public static class BooleanSerializer extends Serializer<Boolean>
    {
        public BooleanSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Boolean object) {
            output.writeBoolean(object);
        }
        
        public Boolean read(final Kryo kryo, final Input input, final Class<Boolean> type) {
            return input.readBoolean();
        }
    }
    
    public static class ByteSerializer extends Serializer<Byte>
    {
        public ByteSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Byte object) {
            output.writeByte(object);
        }
        
        public Byte read(final Kryo kryo, final Input input, final Class<Byte> type) {
            return input.readByte();
        }
    }
    
    public static class CharSerializer extends Serializer<Character>
    {
        public CharSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Character object) {
            output.writeChar(object);
        }
        
        public Character read(final Kryo kryo, final Input input, final Class<Character> type) {
            return input.readChar();
        }
    }
    
    public static class ShortSerializer extends Serializer<Short>
    {
        public ShortSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Short object) {
            output.writeShort(object);
        }
        
        public Short read(final Kryo kryo, final Input input, final Class<Short> type) {
            return input.readShort();
        }
    }
    
    public static class IntSerializer extends Serializer<Integer>
    {
        public IntSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Integer object) {
            output.writeInt(object, false);
        }
        
        public Integer read(final Kryo kryo, final Input input, final Class<Integer> type) {
            return input.readInt(false);
        }
    }
    
    public static class LongSerializer extends Serializer<Long>
    {
        public LongSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Long object) {
            output.writeLong(object, false);
        }
        
        public Long read(final Kryo kryo, final Input input, final Class<Long> type) {
            return input.readLong(false);
        }
    }
    
    public static class FloatSerializer extends Serializer<Float>
    {
        public FloatSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Float object) {
            output.writeFloat(object);
        }
        
        public Float read(final Kryo kryo, final Input input, final Class<Float> type) {
            return input.readFloat();
        }
    }
    
    public static class DoubleSerializer extends Serializer<Double>
    {
        public DoubleSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Double object) {
            output.writeDouble(object);
        }
        
        public Double read(final Kryo kryo, final Input input, final Class<Double> type) {
            return input.readDouble();
        }
    }
    
    public static class StringSerializer extends Serializer<String>
    {
        public StringSerializer() {
            this.setImmutable(true);
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final String object) {
            output.writeString(object);
        }
        
        public String read(final Kryo kryo, final Input input, final Class<String> type) {
            return input.readString();
        }
    }
    
    public static class BigIntegerSerializer extends Serializer<BigInteger>
    {
        public BigIntegerSerializer() {
            this.setImmutable(true);
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final BigInteger object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            final byte[] bytes = object.toByteArray();
            output.writeInt(bytes.length + 1, true);
            output.writeBytes(bytes);
        }
        
        public BigInteger read(final Kryo kryo, final Input input, final Class<BigInteger> type) {
            final int length = input.readInt(true);
            if (length == 0) {
                return null;
            }
            final byte[] bytes = input.readBytes(length - 1);
            return new BigInteger(bytes);
        }
    }
    
    public static class BigDecimalSerializer extends Serializer<BigDecimal>
    {
        private BigIntegerSerializer bigIntegerSerializer;
        
        public BigDecimalSerializer() {
            this.bigIntegerSerializer = new BigIntegerSerializer();
            this.setAcceptsNull(true);
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final BigDecimal object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            this.bigIntegerSerializer.write(kryo, output, object.unscaledValue());
            output.writeInt(object.scale(), false);
        }
        
        public BigDecimal read(final Kryo kryo, final Input input, final Class<BigDecimal> type) {
            final BigInteger unscaledValue = this.bigIntegerSerializer.read(kryo, input, null);
            if (unscaledValue == null) {
                return null;
            }
            final int scale = input.readInt(false);
            return new BigDecimal(unscaledValue, scale);
        }
    }
    
    public static class ClassSerializer extends Serializer<Class>
    {
        public ClassSerializer() {
            this.setImmutable(true);
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Class object) {
            kryo.writeClass(output, object);
        }
        
        public Class read(final Kryo kryo, final Input input, final Class<Class> type) {
            return kryo.readClass(input).getType();
        }
    }
    
    public static class DateSerializer extends Serializer<Date>
    {
        public void write(final Kryo kryo, final Output output, final Date object) {
            output.writeLong(object.getTime(), true);
        }
        
        public Date read(final Kryo kryo, final Input input, final Class<Date> type) {
            return new Date(input.readLong(true));
        }
        
        public Date copy(final Kryo kryo, final Date original) {
            return new Date(original.getTime());
        }
    }
    
    public static class EnumSerializer extends Serializer<Enum>
    {
        private Object[] enumConstants;
        
        public EnumSerializer(final Class<? extends Enum> type) {
            this.setImmutable(true);
            this.setAcceptsNull(true);
            this.enumConstants = (Object[])type.getEnumConstants();
            if (this.enumConstants == null) {
                throw new IllegalArgumentException("The type must be an enum: " + type);
            }
        }
        
        public void write(final Kryo kryo, final Output output, final Enum object) {
            if (object == null) {
                output.writeByte((byte)0);
                return;
            }
            output.writeInt(object.ordinal() + 1, true);
        }
        
        public Enum read(final Kryo kryo, final Input input, final Class<Enum> type) {
            int ordinal = input.readInt(true);
            if (ordinal == 0) {
                return null;
            }
            if (--ordinal < 0 || ordinal > this.enumConstants.length - 1) {
                throw new KryoException("Invalid ordinal for enum \"" + type.getName() + "\": " + ordinal);
            }
            final Object constant = this.enumConstants[ordinal];
            return (Enum)constant;
        }
    }
    
    public static class EnumSetSerializer extends Serializer<EnumSet>
    {
        public void write(final Kryo kryo, final Output output, final EnumSet object) {
            if (object.isEmpty()) {
                throw new KryoException("An empty EnumSet cannot be serialized.");
            }
            final Serializer serializer = kryo.writeClass(output, object.iterator().next().getClass()).getSerializer();
            output.writeInt(object.size(), true);
            for (final Object element : object) {
                serializer.write(kryo, output, element);
            }
        }
        
        public EnumSet read(final Kryo kryo, final Input input, final Class<EnumSet> type) {
            final Registration registration = kryo.readClass(input);
            final EnumSet object = EnumSet.noneOf((Class<Enum>)registration.getType());
            final Serializer serializer = registration.getSerializer();
            for (int length = input.readInt(true), i = 0; i < length; ++i) {
                object.add(serializer.read(kryo, input, null));
            }
            return object;
        }
        
        public EnumSet copy(final Kryo kryo, final EnumSet original) {
            return EnumSet.copyOf((EnumSet<Enum>)original);
        }
    }
    
    public static class CurrencySerializer extends Serializer<Currency>
    {
        public CurrencySerializer() {
            this.setImmutable(true);
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Currency object) {
            output.writeString((object == null) ? null : object.getCurrencyCode());
        }
        
        public Currency read(final Kryo kryo, final Input input, final Class<Currency> type) {
            final String currencyCode = input.readString();
            if (currencyCode == null) {
                return null;
            }
            return Currency.getInstance(currencyCode);
        }
    }
    
    public static class StringBufferSerializer extends Serializer<StringBuffer>
    {
        public StringBufferSerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final StringBuffer object) {
            output.writeString(object);
        }
        
        public StringBuffer read(final Kryo kryo, final Input input, final Class<StringBuffer> type) {
            final String value = input.readString();
            if (value == null) {
                return null;
            }
            return new StringBuffer(value);
        }
        
        public StringBuffer copy(final Kryo kryo, final StringBuffer original) {
            return new StringBuffer(original);
        }
    }
    
    public static class StringBuilderSerializer extends Serializer<StringBuilder>
    {
        public StringBuilderSerializer() {
            this.setAcceptsNull(true);
        }
        
        public void write(final Kryo kryo, final Output output, final StringBuilder object) {
            output.writeString(object);
        }
        
        public StringBuilder read(final Kryo kryo, final Input input, final Class<StringBuilder> type) {
            return input.readStringBuilder();
        }
        
        public StringBuilder copy(final Kryo kryo, final StringBuilder original) {
            return new StringBuilder(original);
        }
    }
    
    public static class KryoSerializableSerializer extends Serializer<KryoSerializable>
    {
        public void write(final Kryo kryo, final Output output, final KryoSerializable object) {
            object.write(kryo, output);
        }
        
        public KryoSerializable read(final Kryo kryo, final Input input, final Class<KryoSerializable> type) {
            final KryoSerializable object = kryo.newInstance(type);
            kryo.reference(object);
            object.read(kryo, input);
            return object;
        }
    }
    
    public static class CollectionsEmptyListSerializer extends Serializer
    {
        public CollectionsEmptyListSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Object object) {
        }
        
        public Object read(final Kryo kryo, final Input input, final Class type) {
            return Collections.EMPTY_LIST;
        }
    }
    
    public static class CollectionsEmptyMapSerializer extends Serializer
    {
        public CollectionsEmptyMapSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Object object) {
        }
        
        public Object read(final Kryo kryo, final Input input, final Class type) {
            return Collections.EMPTY_MAP;
        }
    }
    
    public static class CollectionsEmptySetSerializer extends Serializer
    {
        public CollectionsEmptySetSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Object object) {
        }
        
        public Object read(final Kryo kryo, final Input input, final Class type) {
            return Collections.EMPTY_SET;
        }
    }
    
    public static class CollectionsSingletonListSerializer extends Serializer<List>
    {
        public CollectionsSingletonListSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final List object) {
            kryo.writeClassAndObject(output, object.get(0));
        }
        
        public List read(final Kryo kryo, final Input input, final Class type) {
            return Collections.singletonList(kryo.readClassAndObject(input));
        }
    }
    
    public static class CollectionsSingletonMapSerializer extends Serializer<Map>
    {
        public CollectionsSingletonMapSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Map object) {
            final Map.Entry entry = (Map.Entry)object.entrySet().iterator().next();
            kryo.writeClassAndObject(output, entry.getKey());
            kryo.writeClassAndObject(output, entry.getValue());
        }
        
        public Map read(final Kryo kryo, final Input input, final Class type) {
            final Object key = kryo.readClassAndObject(input);
            final Object value = kryo.readClassAndObject(input);
            return Collections.singletonMap(key, value);
        }
    }
    
    public static class CollectionsSingletonSetSerializer extends Serializer<Set>
    {
        public CollectionsSingletonSetSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final Set object) {
            kryo.writeClassAndObject(output, object.iterator().next());
        }
        
        public Set read(final Kryo kryo, final Input input, final Class type) {
            return Collections.singleton(kryo.readClassAndObject(input));
        }
    }
    
    public static class TimeZoneSerializer extends Serializer<TimeZone>
    {
        public TimeZoneSerializer() {
            this.setImmutable(true);
        }
        
        public void write(final Kryo kryo, final Output output, final TimeZone object) {
            output.writeString(object.getID());
        }
        
        public TimeZone read(final Kryo kryo, final Input input, final Class<TimeZone> type) {
            return TimeZone.getTimeZone(input.readString());
        }
    }
    
    public static class CalendarSerializer extends Serializer<Calendar>
    {
        private static final long DEFAULT_GREGORIAN_CUTOVER = -12219292800000L;
        TimeZoneSerializer timeZoneSerializer;
        
        public CalendarSerializer() {
            this.timeZoneSerializer = new TimeZoneSerializer();
        }
        
        public void write(final Kryo kryo, final Output output, final Calendar object) {
            this.timeZoneSerializer.write(kryo, output, object.getTimeZone());
            output.writeLong(object.getTimeInMillis(), true);
            output.writeBoolean(object.isLenient());
            output.writeInt(object.getFirstDayOfWeek(), true);
            output.writeInt(object.getMinimalDaysInFirstWeek(), true);
            if (object instanceof GregorianCalendar) {
                output.writeLong(((GregorianCalendar)object).getGregorianChange().getTime(), false);
            }
            else {
                output.writeLong(-12219292800000L, false);
            }
        }
        
        public Calendar read(final Kryo kryo, final Input input, final Class<Calendar> type) {
            final Calendar result = Calendar.getInstance(this.timeZoneSerializer.read(kryo, input, TimeZone.class));
            result.setTimeInMillis(input.readLong(true));
            result.setLenient(input.readBoolean());
            result.setFirstDayOfWeek(input.readInt(true));
            result.setMinimalDaysInFirstWeek(input.readInt(true));
            final long gregorianChange = input.readLong(false);
            if (gregorianChange != -12219292800000L && result instanceof GregorianCalendar) {
                ((GregorianCalendar)result).setGregorianChange(new Date(gregorianChange));
            }
            return result;
        }
        
        public Calendar copy(final Kryo kryo, final Calendar original) {
            return (Calendar)original.clone();
        }
    }
    
    public static class TreeMapSerializer extends MapSerializer
    {
        public void write(final Kryo kryo, final Output output, final Map map) {
            final TreeMap treeMap = (TreeMap)map;
            final boolean references = kryo.setReferences(false);
            kryo.writeClassAndObject(output, treeMap.comparator());
            kryo.setReferences(references);
            super.write(kryo, output, map);
        }
        
        protected Map create(final Kryo kryo, final Input input, final Class<Map> type) {
            return new TreeMap((Comparator)kryo.readClassAndObject(input));
        }
        
        protected Map createCopy(final Kryo kryo, final Map original) {
            return new TreeMap(((TreeMap)original).comparator());
        }
    }
}
