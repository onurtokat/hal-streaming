// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library.digest;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.keyword.digest.helpers.NullDigester;
import com.github.fge.jsonschema.keyword.digest.common.AdditionalPropertiesDigester;
import com.github.fge.jsonschema.keyword.digest.common.MaximumDigester;
import com.github.fge.jsonschema.keyword.digest.common.MinimumDigester;
import com.github.fge.jsonschema.keyword.digest.helpers.SimpleDigester;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.common.AdditionalItemsDigester;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class CommonDigesterDictionary
{
    private static final Dictionary<Digester> DICTIONARY;
    
    public static Dictionary<Digester> get() {
        return CommonDigesterDictionary.DICTIONARY;
    }
    
    static {
        final DictionaryBuilder<Digester> builder = Dictionary.newBuilder();
        String keyword = "additionalItems";
        Digester digester = AdditionalItemsDigester.getInstance();
        builder.addEntry(keyword, digester);
        keyword = "minItems";
        digester = new SimpleDigester(keyword, NodeType.ARRAY, new NodeType[0]);
        builder.addEntry(keyword, digester);
        keyword = "maxItems";
        digester = new SimpleDigester(keyword, NodeType.ARRAY, new NodeType[0]);
        builder.addEntry(keyword, digester);
        keyword = "uniqueItems";
        digester = new SimpleDigester(keyword, NodeType.ARRAY, new NodeType[0]);
        builder.addEntry(keyword, digester);
        keyword = "minimum";
        digester = MinimumDigester.getInstance();
        builder.addEntry(keyword, digester);
        keyword = "maximum";
        digester = MaximumDigester.getInstance();
        builder.addEntry(keyword, digester);
        keyword = "additionalProperties";
        digester = AdditionalPropertiesDigester.getInstance();
        builder.addEntry(keyword, digester);
        keyword = "minLength";
        digester = new SimpleDigester(keyword, NodeType.STRING, new NodeType[0]);
        builder.addEntry(keyword, digester);
        keyword = "maxLength";
        digester = new SimpleDigester(keyword, NodeType.STRING, new NodeType[0]);
        builder.addEntry(keyword, digester);
        keyword = "pattern";
        digester = new NullDigester(keyword, NodeType.STRING, new NodeType[0]);
        builder.addEntry(keyword, digester);
        keyword = "enum";
        digester = new SimpleDigester(keyword, NodeType.ARRAY, NodeType.values());
        builder.addEntry(keyword, digester);
        DICTIONARY = builder.freeze();
    }
}
