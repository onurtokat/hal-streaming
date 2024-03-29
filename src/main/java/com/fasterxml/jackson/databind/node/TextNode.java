// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.Base64Variants;
import java.io.IOException;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonToken;

public class TextNode extends ValueNode
{
    static final TextNode EMPTY_STRING_NODE;
    protected final String _value;
    
    public TextNode(final String v) {
        this._value = v;
    }
    
    public static TextNode valueOf(final String v) {
        if (v == null) {
            return null;
        }
        if (v.length() == 0) {
            return TextNode.EMPTY_STRING_NODE;
        }
        return new TextNode(v);
    }
    
    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.STRING;
    }
    
    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_STRING;
    }
    
    @Override
    public String textValue() {
        return this._value;
    }
    
    public byte[] getBinaryValue(final Base64Variant b64variant) throws IOException {
        final ByteArrayBuilder builder = new ByteArrayBuilder(100);
        final String str = this._value;
        int ptr = 0;
        final int len = str.length();
    Label_0406:
        while (ptr < len) {
            char ch;
            do {
                ch = str.charAt(ptr++);
                if (ptr >= len) {
                    break Label_0406;
                }
            } while (ch <= ' ');
            int bits = b64variant.decodeBase64Char(ch);
            if (bits < 0) {
                this._reportInvalidBase64(b64variant, ch, 0);
            }
            int decodedData = bits;
            if (ptr >= len) {
                this._reportBase64EOF();
            }
            ch = str.charAt(ptr++);
            bits = b64variant.decodeBase64Char(ch);
            if (bits < 0) {
                this._reportInvalidBase64(b64variant, ch, 1);
            }
            decodedData = (decodedData << 6 | bits);
            if (ptr >= len) {
                if (!b64variant.usesPadding()) {
                    decodedData >>= 4;
                    builder.append(decodedData);
                    break;
                }
                this._reportBase64EOF();
            }
            ch = str.charAt(ptr++);
            bits = b64variant.decodeBase64Char(ch);
            if (bits < 0) {
                if (bits != -2) {
                    this._reportInvalidBase64(b64variant, ch, 2);
                }
                if (ptr >= len) {
                    this._reportBase64EOF();
                }
                ch = str.charAt(ptr++);
                if (!b64variant.usesPaddingChar(ch)) {
                    this._reportInvalidBase64(b64variant, ch, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
                }
                decodedData >>= 4;
                builder.append(decodedData);
            }
            else {
                decodedData = (decodedData << 6 | bits);
                if (ptr >= len) {
                    if (!b64variant.usesPadding()) {
                        decodedData >>= 2;
                        builder.appendTwoBytes(decodedData);
                        break;
                    }
                    this._reportBase64EOF();
                }
                ch = str.charAt(ptr++);
                bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    if (bits != -2) {
                        this._reportInvalidBase64(b64variant, ch, 3);
                    }
                    decodedData >>= 2;
                    builder.appendTwoBytes(decodedData);
                }
                else {
                    decodedData = (decodedData << 6 | bits);
                    builder.appendThreeBytes(decodedData);
                }
            }
        }
        return builder.toByteArray();
    }
    
    @Override
    public byte[] binaryValue() throws IOException {
        return this.getBinaryValue(Base64Variants.getDefaultVariant());
    }
    
    @Override
    public String asText() {
        return this._value;
    }
    
    @Override
    public String asText(final String defaultValue) {
        return (this._value == null) ? defaultValue : this._value;
    }
    
    @Override
    public boolean asBoolean(final boolean defaultValue) {
        if (this._value != null) {
            final String v = this._value.trim();
            if ("true".equals(v)) {
                return true;
            }
            if ("false".equals(v)) {
                return false;
            }
        }
        return defaultValue;
    }
    
    @Override
    public int asInt(final int defaultValue) {
        return NumberInput.parseAsInt(this._value, defaultValue);
    }
    
    @Override
    public long asLong(final long defaultValue) {
        return NumberInput.parseAsLong(this._value, defaultValue);
    }
    
    @Override
    public double asDouble(final double defaultValue) {
        return NumberInput.parseAsDouble(this._value, defaultValue);
    }
    
    @Override
    public final void serialize(final JsonGenerator jg, final SerializerProvider provider) throws IOException {
        if (this._value == null) {
            jg.writeNull();
        }
        else {
            jg.writeString(this._value);
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o != null && o instanceof TextNode && ((TextNode)o)._value.equals(this._value));
    }
    
    @Override
    public int hashCode() {
        return this._value.hashCode();
    }
    
    @Override
    public String toString() {
        int len = this._value.length();
        len = len + 2 + (len >> 4);
        final StringBuilder sb = new StringBuilder(len);
        appendQuoted(sb, this._value);
        return sb.toString();
    }
    
    protected static void appendQuoted(final StringBuilder sb, final String content) {
        sb.append('\"');
        CharTypes.appendQuoted(sb, content);
        sb.append('\"');
    }
    
    protected void _reportInvalidBase64(final Base64Variant b64variant, final char ch, final int bindex) throws JsonParseException {
        this._reportInvalidBase64(b64variant, ch, bindex, null);
    }
    
    protected void _reportInvalidBase64(final Base64Variant b64variant, final char ch, final int bindex, final String msg) throws JsonParseException {
        String base;
        if (ch <= ' ') {
            base = "Illegal white space character (code 0x" + Integer.toHexString(ch) + ") as character #" + (bindex + 1) + " of 4-char base64 unit: can only used between units";
        }
        else if (b64variant.usesPaddingChar(ch)) {
            base = "Unexpected padding character ('" + b64variant.getPaddingChar() + "') as character #" + (bindex + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
        }
        else if (!Character.isDefined(ch) || Character.isISOControl(ch)) {
            base = "Illegal character (code 0x" + Integer.toHexString(ch) + ") in base64 content";
        }
        else {
            base = "Illegal character '" + ch + "' (code 0x" + Integer.toHexString(ch) + ") in base64 content";
        }
        if (msg != null) {
            base = base + ": " + msg;
        }
        throw new JsonParseException(base, JsonLocation.NA);
    }
    
    protected void _reportBase64EOF() throws JsonParseException {
        throw new JsonParseException("Unexpected end-of-String when base64 content", JsonLocation.NA);
    }
    
    static {
        EMPTY_STRING_NODE = new TextNode("");
    }
}
