// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library.format;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.format.common.URIAttribute;
import com.github.fge.jsonschema.format.common.RegexAttribute;
import com.github.fge.jsonschema.format.common.IPv6Attribute;
import com.github.fge.jsonschema.format.common.EmailAttribute;
import com.github.fge.jsonschema.format.common.DateTimeAttribute;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class CommonFormatAttributesDictionary
{
    private static final Dictionary<FormatAttribute> DICTIONARY;
    
    public static Dictionary<FormatAttribute> get() {
        return CommonFormatAttributesDictionary.DICTIONARY;
    }
    
    static {
        final DictionaryBuilder<FormatAttribute> builder = Dictionary.newBuilder();
        builder.addAll(ExtraFormatsDictionary.get());
        String name = "date-time";
        FormatAttribute attribute = DateTimeAttribute.getInstance();
        builder.addEntry(name, attribute);
        name = "email";
        attribute = EmailAttribute.getInstance();
        builder.addEntry(name, attribute);
        name = "ipv6";
        attribute = IPv6Attribute.getInstance();
        builder.addEntry(name, attribute);
        name = "regex";
        attribute = RegexAttribute.getInstance();
        builder.addEntry(name, attribute);
        name = "uri";
        attribute = URIAttribute.getInstance();
        builder.addEntry(name, attribute);
        DICTIONARY = builder.freeze();
    }
}
