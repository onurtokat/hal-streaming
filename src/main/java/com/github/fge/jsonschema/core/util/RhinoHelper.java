// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class RhinoHelper
{
    private static final String jsAsString = "function regexIsValid(re){    try {         new RegExp(re);         return true;    } catch (e) {        return false;    }}function regMatch(re, input){    return new RegExp(re).test(input);}";
    private static final Scriptable SCOPE;
    private static final Function REGEX_IS_VALID;
    private static final Function REG_MATCH;
    
    public static boolean regexIsValid(final String regex) {
        final Context context = Context.enter();
        try {
            return (boolean)RhinoHelper.REGEX_IS_VALID.call(context, RhinoHelper.SCOPE, RhinoHelper.SCOPE, new Object[] { regex });
        }
        finally {
            Context.exit();
        }
    }
    
    public static boolean regMatch(final String regex, final String input) {
        final Context context = Context.enter();
        try {
            return (boolean)RhinoHelper.REG_MATCH.call(context, RhinoHelper.SCOPE, RhinoHelper.SCOPE, new Object[] { regex, input });
        }
        finally {
            Context.exit();
        }
    }
    
    static {
        final Context ctx = Context.enter();
        try {
            ctx.evaluateString(SCOPE = ctx.initStandardObjects(null, false), "function regexIsValid(re){    try {         new RegExp(re);         return true;    } catch (e) {        return false;    }}function regMatch(re, input){    return new RegExp(re).test(input);}", "re", 1, null);
            REGEX_IS_VALID = (Function)RhinoHelper.SCOPE.get("regexIsValid", RhinoHelper.SCOPE);
            REG_MATCH = (Function)RhinoHelper.SCOPE.get("regMatch", RhinoHelper.SCOPE);
        }
        finally {
            Context.exit();
        }
    }
}
