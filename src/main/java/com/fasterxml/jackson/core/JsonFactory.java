// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import java.io.OutputStreamWriter;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.OutputStream;
import java.io.CharArrayReader;
import java.io.StringReader;
import java.io.Reader;
import java.net.URL;
import java.io.InputStream;
import com.fasterxml.jackson.core.io.IOContext;
import java.io.FileInputStream;
import java.io.File;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import java.io.IOException;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.io.OutputDecorator;
import com.fasterxml.jackson.core.io.InputDecorator;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.BufferRecycler;
import java.lang.ref.SoftReference;
import java.io.Serializable;

public class JsonFactory implements Versioned, Serializable
{
    private static final long serialVersionUID = 3306684576057132431L;
    public static final String FORMAT_NAME_JSON = "JSON";
    protected static final int DEFAULT_FACTORY_FEATURE_FLAGS;
    protected static final int DEFAULT_PARSER_FEATURE_FLAGS;
    protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS;
    private static final SerializableString DEFAULT_ROOT_VALUE_SEPARATOR;
    protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef;
    protected final transient CharsToNameCanonicalizer _rootCharSymbols;
    protected final transient BytesToNameCanonicalizer _rootByteSymbols;
    protected ObjectCodec _objectCodec;
    protected int _factoryFeatures;
    protected int _parserFeatures;
    protected int _generatorFeatures;
    protected CharacterEscapes _characterEscapes;
    protected InputDecorator _inputDecorator;
    protected OutputDecorator _outputDecorator;
    protected SerializableString _rootValueSeparator;
    
    public JsonFactory() {
        this(null);
    }
    
    public JsonFactory(final ObjectCodec oc) {
        this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
        this._rootByteSymbols = BytesToNameCanonicalizer.createRoot();
        this._factoryFeatures = JsonFactory.DEFAULT_FACTORY_FEATURE_FLAGS;
        this._parserFeatures = JsonFactory.DEFAULT_PARSER_FEATURE_FLAGS;
        this._generatorFeatures = JsonFactory.DEFAULT_GENERATOR_FEATURE_FLAGS;
        this._rootValueSeparator = JsonFactory.DEFAULT_ROOT_VALUE_SEPARATOR;
        this._objectCodec = oc;
    }
    
    protected JsonFactory(final JsonFactory src, final ObjectCodec codec) {
        this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
        this._rootByteSymbols = BytesToNameCanonicalizer.createRoot();
        this._factoryFeatures = JsonFactory.DEFAULT_FACTORY_FEATURE_FLAGS;
        this._parserFeatures = JsonFactory.DEFAULT_PARSER_FEATURE_FLAGS;
        this._generatorFeatures = JsonFactory.DEFAULT_GENERATOR_FEATURE_FLAGS;
        this._rootValueSeparator = JsonFactory.DEFAULT_ROOT_VALUE_SEPARATOR;
        this._objectCodec = null;
        this._factoryFeatures = src._factoryFeatures;
        this._parserFeatures = src._parserFeatures;
        this._generatorFeatures = src._generatorFeatures;
        this._characterEscapes = src._characterEscapes;
        this._inputDecorator = src._inputDecorator;
        this._outputDecorator = src._outputDecorator;
        this._rootValueSeparator = src._rootValueSeparator;
    }
    
    public JsonFactory copy() {
        this._checkInvalidCopy(JsonFactory.class);
        return new JsonFactory(this, null);
    }
    
    protected void _checkInvalidCopy(final Class<?> exp) {
        if (this.getClass() != exp) {
            throw new IllegalStateException("Failed copy(): " + this.getClass().getName() + " (version: " + this.version() + ") does not override copy(); it has to");
        }
    }
    
    protected Object readResolve() {
        return new JsonFactory(this, this._objectCodec);
    }
    
    public boolean requiresPropertyOrdering() {
        return false;
    }
    
    public boolean canHandleBinaryNatively() {
        return false;
    }
    
    public boolean canUseCharArrays() {
        return true;
    }
    
    public boolean canUseSchema(final FormatSchema schema) {
        final String ourFormat = this.getFormatName();
        return ourFormat != null && ourFormat.equals(schema.getSchemaType());
    }
    
    public String getFormatName() {
        if (this.getClass() == JsonFactory.class) {
            return "JSON";
        }
        return null;
    }
    
    public MatchStrength hasFormat(final InputAccessor acc) throws IOException {
        if (this.getClass() == JsonFactory.class) {
            return this.hasJSONFormat(acc);
        }
        return null;
    }
    
    public boolean requiresCustomCodec() {
        return false;
    }
    
    protected MatchStrength hasJSONFormat(final InputAccessor acc) throws IOException {
        return ByteSourceJsonBootstrapper.hasJSONFormat(acc);
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    public final JsonFactory configure(final Feature f, final boolean state) {
        return state ? this.enable(f) : this.disable(f);
    }
    
    public JsonFactory enable(final Feature f) {
        this._factoryFeatures |= f.getMask();
        return this;
    }
    
    public JsonFactory disable(final Feature f) {
        this._factoryFeatures &= ~f.getMask();
        return this;
    }
    
    public final boolean isEnabled(final Feature f) {
        return (this._factoryFeatures & f.getMask()) != 0x0;
    }
    
    public final JsonFactory configure(final JsonParser.Feature f, final boolean state) {
        return state ? this.enable(f) : this.disable(f);
    }
    
    public JsonFactory enable(final JsonParser.Feature f) {
        this._parserFeatures |= f.getMask();
        return this;
    }
    
    public JsonFactory disable(final JsonParser.Feature f) {
        this._parserFeatures &= ~f.getMask();
        return this;
    }
    
    public final boolean isEnabled(final JsonParser.Feature f) {
        return (this._parserFeatures & f.getMask()) != 0x0;
    }
    
    public InputDecorator getInputDecorator() {
        return this._inputDecorator;
    }
    
    public JsonFactory setInputDecorator(final InputDecorator d) {
        this._inputDecorator = d;
        return this;
    }
    
    public final JsonFactory configure(final JsonGenerator.Feature f, final boolean state) {
        return state ? this.enable(f) : this.disable(f);
    }
    
    public JsonFactory enable(final JsonGenerator.Feature f) {
        this._generatorFeatures |= f.getMask();
        return this;
    }
    
    public JsonFactory disable(final JsonGenerator.Feature f) {
        this._generatorFeatures &= ~f.getMask();
        return this;
    }
    
    public final boolean isEnabled(final JsonGenerator.Feature f) {
        return (this._generatorFeatures & f.getMask()) != 0x0;
    }
    
    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }
    
    public JsonFactory setCharacterEscapes(final CharacterEscapes esc) {
        this._characterEscapes = esc;
        return this;
    }
    
    public OutputDecorator getOutputDecorator() {
        return this._outputDecorator;
    }
    
    public JsonFactory setOutputDecorator(final OutputDecorator d) {
        this._outputDecorator = d;
        return this;
    }
    
    public JsonFactory setRootValueSeparator(final String sep) {
        this._rootValueSeparator = ((sep == null) ? null : new SerializedString(sep));
        return this;
    }
    
    public String getRootValueSeparator() {
        return (this._rootValueSeparator == null) ? null : this._rootValueSeparator.getValue();
    }
    
    public JsonFactory setCodec(final ObjectCodec oc) {
        this._objectCodec = oc;
        return this;
    }
    
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }
    
    public JsonParser createParser(final File f) throws IOException, JsonParseException {
        final IOContext ctxt = this._createContext(f, true);
        final InputStream in = new FileInputStream(f);
        return this._createParser(this._decorate(in, ctxt), ctxt);
    }
    
    public JsonParser createParser(final URL url) throws IOException, JsonParseException {
        final IOContext ctxt = this._createContext(url, true);
        final InputStream in = this._optimizedStreamFromURL(url);
        return this._createParser(this._decorate(in, ctxt), ctxt);
    }
    
    public JsonParser createParser(final InputStream in) throws IOException, JsonParseException {
        final IOContext ctxt = this._createContext(in, false);
        return this._createParser(this._decorate(in, ctxt), ctxt);
    }
    
    public JsonParser createParser(final Reader r) throws IOException, JsonParseException {
        final IOContext ctxt = this._createContext(r, false);
        return this._createParser(this._decorate(r, ctxt), ctxt);
    }
    
    public JsonParser createParser(final byte[] data) throws IOException, JsonParseException {
        final IOContext ctxt = this._createContext(data, true);
        if (this._inputDecorator != null) {
            final InputStream in = this._inputDecorator.decorate(ctxt, data, 0, data.length);
            if (in != null) {
                return this._createParser(in, ctxt);
            }
        }
        return this._createParser(data, 0, data.length, ctxt);
    }
    
    public JsonParser createParser(final byte[] data, final int offset, final int len) throws IOException, JsonParseException {
        final IOContext ctxt = this._createContext(data, true);
        if (this._inputDecorator != null) {
            final InputStream in = this._inputDecorator.decorate(ctxt, data, offset, len);
            if (in != null) {
                return this._createParser(in, ctxt);
            }
        }
        return this._createParser(data, offset, len, ctxt);
    }
    
    public JsonParser createParser(final String content) throws IOException, JsonParseException {
        final int strLen = content.length();
        if (this._inputDecorator != null || strLen > 32768 || !this.canUseCharArrays()) {
            return this.createParser(new StringReader(content));
        }
        final IOContext ctxt = this._createContext(content, true);
        final char[] buf = ctxt.allocTokenBuffer(strLen);
        content.getChars(0, strLen, buf, 0);
        return this._createParser(buf, 0, strLen, ctxt, true);
    }
    
    public JsonParser createParser(final char[] content) throws IOException {
        return this.createParser(content, 0, content.length);
    }
    
    public JsonParser createParser(final char[] content, final int offset, final int len) throws IOException {
        if (this._inputDecorator != null) {
            return this.createParser(new CharArrayReader(content, offset, len));
        }
        return this._createParser(content, offset, len, this._createContext(content, true), false);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final File f) throws IOException, JsonParseException {
        return this.createParser(f);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final URL url) throws IOException, JsonParseException {
        return this.createParser(url);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final InputStream in) throws IOException, JsonParseException {
        return this.createParser(in);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final Reader r) throws IOException, JsonParseException {
        return this.createParser(r);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final byte[] data) throws IOException, JsonParseException {
        return this.createParser(data);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final byte[] data, final int offset, final int len) throws IOException, JsonParseException {
        return this.createParser(data, offset, len);
    }
    
    @Deprecated
    public JsonParser createJsonParser(final String content) throws IOException, JsonParseException {
        return this.createParser(content);
    }
    
    public JsonGenerator createGenerator(final OutputStream out, final JsonEncoding enc) throws IOException {
        final IOContext ctxt = this._createContext(out, false);
        ctxt.setEncoding(enc);
        if (enc == JsonEncoding.UTF8) {
            return this._createUTF8Generator(this._decorate(out, ctxt), ctxt);
        }
        final Writer w = this._createWriter(out, enc, ctxt);
        return this._createGenerator(this._decorate(w, ctxt), ctxt);
    }
    
    public JsonGenerator createGenerator(final OutputStream out) throws IOException {
        return this.createGenerator(out, JsonEncoding.UTF8);
    }
    
    public JsonGenerator createGenerator(final Writer w) throws IOException {
        final IOContext ctxt = this._createContext(w, false);
        return this._createGenerator(this._decorate(w, ctxt), ctxt);
    }
    
    public JsonGenerator createGenerator(final File f, final JsonEncoding enc) throws IOException {
        final OutputStream out = new FileOutputStream(f);
        final IOContext ctxt = this._createContext(out, true);
        ctxt.setEncoding(enc);
        if (enc == JsonEncoding.UTF8) {
            return this._createUTF8Generator(this._decorate(out, ctxt), ctxt);
        }
        final Writer w = this._createWriter(out, enc, ctxt);
        return this._createGenerator(this._decorate(w, ctxt), ctxt);
    }
    
    @Deprecated
    public JsonGenerator createJsonGenerator(final OutputStream out, final JsonEncoding enc) throws IOException {
        return this.createGenerator(out, enc);
    }
    
    @Deprecated
    public JsonGenerator createJsonGenerator(final Writer out) throws IOException {
        return this.createGenerator(out);
    }
    
    @Deprecated
    public JsonGenerator createJsonGenerator(final OutputStream out) throws IOException {
        return this.createGenerator(out, JsonEncoding.UTF8);
    }
    
    @Deprecated
    public JsonGenerator createJsonGenerator(final File f, final JsonEncoding enc) throws IOException {
        return this.createGenerator(f, enc);
    }
    
    protected JsonParser _createParser(final InputStream in, final IOContext ctxt) throws IOException {
        return new ByteSourceJsonBootstrapper(ctxt, in).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, this._factoryFeatures);
    }
    
    protected JsonParser _createParser(final Reader r, final IOContext ctxt) throws IOException {
        return new ReaderBasedJsonParser(ctxt, this._parserFeatures, r, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures));
    }
    
    protected JsonParser _createParser(final char[] data, final int offset, final int len, final IOContext ctxt, final boolean recyclable) throws IOException {
        return new ReaderBasedJsonParser(ctxt, this._parserFeatures, null, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures), data, offset, offset + len, recyclable);
    }
    
    protected JsonParser _createParser(final byte[] data, final int offset, final int len, final IOContext ctxt) throws IOException {
        return new ByteSourceJsonBootstrapper(ctxt, data, offset, len).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, this._factoryFeatures);
    }
    
    protected JsonGenerator _createGenerator(final Writer out, final IOContext ctxt) throws IOException {
        final WriterBasedJsonGenerator gen = new WriterBasedJsonGenerator(ctxt, this._generatorFeatures, this._objectCodec, out);
        if (this._characterEscapes != null) {
            gen.setCharacterEscapes(this._characterEscapes);
        }
        final SerializableString rootSep = this._rootValueSeparator;
        if (rootSep != JsonFactory.DEFAULT_ROOT_VALUE_SEPARATOR) {
            gen.setRootValueSeparator(rootSep);
        }
        return gen;
    }
    
    protected JsonGenerator _createUTF8Generator(final OutputStream out, final IOContext ctxt) throws IOException {
        final UTF8JsonGenerator gen = new UTF8JsonGenerator(ctxt, this._generatorFeatures, this._objectCodec, out);
        if (this._characterEscapes != null) {
            gen.setCharacterEscapes(this._characterEscapes);
        }
        final SerializableString rootSep = this._rootValueSeparator;
        if (rootSep != JsonFactory.DEFAULT_ROOT_VALUE_SEPARATOR) {
            gen.setRootValueSeparator(rootSep);
        }
        return gen;
    }
    
    protected Writer _createWriter(final OutputStream out, final JsonEncoding enc, final IOContext ctxt) throws IOException {
        if (enc == JsonEncoding.UTF8) {
            return new UTF8Writer(ctxt, out);
        }
        return new OutputStreamWriter(out, enc.getJavaName());
    }
    
    protected final InputStream _decorate(final InputStream in, final IOContext ctxt) throws IOException {
        if (this._inputDecorator != null) {
            final InputStream in2 = this._inputDecorator.decorate(ctxt, in);
            if (in2 != null) {
                return in2;
            }
        }
        return in;
    }
    
    protected final Reader _decorate(final Reader in, final IOContext ctxt) throws IOException {
        if (this._inputDecorator != null) {
            final Reader in2 = this._inputDecorator.decorate(ctxt, in);
            if (in2 != null) {
                return in2;
            }
        }
        return in;
    }
    
    protected final OutputStream _decorate(final OutputStream out, final IOContext ctxt) throws IOException {
        if (this._outputDecorator != null) {
            final OutputStream out2 = this._outputDecorator.decorate(ctxt, out);
            if (out2 != null) {
                return out2;
            }
        }
        return out;
    }
    
    protected final Writer _decorate(final Writer out, final IOContext ctxt) throws IOException {
        if (this._outputDecorator != null) {
            final Writer out2 = this._outputDecorator.decorate(ctxt, out);
            if (out2 != null) {
                return out2;
            }
        }
        return out;
    }
    
    public BufferRecycler _getBufferRecycler() {
        final SoftReference<BufferRecycler> ref = JsonFactory._recyclerRef.get();
        BufferRecycler br = (ref == null) ? null : ref.get();
        if (br == null) {
            br = new BufferRecycler();
            JsonFactory._recyclerRef.set(new SoftReference<BufferRecycler>(br));
        }
        return br;
    }
    
    protected IOContext _createContext(final Object srcRef, final boolean resourceManaged) {
        return new IOContext(this._getBufferRecycler(), srcRef, resourceManaged);
    }
    
    protected InputStream _optimizedStreamFromURL(final URL url) throws IOException {
        if ("file".equals(url.getProtocol())) {
            final String host = url.getHost();
            if (host == null || host.length() == 0) {
                final String path = url.getPath();
                if (path.indexOf(37) < 0) {
                    return new FileInputStream(url.getPath());
                }
            }
        }
        return url.openStream();
    }
    
    static {
        DEFAULT_FACTORY_FEATURE_FLAGS = Feature.collectDefaults();
        DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
        DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
        DEFAULT_ROOT_VALUE_SEPARATOR = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
        _recyclerRef = new ThreadLocal<SoftReference<BufferRecycler>>();
    }
    
    public enum Feature
    {
        INTERN_FIELD_NAMES(true), 
        CANONICALIZE_FIELD_NAMES(true), 
        FAIL_ON_SYMBOL_HASH_OVERFLOW(true);
        
        private final boolean _defaultState;
        
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
        }
        
        public boolean enabledByDefault() {
            return this._defaultState;
        }
        
        public boolean enabledIn(final int flags) {
            return (flags & this.getMask()) != 0x0;
        }
        
        public int getMask() {
            return 1 << this.ordinal();
        }
    }
}
