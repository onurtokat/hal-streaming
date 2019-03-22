// 
// Decompiled by Procyon v0.5.30
// 

package com.squareup.okhttp.internal.framed;

import okio.ByteString;

public final class Header
{
    public static final ByteString RESPONSE_STATUS;
    public static final ByteString TARGET_METHOD;
    public static final ByteString TARGET_PATH;
    public static final ByteString TARGET_SCHEME;
    public static final ByteString TARGET_AUTHORITY;
    public static final ByteString TARGET_HOST;
    public static final ByteString VERSION;
    public final ByteString name;
    public final ByteString value;
    final int hpackSize;
    
    public Header(final String name, final String value) {
        this(ByteString.encodeUtf8(name), ByteString.encodeUtf8(value));
    }
    
    public Header(final ByteString name, final String value) {
        this(name, ByteString.encodeUtf8(value));
    }
    
    public Header(final ByteString name, final ByteString value) {
        this.name = name;
        this.value = value;
        this.hpackSize = 32 + name.size() + value.size();
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other instanceof Header) {
            final Header that = (Header)other;
            return this.name.equals(that.name) && this.value.equals(that.value);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.value.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %s", this.name.utf8(), this.value.utf8());
    }
    
    static {
        RESPONSE_STATUS = ByteString.encodeUtf8(":status");
        TARGET_METHOD = ByteString.encodeUtf8(":method");
        TARGET_PATH = ByteString.encodeUtf8(":path");
        TARGET_SCHEME = ByteString.encodeUtf8(":scheme");
        TARGET_AUTHORITY = ByteString.encodeUtf8(":authority");
        TARGET_HOST = ByteString.encodeUtf8(":host");
        VERSION = ByteString.encodeUtf8(":version");
    }
}
