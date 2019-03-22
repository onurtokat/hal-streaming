// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format.extra;

import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.helpers.HexStringFormatAttribute;

public final class MD5FormatAttribute extends HexStringFormatAttribute
{
    private static final FormatAttribute instance;
    
    private MD5FormatAttribute() {
        super("md5", 32);
    }
    
    public static FormatAttribute getInstance() {
        return MD5FormatAttribute.instance;
    }
    
    static {
        instance = new MD5FormatAttribute();
    }
}
