// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.extra;

import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.helpers.HexStringFormatAttribute;

public final class SHA256FormatAttribute extends HexStringFormatAttribute
{
    private static final FormatAttribute instance;
    
    private SHA256FormatAttribute() {
        super("sha256", 64);
    }
    
    public static FormatAttribute getInstance() {
        return SHA256FormatAttribute.instance;
    }
    
    static {
        instance = new SHA256FormatAttribute();
    }
}
