// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.utils;

import org.apache.commons.lang.text.StrSubstitutor;
import java.util.stream.IntStream;
import java.util.regex.Matcher;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.List;
import java.util.regex.Pattern;
import com.alu.hal.streaming.config.DashBoardConfig;
import java.util.HashMap;
import java.util.Map;

public class QueryUdfResolver
{
    private static final String EXPECTED_RETRANSMISION_PERC_UDAF_NAME = "expected_retransmissions_perc_udf";
    private static final String EXPECTED_RETRANSMISION_PERC_UDAF_VALUE;
    private static final String ASSOCIATIONS_OBS_UDAF_NAME = "associations_obs_udaf";
    private static final String ASSOCIATIONS_OBS_UDAF_VALUE;
    private static final String CONNECTION_SIGNAL_SCORE_UDAF_NAME = "connection_signal_score_udaf";
    private static final String CONNECTION_SIGNAL_SCORE_UDAF_VALUE = "least((${p0}+avg(${arg0})-var_pop(${arg0}))/100,1.0)";
    private static final String CONNECTION_QUALITY_SCORE_UDAF_NAME = "connection_quality_score_udaf";
    private static final String CONNECTION_QUALITY_SCORE_UDAF_VALUE = "1-least(coalesce (sum(${arg0})/sum(${arg1}), sum(${arg2})/sum(${arg3})),1.0)";
    private static final String CONNECTION_QUALITY_SCORE_UDAF2_NAME = "connection_quality_score_assoc_device_udaf";
    private static final String CONNECTION_QUALITY_SCORE_UDAF2_VALUE = "1-least(sum(${arg0})/sum(${arg1}),1.0)";
    private static final String CONNECTION_FAILURE_SCORE_UDAF_NAME = "connection_failure_score_udaf";
    private static final String CONNECTION_FAILURE_SCORE_UDAF_VALUE = "least(sum(if(${arg0},1,0))/sum(${arg1}) + (1- count(distinct ${arg2})/count(distinct ${arg3})) /2,1.0)";
    private static final String CONNECTION_FAILURE_SCORE_ASSOC_DEVICE_UDAF_NAME = "connection_failure_score_assoc_device_udaf";
    private static final String CONNECTION_FAILURE_SCORE_ASSOC_DEVICE_UDAF_VALUE = "least(1- count(distinct ${arg0})/count(distinct ${arg1}),1.0)";
    private static final Map<String, String> udfMap;
    
    private static Map<String, String> buildUdfMap() {
        final Map<String, String> result = new HashMap<String, String>();
        result.put("expected_retransmissions_perc_udf", QueryUdfResolver.EXPECTED_RETRANSMISION_PERC_UDAF_VALUE);
        result.put("associations_obs_udaf", QueryUdfResolver.ASSOCIATIONS_OBS_UDAF_VALUE);
        result.put("connection_signal_score_udaf", "least((${p0}+avg(${arg0})-var_pop(${arg0}))/100,1.0)");
        result.put("connection_quality_score_udaf", "1-least(coalesce (sum(${arg0})/sum(${arg1}), sum(${arg2})/sum(${arg3})),1.0)");
        result.put("connection_quality_score_assoc_device_udaf", "1-least(sum(${arg0})/sum(${arg1}),1.0)");
        result.put("connection_failure_score_udaf", "least(sum(if(${arg0},1,0))/sum(${arg1}) + (1- count(distinct ${arg2})/count(distinct ${arg3})) /2,1.0)");
        result.put("connection_failure_score_assoc_device_udaf", "least(1- count(distinct ${arg0})/count(distinct ${arg1}),1.0)");
        return result;
    }
    
    public String getUdfFormula(final String key) {
        return QueryUdfResolver.udfMap.getOrDefault(key, "");
    }
    
    public static String resolveQuery(String query, final DashBoardConfig CONFIG) {
        final Set<Map.Entry<String, String>> entrySetUdf = QueryUdfResolver.udfMap.entrySet();
        for (final Map.Entry<String, String> entry : entrySetUdf) {
            final Pattern pattern = Pattern.compile(entry.getKey() + "\\s*\\(.*?\\)");
            final Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                System.out.println("find udf=" + matcher.group(0));
                final String udfDeclaration = matcher.group(0).trim();
                final String argsList = udfDeclaration.substring(entry.getKey().length() + 1, udfDeclaration.length() - 1);
                final List<String> udfArgs = Pattern.compile(",").splitAsStream(argsList).map((Function<? super String, ?>)String::trim).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
                System.out.println("udfArgs=" + udfArgs);
                final String udfString = getUfdFromTemplate(entry.getValue(), udfArgs, CONFIG.getListOfString(entry.getKey() + ".params"));
                System.out.println("udfString=" + udfString);
                query = query.replaceAll(entry.getKey() + "\\s*\\(.*?\\)", udfString);
            }
        }
        return query;
    }
    
    private static String getUfdFromTemplate(final String template, final List<String> udfArgs, final List<String> params) {
        final Map<String, String> paramsMap = IntStream.range(0, params.size()).boxed().collect(Collectors.toMap(i -> "p" + i, i -> params.get(i)));
        System.out.println("params=" + paramsMap);
        final Map<String, String> udfArgsMap = IntStream.range(0, udfArgs.size()).boxed().collect(Collectors.toMap(i -> "arg" + i, i -> udfArgs.get(i)));
        System.out.println("udfArgsMap=" + udfArgsMap);
        paramsMap.putAll(udfArgsMap);
        final StrSubstitutor sub = new StrSubstitutor(paramsMap);
        return sub.replace(template);
    }
    
    static {
        EXPECTED_RETRANSMISION_PERC_UDAF_VALUE = String.join(System.getProperty("line.separator"), " sum(case when ${arg0} >= ${p0} then ${arg1} * ${p1}", " when ${arg0} < ${p0} and ${arg0} >= ${p2} then ${arg1} * ${p3}", " when ${arg0} < ${p2} then ${arg1} * ${p4} end)/sum(${arg1})");
        ASSOCIATIONS_OBS_UDAF_VALUE = String.join(System.getProperty("line.separator"), " case when avg(${arg0}) < ${p0} and avg(${arg1})> ${p1} then \"Excessive\"", " when avg(${arg0}) < ${p2} and avg(${arg1}) > ${p1} then \"Many\"", " when avg(${arg0}) >= ${p2} then \"Normal\" end");
        udfMap = buildUdfMap();
    }
}
