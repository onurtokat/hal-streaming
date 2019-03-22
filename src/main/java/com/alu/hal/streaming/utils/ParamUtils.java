// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.utils;

import com.alu.hal.streaming.hive.model.ParamType;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import com.alu.hal.streaming.hive.model.ScriptEngineSingleton;
import com.alu.hal.streaming.hive.model.ParamMetaData;
import com.alu.hal.streaming.exception.ParameterFormatException;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class ParamUtils
{
    private static Logger LOG;
    private static final Pattern DIGIT_PATTERN;
    private static final Pattern GROUP_WITH_DOT_PATTERN;
    private static String LAST_DOT_REGEX;
    
    public static List<String> getIndexes(final String parameterName, final Pattern patternWithIndexGroups) {
        final Matcher indexMatcher = patternWithIndexGroups.matcher(parameterName);
        if (!indexMatcher.find()) {
            return Collections.emptyList();
        }
        final List<String> indexes = new ArrayList<String>(indexMatcher.groupCount());
        for (int groupId = 1; groupId <= indexMatcher.groupCount(); ++groupId) {
            indexes.add(indexMatcher.group(groupId));
        }
        return indexes;
    }
    
    public static String[] splitAtLatestDot(final String parameterWithPath) {
        final String[] parameterPathAndNameSplitted = parameterWithPath.split(ParamUtils.LAST_DOT_REGEX);
        final StringBuilder sb = new StringBuilder();
        final String[] array = parameterPathAndNameSplitted;
        final int n = 0;
        array[n] = sb.append(array[n]).append(".").toString();
        if (parameterPathAndNameSplitted.length != 2) {
            throw new ParameterFormatException(parameterWithPath + "cannot be splitted in path and parameter name");
        }
        return parameterPathAndNameSplitted;
    }
    
    public static int getIndexesCount(final String parameterName) {
        int indexCount = 0;
        final Matcher indexMatcher = ParamUtils.DIGIT_PATTERN.matcher(parameterName);
        while (indexMatcher.find()) {
            ++indexCount;
        }
        return indexCount;
    }
    
    public static String getGroupName(final String parameterName) {
        final int lastdotIdx = parameterName.lastIndexOf(".");
        return (lastdotIdx >= 0) ? parameterName.substring(0, lastdotIdx) : "";
    }
    
    public static String normalizeValue(final ParamMetaData param, final String originalValue) {
        ParamUtils.LOG.debug("normalize value=" + originalValue + " parameter=" + param.getCompiledParamWithPathPattern().pattern());
        if (param.hasConversionScript() && originalValue != null) {
            final String conversionScript = param.getConversionScript();
            try {
                final ScriptEngine engine = ScriptEngineSingleton.getInstance();
                final Bindings bindings = engine.createBindings();
                bindings.put("parameter", (Object)originalValue);
                final Object result = engine.eval(conversionScript, bindings);
                final String res = getResult(result, param.getParamType());
                ParamUtils.LOG.debug("script:" + conversionScript + " before:" + originalValue + " after:" + res);
                return res;
            }
            catch (ScriptException e) {
                ParamUtils.LOG.error("Error while normalizing value '" + originalValue + "' for the parameter " + param.getCompiledParamWithPathPattern().pattern() + " : " + e);
                if (param.getParamType().isNumerical()) {
                    return null;
                }
                return "error";
            }
        }
        return originalValue;
    }
    
    private static String getResult(final Object result, final ParamType paramType) {
        if (result == null) {
            return null;
        }
        if (result instanceof Double) {
            final Double doubleValue = (Double)result;
            if (doubleValue.isInfinite() || doubleValue.isNaN()) {
                ParamUtils.LOG.info("Detected Infinity or NaN, returning null");
                return null;
            }
            if (paramType.equals(ParamType.BOOLEAN) && doubleValue % 1.0 == 0.0) {
                return paramType.toDataTypeObject(ParamType.INT.toDataTypeObject(result.toString()).toString()).toString();
            }
        }
        return paramType.toDataTypeObject(result.toString()).toString();
    }
    
    static {
        ParamUtils.LOG = Logger.getLogger(ParamUtils.class);
        DIGIT_PATTERN = Pattern.compile("\\.([0-9]+)\\.");
        GROUP_WITH_DOT_PATTERN = Pattern.compile("((\\w\\.)+)\\w");
        ParamUtils.LAST_DOT_REGEX = "\\.(?=[^\\.]+$)";
    }
}
