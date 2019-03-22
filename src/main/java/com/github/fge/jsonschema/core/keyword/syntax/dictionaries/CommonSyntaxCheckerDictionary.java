// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.dictionaries;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.common.EnumSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.URISyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.common.PatternSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.common.PatternPropertiesSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.common.ExclusiveMaximumSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.common.ExclusiveMinimumSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.TypeOnlySyntaxChecker;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.PositiveIntegerSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.common.AdditionalSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class CommonSyntaxCheckerDictionary
{
    private static final Dictionary<SyntaxChecker> DICTIONARY;
    
    public static Dictionary<SyntaxChecker> get() {
        return CommonSyntaxCheckerDictionary.DICTIONARY;
    }
    
    static {
        final DictionaryBuilder<SyntaxChecker> dict = Dictionary.newBuilder();
        String keyword = "additionalItems";
        SyntaxChecker checker = new AdditionalSyntaxChecker(keyword);
        dict.addEntry(keyword, checker);
        keyword = "minItems";
        checker = new PositiveIntegerSyntaxChecker(keyword);
        dict.addEntry(keyword, checker);
        keyword = "maxItems";
        checker = new PositiveIntegerSyntaxChecker(keyword);
        dict.addEntry(keyword, checker);
        keyword = "uniqueItems";
        checker = new TypeOnlySyntaxChecker(keyword, NodeType.BOOLEAN, new NodeType[0]);
        dict.addEntry(keyword, checker);
        keyword = "minimum";
        checker = new TypeOnlySyntaxChecker(keyword, NodeType.INTEGER, new NodeType[] { NodeType.NUMBER });
        dict.addEntry(keyword, checker);
        keyword = "exclusiveMinimum";
        checker = ExclusiveMinimumSyntaxChecker.getInstance();
        dict.addEntry(keyword, checker);
        keyword = "maximum";
        checker = new TypeOnlySyntaxChecker(keyword, NodeType.INTEGER, new NodeType[] { NodeType.NUMBER });
        dict.addEntry(keyword, checker);
        keyword = "exclusiveMaximum";
        checker = ExclusiveMaximumSyntaxChecker.getInstance();
        dict.addEntry(keyword, checker);
        keyword = "additionalProperties";
        checker = new AdditionalSyntaxChecker(keyword);
        dict.addEntry(keyword, checker);
        keyword = "patternProperties";
        checker = PatternPropertiesSyntaxChecker.getInstance();
        dict.addEntry(keyword, checker);
        keyword = "required";
        checker = new TypeOnlySyntaxChecker(keyword, NodeType.BOOLEAN, new NodeType[0]);
        dict.addEntry(keyword, checker);
        keyword = "minLength";
        checker = new PositiveIntegerSyntaxChecker(keyword);
        dict.addEntry(keyword, checker);
        keyword = "maxLength";
        checker = new PositiveIntegerSyntaxChecker(keyword);
        dict.addEntry(keyword, checker);
        keyword = "pattern";
        checker = PatternSyntaxChecker.getInstance();
        dict.addEntry(keyword, checker);
        keyword = "$schema";
        checker = new URISyntaxChecker(keyword);
        dict.addEntry(keyword, checker);
        keyword = "$ref";
        checker = new URISyntaxChecker(keyword);
        dict.addEntry(keyword, checker);
        keyword = "id";
        checker = new URISyntaxChecker(keyword);
        dict.addEntry(keyword, checker);
        keyword = "description";
        checker = new TypeOnlySyntaxChecker(keyword, NodeType.STRING, new NodeType[0]);
        dict.addEntry(keyword, checker);
        keyword = "title";
        checker = new TypeOnlySyntaxChecker(keyword, NodeType.STRING, new NodeType[0]);
        dict.addEntry(keyword, checker);
        keyword = "enum";
        checker = EnumSyntaxChecker.getInstance();
        dict.addEntry(keyword, checker);
        keyword = "format";
        checker = new TypeOnlySyntaxChecker(keyword, NodeType.STRING, new NodeType[0]);
        dict.addEntry(keyword, checker);
        keyword = "default";
        checker = new TypeOnlySyntaxChecker(keyword, NodeType.ARRAY, NodeType.values());
        dict.addEntry(keyword, checker);
        DICTIONARY = dict.freeze();
    }
}
