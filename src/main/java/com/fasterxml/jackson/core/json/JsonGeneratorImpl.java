// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.JsonGenerationException;
import java.io.IOException;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.base.GeneratorBase;

public abstract class JsonGeneratorImpl extends GeneratorBase
{
    protected static final int[] sOutputEscapes;
    protected final IOContext _ioContext;
    protected int[] _outputEscapes;
    protected int _maximumNonEscapedChar;
    protected CharacterEscapes _characterEscapes;
    protected SerializableString _rootValueSeparator;
    
    public JsonGeneratorImpl(final IOContext ctxt, final int features, final ObjectCodec codec) {
        super(features, codec);
        this._outputEscapes = JsonGeneratorImpl.sOutputEscapes;
        this._rootValueSeparator = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
        this._ioContext = ctxt;
        if (this.isEnabled(Feature.ESCAPE_NON_ASCII)) {
            this.setHighestNonEscapedChar(127);
        }
    }
    
    @Override
    public JsonGenerator setHighestNonEscapedChar(final int charCode) {
        this._maximumNonEscapedChar = ((charCode < 0) ? 0 : charCode);
        return this;
    }
    
    @Override
    public int getHighestEscapedChar() {
        return this._maximumNonEscapedChar;
    }
    
    @Override
    public JsonGenerator setCharacterEscapes(final CharacterEscapes esc) {
        this._characterEscapes = esc;
        if (esc == null) {
            this._outputEscapes = JsonGeneratorImpl.sOutputEscapes;
        }
        else {
            this._outputEscapes = esc.getEscapeCodesForAscii();
        }
        return this;
    }
    
    @Override
    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }
    
    @Override
    public JsonGenerator setRootValueSeparator(final SerializableString sep) {
        this._rootValueSeparator = sep;
        return this;
    }
    
    @Override
    public Version version() {
        return VersionUtil.versionFor(this.getClass());
    }
    
    @Override
    public final void writeStringField(final String fieldName, final String value) throws IOException, JsonGenerationException {
        this.writeFieldName(fieldName);
        this.writeString(value);
    }
    
    static {
        sOutputEscapes = CharTypes.get7BitOutputEscapes();
    }
}
