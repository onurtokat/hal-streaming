// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.TreeNode;
import java.io.InputStream;
import com.fasterxml.jackson.core.Base64Variant;
import java.io.IOException;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.JsonGenerator;

public abstract class GeneratorBase extends JsonGenerator
{
    protected static final int DERIVED_FEATURES_MASK;
    protected ObjectCodec _objectCodec;
    protected int _features;
    protected boolean _cfgNumbersAsStrings;
    protected JsonWriteContext _writeContext;
    protected boolean _closed;
    
    protected GeneratorBase(final int features, final ObjectCodec codec) {
        this._features = features;
        final DupDetector dups = Feature.STRICT_DUPLICATE_DETECTION.enabledIn(features) ? DupDetector.rootDetector(this) : null;
        this._writeContext = JsonWriteContext.createRootContext(dups);
        this._objectCodec = codec;
        this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(features);
    }
    
    @Override
    public Version version() {
        return VersionUtil.versionFor(this.getClass());
    }
    
    @Override
    public JsonGenerator enable(final Feature f) {
        final int mask = f.getMask();
        this._features |= mask;
        if ((mask & GeneratorBase.DERIVED_FEATURES_MASK) != 0x0) {
            if (f == Feature.WRITE_NUMBERS_AS_STRINGS) {
                this._cfgNumbersAsStrings = true;
            }
            else if (f == Feature.ESCAPE_NON_ASCII) {
                this.setHighestNonEscapedChar(127);
            }
        }
        return this;
    }
    
    @Override
    public JsonGenerator disable(final Feature f) {
        final int mask = f.getMask();
        this._features &= ~mask;
        if ((mask & GeneratorBase.DERIVED_FEATURES_MASK) != 0x0) {
            if (f == Feature.WRITE_NUMBERS_AS_STRINGS) {
                this._cfgNumbersAsStrings = false;
            }
            else if (f == Feature.ESCAPE_NON_ASCII) {
                this.setHighestNonEscapedChar(0);
            }
        }
        return this;
    }
    
    @Override
    public final boolean isEnabled(final Feature f) {
        return (this._features & f.getMask()) != 0x0;
    }
    
    @Override
    public int getFeatureMask() {
        return this._features;
    }
    
    @Override
    public JsonGenerator setFeatureMask(final int newMask) {
        final int changed = newMask ^ this._features;
        this._features = newMask;
        if ((changed & GeneratorBase.DERIVED_FEATURES_MASK) != 0x0) {
            this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(newMask);
            if (Feature.ESCAPE_NON_ASCII.enabledIn(changed)) {
                if (Feature.ESCAPE_NON_ASCII.enabledIn(newMask)) {
                    this.setHighestNonEscapedChar(127);
                }
                else {
                    this.setHighestNonEscapedChar(0);
                }
            }
        }
        return this;
    }
    
    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        if (this.getPrettyPrinter() != null) {
            return this;
        }
        return this.setPrettyPrinter(new DefaultPrettyPrinter());
    }
    
    @Override
    public JsonGenerator setCodec(final ObjectCodec oc) {
        this._objectCodec = oc;
        return this;
    }
    
    @Override
    public final ObjectCodec getCodec() {
        return this._objectCodec;
    }
    
    @Override
    public final JsonWriteContext getOutputContext() {
        return this._writeContext;
    }
    
    @Override
    public void writeFieldName(final SerializableString name) throws IOException {
        this.writeFieldName(name.getValue());
    }
    
    @Override
    public void writeString(final SerializableString text) throws IOException {
        this.writeString(text.getValue());
    }
    
    @Override
    public void writeRawValue(final String text) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(text);
    }
    
    @Override
    public void writeRawValue(final String text, final int offset, final int len) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(text, offset, len);
    }
    
    @Override
    public void writeRawValue(final char[] text, final int offset, final int len) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(text, offset, len);
    }
    
    @Override
    public int writeBinary(final Base64Variant b64variant, final InputStream data, final int dataLength) throws IOException {
        this._reportUnsupportedOperation();
        return 0;
    }
    
    @Override
    public void writeObject(final Object value) throws IOException {
        if (value == null) {
            this.writeNull();
        }
        else {
            if (this._objectCodec != null) {
                this._objectCodec.writeValue(this, value);
                return;
            }
            this._writeSimpleObject(value);
        }
    }
    
    @Override
    public void writeTree(final TreeNode rootNode) throws IOException {
        if (rootNode == null) {
            this.writeNull();
        }
        else {
            if (this._objectCodec == null) {
                throw new IllegalStateException("No ObjectCodec defined");
            }
            this._objectCodec.writeValue(this, rootNode);
        }
    }
    
    @Override
    public abstract void flush() throws IOException;
    
    @Override
    public void close() throws IOException {
        this._closed = true;
    }
    
    @Override
    public boolean isClosed() {
        return this._closed;
    }
    
    protected abstract void _releaseBuffers();
    
    protected abstract void _verifyValueWrite(final String p0) throws IOException;
    
    static {
        DERIVED_FEATURES_MASK = (Feature.WRITE_NUMBERS_AS_STRINGS.getMask() | Feature.ESCAPE_NON_ASCII.getMask());
    }
}
