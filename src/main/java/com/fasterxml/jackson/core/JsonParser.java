// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.core;

import java.util.Iterator;
import com.fasterxml.jackson.core.type.TypeReference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.io.Writer;
import java.io.OutputStream;
import java.io.IOException;
import java.io.Closeable;

public abstract class JsonParser implements Closeable, Versioned
{
    private static final int MIN_BYTE_I = -128;
    private static final int MAX_BYTE_I = 255;
    private static final int MIN_SHORT_I = -32768;
    private static final int MAX_SHORT_I = 32767;
    protected int _features;
    
    protected JsonParser() {
    }
    
    protected JsonParser(final int features) {
        this._features = features;
    }
    
    public abstract ObjectCodec getCodec();
    
    public abstract void setCodec(final ObjectCodec p0);
    
    public Object getInputSource() {
        return null;
    }
    
    public void setSchema(final FormatSchema schema) {
        throw new UnsupportedOperationException("Parser of type " + this.getClass().getName() + " does not support schema of type '" + schema.getSchemaType() + "'");
    }
    
    public FormatSchema getSchema() {
        return null;
    }
    
    public boolean canUseSchema(final FormatSchema schema) {
        return false;
    }
    
    public boolean requiresCustomCodec() {
        return false;
    }
    
    @Override
    public abstract Version version();
    
    @Override
    public abstract void close() throws IOException;
    
    public int releaseBuffered(final OutputStream out) throws IOException {
        return -1;
    }
    
    public int releaseBuffered(final Writer w) throws IOException {
        return -1;
    }
    
    public JsonParser enable(final Feature f) {
        this._features |= f.getMask();
        return this;
    }
    
    public JsonParser disable(final Feature f) {
        this._features &= ~f.getMask();
        return this;
    }
    
    public JsonParser configure(final Feature f, final boolean state) {
        if (state) {
            this.enable(f);
        }
        else {
            this.disable(f);
        }
        return this;
    }
    
    public boolean isEnabled(final Feature f) {
        return (this._features & f.getMask()) != 0x0;
    }
    
    public int getFeatureMask() {
        return this._features;
    }
    
    public JsonParser setFeatureMask(final int mask) {
        this._features = mask;
        return this;
    }
    
    public abstract JsonToken nextToken() throws IOException, JsonParseException;
    
    public abstract JsonToken nextValue() throws IOException, JsonParseException;
    
    public boolean nextFieldName(final SerializableString str) throws IOException, JsonParseException {
        return this.nextToken() == JsonToken.FIELD_NAME && str.getValue().equals(this.getCurrentName());
    }
    
    public String nextTextValue() throws IOException, JsonParseException {
        return (this.nextToken() == JsonToken.VALUE_STRING) ? this.getText() : null;
    }
    
    public int nextIntValue(final int defaultValue) throws IOException, JsonParseException {
        return (this.nextToken() == JsonToken.VALUE_NUMBER_INT) ? this.getIntValue() : defaultValue;
    }
    
    public long nextLongValue(final long defaultValue) throws IOException, JsonParseException {
        return (this.nextToken() == JsonToken.VALUE_NUMBER_INT) ? this.getLongValue() : defaultValue;
    }
    
    public Boolean nextBooleanValue() throws IOException, JsonParseException {
        final JsonToken t = this.nextToken();
        if (t == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (t == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        return null;
    }
    
    public abstract JsonParser skipChildren() throws IOException, JsonParseException;
    
    public abstract boolean isClosed();
    
    public abstract JsonToken getCurrentToken();
    
    public abstract int getCurrentTokenId();
    
    public abstract boolean hasCurrentToken();
    
    public abstract String getCurrentName() throws IOException;
    
    public abstract JsonStreamContext getParsingContext();
    
    public abstract JsonLocation getTokenLocation();
    
    public abstract JsonLocation getCurrentLocation();
    
    public boolean isExpectedStartArrayToken() {
        return this.getCurrentToken() == JsonToken.START_ARRAY;
    }
    
    public abstract void clearCurrentToken();
    
    public abstract JsonToken getLastClearedToken();
    
    public abstract void overrideCurrentName(final String p0);
    
    public abstract String getText() throws IOException;
    
    public abstract char[] getTextCharacters() throws IOException;
    
    public abstract int getTextLength() throws IOException;
    
    public abstract int getTextOffset() throws IOException;
    
    public abstract boolean hasTextCharacters();
    
    public abstract Number getNumberValue() throws IOException;
    
    public abstract NumberType getNumberType() throws IOException;
    
    public byte getByteValue() throws IOException {
        final int value = this.getIntValue();
        if (value < -128 || value > 255) {
            throw this._constructError("Numeric value (" + this.getText() + ") out of range of Java byte");
        }
        return (byte)value;
    }
    
    public short getShortValue() throws IOException {
        final int value = this.getIntValue();
        if (value < -32768 || value > 32767) {
            throw this._constructError("Numeric value (" + this.getText() + ") out of range of Java short");
        }
        return (short)value;
    }
    
    public abstract int getIntValue() throws IOException;
    
    public abstract long getLongValue() throws IOException;
    
    public abstract BigInteger getBigIntegerValue() throws IOException;
    
    public abstract float getFloatValue() throws IOException;
    
    public abstract double getDoubleValue() throws IOException;
    
    public abstract BigDecimal getDecimalValue() throws IOException;
    
    public boolean getBooleanValue() throws IOException {
        final JsonToken t = this.getCurrentToken();
        if (t == JsonToken.VALUE_TRUE) {
            return true;
        }
        if (t == JsonToken.VALUE_FALSE) {
            return false;
        }
        throw new JsonParseException("Current token (" + t + ") not of boolean type", this.getCurrentLocation());
    }
    
    public abstract Object getEmbeddedObject() throws IOException;
    
    public abstract byte[] getBinaryValue(final Base64Variant p0) throws IOException;
    
    public byte[] getBinaryValue() throws IOException {
        return this.getBinaryValue(Base64Variants.getDefaultVariant());
    }
    
    public int readBinaryValue(final OutputStream out) throws IOException {
        return this.readBinaryValue(Base64Variants.getDefaultVariant(), out);
    }
    
    public int readBinaryValue(final Base64Variant bv, final OutputStream out) throws IOException {
        this._reportUnsupportedOperation();
        return 0;
    }
    
    public int getValueAsInt() throws IOException {
        return this.getValueAsInt(0);
    }
    
    public int getValueAsInt(final int def) throws IOException {
        return def;
    }
    
    public long getValueAsLong() throws IOException {
        return this.getValueAsLong(0L);
    }
    
    public long getValueAsLong(final long def) throws IOException {
        return def;
    }
    
    public double getValueAsDouble() throws IOException {
        return this.getValueAsDouble(0.0);
    }
    
    public double getValueAsDouble(final double def) throws IOException {
        return def;
    }
    
    public boolean getValueAsBoolean() throws IOException {
        return this.getValueAsBoolean(false);
    }
    
    public boolean getValueAsBoolean(final boolean def) throws IOException {
        return def;
    }
    
    public String getValueAsString() throws IOException {
        return this.getValueAsString(null);
    }
    
    public abstract String getValueAsString(final String p0) throws IOException;
    
    public boolean canReadObjectId() {
        return false;
    }
    
    public boolean canReadTypeId() {
        return false;
    }
    
    public Object getObjectId() throws IOException {
        return null;
    }
    
    public Object getTypeId() throws IOException {
        return null;
    }
    
    public <T> T readValueAs(final Class<T> valueType) throws IOException {
        return this._codec().readValue(this, valueType);
    }
    
    public <T> T readValueAs(final TypeReference<?> valueTypeRef) throws IOException {
        return this._codec().readValue(this, valueTypeRef);
    }
    
    public <T> Iterator<T> readValuesAs(final Class<T> valueType) throws IOException {
        return this._codec().readValues(this, valueType);
    }
    
    public <T> Iterator<T> readValuesAs(final TypeReference<?> valueTypeRef) throws IOException {
        return this._codec().readValues(this, valueTypeRef);
    }
    
    public <T extends TreeNode> T readValueAsTree() throws IOException {
        return this._codec().readTree(this);
    }
    
    protected ObjectCodec _codec() {
        final ObjectCodec c = this.getCodec();
        if (c == null) {
            throw new IllegalStateException("No ObjectCodec defined for parser, needed for deserialization");
        }
        return c;
    }
    
    protected JsonParseException _constructError(final String msg) {
        return new JsonParseException(msg, this.getCurrentLocation());
    }
    
    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Operation not supported by parser of type " + this.getClass().getName());
    }
    
    public enum NumberType
    {
        INT, 
        LONG, 
        BIG_INTEGER, 
        FLOAT, 
        DOUBLE, 
        BIG_DECIMAL;
    }
    
    public enum Feature
    {
        AUTO_CLOSE_SOURCE(true), 
        ALLOW_COMMENTS(false), 
        ALLOW_YAML_COMMENTS(false), 
        ALLOW_UNQUOTED_FIELD_NAMES(false), 
        ALLOW_SINGLE_QUOTES(false), 
        ALLOW_UNQUOTED_CONTROL_CHARS(false), 
        ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false), 
        ALLOW_NUMERIC_LEADING_ZEROS(false), 
        ALLOW_NON_NUMERIC_NUMBERS(false), 
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
            this._mask = 1 << this.ordinal();
            this._defaultState = defaultState;
        }
        
        public boolean enabledByDefault() {
            return this._defaultState;
        }
        
        public boolean enabledIn(final int flags) {
            return (flags & this._mask) != 0x0;
        }
        
        public int getMask() {
            return 1 << this.ordinal();
        }
    }
}
