// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.extra;

import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.helpers.HexStringFormatAttribute;

public final class SHA512FormatAttribute extends HexStringFormatAttribute
{
    private static final FormatAttribute instance;
    
    private SHA512FormatAttribute() {
        super("sha512", 128);
    }
    
    public static FormatAttribute getInstance() {
        return SHA512FormatAttribute.instance;
    }
    
    static {
        instance = new SHA512FormatAttribute();
    }
}
