// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.extra;

import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.helpers.HexStringFormatAttribute;

public final class SHA1FormatAttribute extends HexStringFormatAttribute
{
    private static final FormatAttribute instance;
    
    private SHA1FormatAttribute() {
        super("sha1", 40);
    }
    
    public static FormatAttribute getInstance() {
        return SHA1FormatAttribute.instance;
    }
    
    static {
        instance = new SHA1FormatAttribute();
    }
}
