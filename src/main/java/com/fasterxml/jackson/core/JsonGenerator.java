// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.core;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicInteger;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.io.InputStream;
import java.io.IOException;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import java.io.Flushable;
import java.io.Closeable;

public abstract class JsonGenerator implements Closeable, Flushable, Versioned
{
    protected PrettyPrinter _cfgPrettyPrinter;
    
    public abstract JsonGenerator setCodec(final ObjectCodec p0);
    
    public abstract ObjectCodec getCodec();
    
    @Override
    public abstract Version version();
    
    public Object getOutputTarget() {
        return null;
    }
    
    public abstract JsonGenerator enable(final Feature p0);
    
    public abstract JsonGenerator disable(final Feature p0);
    
    public final JsonGenerator configure(final Feature f, final boolean state) {
        if (state) {
            this.enable(f);
        }
        else {
            this.disable(f);
        }
        return this;
    }
    
    public abstract boolean isEnabled(final Feature p0);
    
    public abstract int getFeatureMask();
    
    public abstract JsonGenerator setFeatureMask(final int p0);
    
    public void setSchema(final FormatSchema schema) {
        throw new UnsupportedOperationException("Generator of type " + this.getClass().getName() + " does not support schema of type '" + schema.getSchemaType() + "'");
    }
    
    public FormatSchema getSchema() {
        return null;
    }
    
    public JsonGenerator setPrettyPrinter(final PrettyPrinter pp) {
        this._cfgPrettyPrinter = pp;
        return this;
    }
    
    public PrettyPrinter getPrettyPrinter() {
        return this._cfgPrettyPrinter;
    }
    
    public abstract JsonGenerator useDefaultPrettyPrinter();
    
    public JsonGenerator setHighestNonEscapedChar(final int charCode) {
        return this;
    }
    
    public int getHighestEscapedChar() {
        return 0;
    }
    
    public CharacterEscapes getCharacterEscapes() {
        return null;
    }
    
    public JsonGenerator setCharacterEscapes(final CharacterEscapes esc) {
        return this;
    }
    
    public JsonGenerator setRootValueSeparator(final SerializableString sep) {
        throw new UnsupportedOperationException();
    }
    
    public boolean canUseSchema(final FormatSchema schema) {
        return false;
    }
    
    public boolean canWriteObjectId() {
        return false;
    }
    
    public boolean canWriteTypeId() {
        return false;
    }
    
    public boolean canWriteBinaryNatively() {
        return false;
    }
    
    public boolean canOmitFields() {
        return true;
    }
    
    public abstract void writeStartArray() throws IOException;
    
    public void writeStartArray(final int size) throws IOException {
        this.writeStartArray();
    }
    
    public abstract void writeEndArray() throws IOException;
    
    public abstract void writeStartObject() throws IOException;
    
    public abstract void writeEndObject() throws IOException;
    
    public abstract void writeFieldName(final String p0) throws IOException;
    
    public abstract void writeFieldName(final SerializableString p0) throws IOException;
    
    public abstract void writeString(final String p0) throws IOException;
    
    public abstract void writeString(final char[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeString(final SerializableString p0) throws IOException;
    
    public abstract void writeRawUTF8String(final byte[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeUTF8String(final byte[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeRaw(final String p0) throws IOException;
    
    public abstract void writeRaw(final String p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeRaw(final char[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeRaw(final char p0) throws IOException;
    
    public void writeRaw(final SerializableString raw) throws IOException {
        this.writeRaw(raw.getValue());
    }
    
    public abstract void writeRawValue(final String p0) throws IOException;
    
    public abstract void writeRawValue(final String p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeRawValue(final char[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeBinary(final Base64Variant p0, final byte[] p1, final int p2, final int p3) throws IOException;
    
    public void writeBinary(final byte[] data, final int offset, final int len) throws IOException {
        this.writeBinary(Base64Variants.getDefaultVariant(), data, offset, len);
    }
    
    public void writeBinary(final byte[] data) throws IOException {
        this.writeBinary(Base64Variants.getDefaultVariant(), data, 0, data.length);
    }
    
    public int writeBinary(final InputStream data, final int dataLength) throws IOException {
        return this.writeBinary(Base64Variants.getDefaultVariant(), data, dataLength);
    }
    
    public abstract int writeBinary(final Base64Variant p0, final InputStream p1, final int p2) throws IOException;
    
    public void writeNumber(final short v) throws IOException {
        this.writeNumber((int)v);
    }
    
    public abstract void writeNumber(final int p0) throws IOException;
    
    public abstract void writeNumber(final long p0) throws IOException;
    
    public abstract void writeNumber(final BigInteger p0) throws IOException;
    
    public abstract void writeNumber(final double p0) throws IOException;
    
    public abstract void writeNumber(final float p0) throws IOException;
    
    public abstract void writeNumber(final BigDecimal p0) throws IOException;
    
    public abstract void writeNumber(final String p0) throws IOException;
    
    public abstract void writeBoolean(final boolean p0) throws IOException;
    
    public abstract void writeNull() throws IOException;
    
    public void writeObjectId(final Object id) throws IOException {
        throw new JsonGenerationException("No native support for writing Object Ids");
    }
    
    public void writeObjectRef(final Object id) throws IOException {
        throw new JsonGenerationException("No native support for writing Object Ids");
    }
    
    public void writeTypeId(final Object id) throws IOException {
        throw new JsonGenerationException("No native support for writing Type Ids");
    }
    
    public abstract void writeObject(final Object p0) throws IOException;
    
    public abstract void writeTree(final TreeNode p0) throws IOException;
    
    public void writeStringField(final String fieldName, final String value) throws IOException {
        this.writeFieldName(fieldName);
        this.writeString(value);
    }
    
    public final void writeBooleanField(final String fieldName, final boolean value) throws IOException {
        this.writeFieldName(fieldName);
        this.writeBoolean(value);
    }
    
    public final void writeNullField(final String fieldName) throws IOException {
        this.writeFieldName(fieldName);
        this.writeNull();
    }
    
    public final void writeNumberField(final String fieldName, final int value) throws IOException {
        this.writeFieldName(fieldName);
        this.writeNumber(value);
    }
    
    public final void writeNumberField(final String fieldName, final long value) throws IOException {
        this.writeFieldName(fieldName);
        this.writeNumber(value);
    }
    
    public final void writeNumberField(final String fieldName, final double value) throws IOException {
        this.writeFieldName(fieldName);
        this.writeNumber(value);
    }
    
    public final void writeNumberField(final String fieldName, final float value) throws IOException {
        this.writeFieldName(fieldName);
        this.writeNumber(value);
    }
    
    public final void writeNumberField(final String fieldName, final BigDecimal value) throws IOException {
        this.writeFieldName(fieldName);
        this.writeNumber(value);
    }
    
    public final void writeBinaryField(final String fieldName, final byte[] data) throws IOException {
        this.writeFieldName(fieldName);
        this.writeBinary(data);
    }
    
    public final void writeArrayFieldStart(final String fieldName) throws IOException {
        this.writeFieldName(fieldName);
        this.writeStartArray();
    }
    
    public final void writeObjectFieldStart(final String fieldName) throws IOException {
        this.writeFieldName(fieldName);
        this.writeStartObject();
    }
    
    public final void writeObjectField(final String fieldName, final Object pojo) throws IOException {
        this.writeFieldName(fieldName);
        this.writeObject(pojo);
    }
    
    public void writeOmittedField(final String fieldName) throws IOException {
    }
    
    public void copyCurrentEvent(final JsonParser jp) throws IOException {
        final JsonToken t = jp.getCurrentToken();
        if (t == null) {
            this._reportError("No current event to copy");
        }
        switch (t.id()) {
            case -1: {
                this._reportError("No current event to copy");
            }
            case 1: {
                this.writeStartObject();
                break;
            }
            case 2: {
                this.writeEndObject();
                break;
            }
            case 3: {
                this.writeStartArray();
                break;
            }
            case 4: {
                this.writeEndArray();
                break;
            }
            case 5: {
                this.writeFieldName(jp.getCurrentName());
                break;
            }
            case 6: {
                if (jp.hasTextCharacters()) {
                    this.writeString(jp.getTextCharacters(), jp.getTextOffset(), jp.getTextLength());
                    break;
                }
                this.writeString(jp.getText());
                break;
            }
            case 7: {
                final JsonParser.NumberType n = jp.getNumberType();
                if (n == JsonParser.NumberType.INT) {
                    this.writeNumber(jp.getIntValue());
                    break;
                }
                if (n == JsonParser.NumberType.BIG_INTEGER) {
                    this.writeNumber(jp.getBigIntegerValue());
                    break;
                }
                this.writeNumber(jp.getLongValue());
                break;
            }
            case 8: {
                final JsonParser.NumberType n = jp.getNumberType();
                if (n == JsonParser.NumberType.BIG_DECIMAL) {
                    this.writeNumber(jp.getDecimalValue());
                    break;
                }
                if (n == JsonParser.NumberType.FLOAT) {
                    this.writeNumber(jp.getFloatValue());
                    break;
                }
                this.writeNumber(jp.getDoubleValue());
                break;
            }
            case 9: {
                this.writeBoolean(true);
                break;
            }
            case 10: {
                this.writeBoolean(false);
                break;
            }
            case 11: {
                this.writeNull();
                break;
            }
            case 12: {
                this.writeObject(jp.getEmbeddedObject());
                break;
            }
            default: {
                this._throwInternal();
                break;
            }
        }
    }
    
    public void copyCurrentStructure(final JsonParser jp) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == null) {
            this._reportError("No current event to copy");
        }
        int id = t.id();
        if (id == 5) {
            this.writeFieldName(jp.getCurrentName());
            t = jp.nextToken();
            id = t.id();
        }
        switch (id) {
            case 1: {
                this.writeStartObject();
                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    this.copyCurrentStructure(jp);
                }
                this.writeEndObject();
                break;
            }
            case 3: {
                this.writeStartArray();
                while (jp.nextToken() != JsonToken.END_ARRAY) {
                    this.copyCurrentStructure(jp);
                }
                this.writeEndArray();
                break;
            }
            default: {
                this.copyCurrentEvent(jp);
                break;
            }
        }
    }
    
    public abstract JsonStreamContext getOutputContext();
    
    @Override
    public abstract void flush() throws IOException;
    
    public abstract boolean isClosed();
    
    @Override
    public abstract void close() throws IOException;
    
    protected void _reportError(final String msg) throws JsonGenerationException {
        throw new JsonGenerationException(msg);
    }
    
    protected final void _throwInternal() {
        VersionUtil.throwInternal();
    }
    
    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Operation not supported by generator of type " + this.getClass().getName());
    }
    
    protected void _writeSimpleObject(final Object value) throws IOException {
        if (value == null) {
            this.writeNull();
            return;
        }
        if (value instanceof String) {
            this.writeString((String)value);
            return;
        }
        if (value instanceof Number) {
            final Number n = (Number)value;
            if (n instanceof Integer) {
                this.writeNumber(n.intValue());
                return;
            }
            if (n instanceof Long) {
                this.writeNumber(n.longValue());
                return;
            }
            if (n instanceof Double) {
                this.writeNumber(n.doubleValue());
                return;
            }
            if (n instanceof Float) {
                this.writeNumber(n.floatValue());
                return;
            }
            if (n instanceof Short) {
                this.writeNumber(n.shortValue());
                return;
            }
            if (n instanceof Byte) {
                this.writeNumber(n.byteValue());
                return;
            }
            if (n instanceof BigInteger) {
                this.writeNumber((BigInteger)n);
                return;
            }
            if (n instanceof BigDecimal) {
                this.writeNumber((BigDecimal)n);
                return;
            }
            if (n instanceof AtomicInteger) {
                this.writeNumber(((AtomicInteger)n).get());
                return;
            }
            if (n instanceof AtomicLong) {
                this.writeNumber(((AtomicLong)n).get());
                return;
            }
        }
        else {
            if (value instanceof byte[]) {
                this.writeBinary((byte[])value);
                return;
            }
            if (value instanceof Boolean) {
                this.writeBoolean((boolean)value);
                return;
            }
            if (value instanceof AtomicBoolean) {
                this.writeBoolean(((AtomicBoolean)value).get());
                return;
            }
        }
        throw new IllegalStateException("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed " + value.getClass().getName() + ")");
    }
    
    public enum Feature
    {
        AUTO_CLOSE_TARGET(true), 
        AUTO_CLOSE_JSON_CONTENT(true), 
        QUOTE_FIELD_NAMES(true), 
        QUOTE_NON_NUMERIC_NUMBERS(true), 
        WRITE_NUMBERS_AS_STRINGS(false), 
        WRITE_BIGDECIMAL_AS_PLAIN(false), 
        FLUSH_PASSED_TO_STREAM(true), 
        ESCAPE_NON_ASCII(false), 
        STRICT_DUPLICATE_DETECTION(false);
        
        private final boolean _defaultState;
        private final int _mask;
        
        public static int collectDefaults() {
            int flags = 0;
            for (final Feature f : values()) {
                if (f.enabledByDefault()) {
                    flags |= f.getMask();
                }
            }
            return flags;
        }
        
        private Feature(final boolean defaultState) {
            this._defaultState = defaultState;
            this._mask = 1 << this.ordinal();
        }
        
        public boolean enabledByDefault() {
            return this._defaultState;
        }
        
        public boolean enabledIn(final int flags) {
            return (flags & this._mask) != 0x0;
        }
        
        public int getMask() {
            return this._mask;
        }
    }
}
