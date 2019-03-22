// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library.format;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.format.draftv3.UTCMillisecAttribute;
import com.github.fge.jsonschema.format.draftv3.TimeAttribute;
import com.github.fge.jsonschema.format.draftv3.PhoneAttribute;
import com.github.fge.jsonschema.format.helpers.IPv4FormatAttribute;
import com.github.fge.jsonschema.format.helpers.SharedHostNameAttribute;
import com.github.fge.jsonschema.format.draftv3.DateAttribute;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class DraftV3FormatAttributesDictionary
{
    private static final Dictionary<FormatAttribute> DICTIONARY;
    
    public static Dictionary<FormatAttribute> get() {
        return DraftV3FormatAttributesDictionary.DICTIONARY;
    }
    
    static {
        final DictionaryBuilder<FormatAttribute> builder = Dictionary.newBuilder();
        builder.addAll(CommonFormatAttributesDictionary.get());
        String name = "date";
        FormatAttribute attribute = DateAttribute.getInstance();
        builder.addEntry(name, attribute);
        name = "host-name";
        attribute = new SharedHostNameAttribute("host-name");
        builder.addEntry(name, attribute);
        name = "ip-address";
        attribute = new IPv4FormatAttribute(name);
        builder.addEntry(name, attribute);
        name = "phone";
        attribute = PhoneAttribute.getInstance();
        builder.addEntry(name, attribute);
        name = "time";
        attribute = TimeAttribute.getInstance();
        builder.addEntry(name, attribute);
        name = "utc-millisec";
        attribute = UTCMillisecAttribute.getInstance();
        builder.addEntry(name, attribute);
        DICTIONARY = builder.freeze();
    }
}
