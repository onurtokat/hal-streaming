// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Set;
import com.github.fge.jsonschema.core.util.RhinoHelper;
import com.google.common.collect.Ordering;
import java.util.Iterator;
import com.google.common.collect.Sets;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.SchemaMapSyntaxChecker;

public final class PatternPropertiesSyntaxChecker extends SchemaMapSyntaxChecker
{
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return PatternPropertiesSyntaxChecker.INSTANCE;
    }
    
    private PatternPropertiesSyntaxChecker() {
        super("patternProperties");
    }
    
    @Override
    protected void extraChecks(final ProcessingReport report, final MessageBundle bundle, final SchemaTree tree) throws ProcessingException {
        final Set<String> set = (Set<String>)Sets.newHashSet((Iterator<?>)this.getNode(tree).fieldNames());
        for (final String s : Ordering.natural().sortedCopy(set)) {
            if (!RhinoHelper.regexIsValid(s)) {
                report.error(this.newMsg(tree, bundle, "common.patternProperties.member.notRegex").putArgument("propertyName", s));
            }
        }
    }
    
    static {
        INSTANCE = new PatternPropertiesSyntaxChecker();
    }
}
