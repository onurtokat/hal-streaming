// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.dictionaries;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4.DraftV4TypeSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4.DefinitionsSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4.NotSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.SchemaArraySyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4.DraftV4DependenciesSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4.RequiredSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4.DraftV4PropertiesSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.PositiveIntegerSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.DivisorSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4.DraftV4ItemsSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class DraftV4SyntaxCheckerDictionary
{
    private static final Dictionary<SyntaxChecker> DICTIONARY;
    
    public static Dictionary<SyntaxChecker> get() {
        return DraftV4SyntaxCheckerDictionary.DICTIONARY;
    }
    
    static {
        final DictionaryBuilder<SyntaxChecker> builder = Dictionary.newBuilder();
        builder.addAll(CommonSyntaxCheckerDictionary.get());
        String keyword = "items";
        SyntaxChecker checker = DraftV4ItemsSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "multipleOf";
        checker = new DivisorSyntaxChecker(keyword);
        builder.addEntry(keyword, checker);
        keyword = "minProperties";
        checker = new PositiveIntegerSyntaxChecker(keyword);
        builder.addEntry(keyword, checker);
        keyword = "maxProperties";
        checker = new PositiveIntegerSyntaxChecker(keyword);
        builder.addEntry(keyword, checker);
        keyword = "properties";
        checker = DraftV4PropertiesSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "required";
        checker = RequiredSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "dependencies";
        checker = DraftV4DependenciesSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "allOf";
        checker = new SchemaArraySyntaxChecker(keyword);
        builder.addEntry(keyword, checker);
        keyword = "anyOf";
        checker = new SchemaArraySyntaxChecker(keyword);
        builder.addEntry(keyword, checker);
        keyword = "oneOf";
        checker = new SchemaArraySyntaxChecker(keyword);
        builder.addEntry(keyword, checker);
        keyword = "not";
        checker = NotSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "definitions";
        checker = DefinitionsSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "type";
        checker = DraftV4TypeSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        DICTIONARY = builder.freeze();
    }
}
