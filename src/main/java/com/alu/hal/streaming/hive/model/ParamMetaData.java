// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Set;
import java.util.stream.Collector;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import com.alu.hal.streaming.utils.ParamUtils;
import com.alu.motive.hal.commons.generated.parser.templates.Group;
import org.apache.spark.api.java.Optional;
import org.apache.log4j.Logger;
import java.util.regex.Pattern;
import java.io.Serializable;

public class ParamMetaData implements Serializable
{
    public static final String INDEX_PATTERN = ".([0-9]+).";
    public static final String CONF_INDEX_PATTERN = ".*.";
    public static final String COL_SEPARATOR = "^";
    public static final Pattern SHORTHAND_PATTERN;
    private static final long serialVersionUID = -3739085035587104032L;
    private static Logger LOG;
    private final int id;
    private final String parameterWithAsteriskPath;
    private final String parameterNameWithoutPath;
    private final Pattern compiledParamWithPathPattern;
    private final Pattern compiledPathPattern;
    private final TransposeParamColumnMapping transposeParamColumnMapping;
    private final Optional<CounterMetaData> counterMetaData;
    private final ParamType paramType;
    private final String transposeColumnString;
    private Optional<String> parentGroupReferredByParameter;
    private Optional<String> referredToGroup;
    private final Optional<String> conversionScript;
    
    public ParamMetaData(final Group.Parameter param, final String patternString, final TransposeParamColumnMapping transposeParamColumnMapping, final Optional<String> parentGroupReferredByParameter, final Optional<String> conversionScript) {
        this(ParamType.fromTypeEnum(param.getType()), patternString, transposeParamColumnMapping, getCounterMetaData(param), parentGroupReferredByParameter, (Optional<String>)Optional.fromNullable((Object)param.getReferredGroup()), conversionScript);
    }
    
    public ParamMetaData(final ParamType paramType, final String parameterWithAsteriskPath, final TransposeParamColumnMapping transposeParamColumnMapping, final Optional<CounterMetaData> counterMetaData, final Optional<String> parentGroupReferredByParameter, final Optional<String> referredToGroup, final Optional<String> conversionScript) {
        this.id = IdGenerator.getId();
        this.paramType = paramType;
        this.parameterWithAsteriskPath = parameterWithAsteriskPath;
        final String[] parameterNamePathSplitted = ParamUtils.splitAtLatestDot(parameterWithAsteriskPath);
        this.compiledParamWithPathPattern = Pattern.compile(toParameterPatternString(parameterWithAsteriskPath));
        this.compiledPathPattern = Pattern.compile(toParameterPatternString(parameterNamePathSplitted[0]) + "$");
        this.parameterNameWithoutPath = parameterNamePathSplitted[1];
        this.transposeParamColumnMapping = transposeParamColumnMapping;
        this.counterMetaData = counterMetaData;
        this.parentGroupReferredByParameter = parentGroupReferredByParameter;
        this.referredToGroup = referredToGroup;
        this.transposeColumnString = this.computeTransposeColumnString(transposeParamColumnMapping);
        this.conversionScript = conversionScript;
        if (conversionScript.isPresent()) {
            ParamMetaData.LOG.debug("param " + parameterWithAsteriskPath + " conversion script=" + (String)conversionScript.get());
        }
        else {
            ParamMetaData.LOG.debug("param " + parameterWithAsteriskPath + " conversion script not defined");
        }
    }
    
    public static String toParameterPatternString(final String parameterWithAsteriskPath) {
        return parameterWithAsteriskPath.replace(".*.", ".([0-9]+).");
    }
    
    public boolean hasParameterWithAsteriskPath(final String parameterWithAsteriskPath) {
        return this.parameterWithAsteriskPath.equals(parameterWithAsteriskPath);
    }
    
    public String getParameterWithAsteriskPath() {
        return this.parameterWithAsteriskPath;
    }
    
    public static Map<String, List<ParamMetaData>> byParameterNameWithoutPath(final Collection<ParamMetaData> paramMetaDataList) {
        return paramMetaDataList.stream().collect((Collector<? super ParamMetaData, ?, Map<String, List<ParamMetaData>>>)Collectors.groupingBy((Function<? super ParamMetaData, ? extends String>)ParamMetaData::getParameterNameWithoutPath));
    }
    
    private static Optional<CounterMetaData> getCounterMetaData(final Group.Parameter param) {
        Optional<CounterMetaData> counterMetadata = (Optional<CounterMetaData>)Optional.absent();
        if (param.getCounter() != null) {
            final ParamType paramType = ParamType.fromTypeEnum(param.getType());
            final Long maxValue = param.getCounter().getMaxValueThreshold();
            counterMetadata = (Optional<CounterMetaData>)Optional.of((Object)new CounterMetaData((Optional<String>)Optional.fromNullable((Object)param.getCounter().getRebootParameter()), maxValue));
        }
        return counterMetadata;
    }
    
    private String computeTransposeColumnString(final TransposeParamColumnMapping paramColumnMapping) {
        if (paramColumnMapping != null) {
            final StringBuffer col = new StringBuffer().append(paramColumnMapping.getTransposeValueColumn().getColPosition());
            for (final ColumnInfo indexCol : paramColumnMapping.getTransposeIndexColumns()) {
                col.append("^").append(indexCol.getColPosition());
            }
            return col.toString();
        }
        return null;
    }
    
    public boolean isCounter() {
        return this.counterMetaData.isPresent();
    }
    
    public ParamType getParamType() {
        return this.paramType;
    }
    
    public Pattern getCompiledParamWithPathPattern() {
        return this.compiledParamWithPathPattern;
    }
    
    public String getParameterNameWithoutPath() {
        return this.parameterNameWithoutPath;
    }
    
    public Pattern getCompiledPathPattern() {
        return this.compiledPathPattern;
    }
    
    public TransposeParamColumnMapping getTransposeParamColumnMapping() {
        return this.transposeParamColumnMapping;
    }
    
    public Optional<CounterMetaData> getCounterMetaData() {
        return this.counterMetaData;
    }
    
    public boolean isParameterOfReferredGroup() {
        return this.parentGroupReferredByParameter.isPresent();
    }
    
    public String getParameterReferringParentGroup() {
        return (String)this.parentGroupReferredByParameter.orNull();
    }
    
    public void setParameterReferringParentGroup(final Optional<String> parameterReferringParentGroup) {
        this.parentGroupReferredByParameter = parameterReferringParentGroup;
    }
    
    public boolean isReferenceParameter() {
        return this.referredToGroup.isPresent();
    }
    
    public String getReferredToGroup() {
        return (String)this.referredToGroup.orNull();
    }
    
    public void setReferredToGroup(final Optional<String> referredGroup) {
        this.referredToGroup = referredGroup;
    }
    
    public String getTransposeColumnString() {
        return this.transposeColumnString;
    }
    
    public boolean hasConversionScript() {
        return this.conversionScript.isPresent();
    }
    
    public String getConversionScript() {
        return (String)this.conversionScript.orNull();
    }
    
    public int getId() {
        return this.id;
    }
    
    public static Set<ParamMetaData> setOfDevUpTimeParamMetaData(final Collection<ParamMetaData> paramMetaDataCollection) {
        return paramMetaDataCollection.stream().filter(paramMetaData -> paramMetaData.isCounter() && ((CounterMetaData)paramMetaData.getCounterMetaData().get()).hasDevUpTimeReference()).flatMap(paramMetaData -> ((CounterMetaData)paramMetaData.getCounterMetaData().get()).getDevUpTimeParamMetaDataList().stream()).collect((Collector<? super Object, ?, Set<ParamMetaData>>)Collectors.toSet());
    }
    
    @Override
    public String toString() {
        return "ParamMetaData{id=" + this.id + ", parameterWithAsteriskPath='" + this.parameterWithAsteriskPath + '\'' + ", parameterNameWithoutPath='" + this.parameterNameWithoutPath + '\'' + ", compiledParamWithPathPattern=" + this.compiledParamWithPathPattern.pattern() + ", compiledPathPattern=" + this.compiledPathPattern.pattern() + ", transposeParamColumnMapping=" + this.transposeParamColumnMapping + ", counterMetaData=" + (this.counterMetaData.isPresent() ? ((Serializable)this.counterMetaData.get()) : "<empty>") + ", paramType=" + this.paramType + ", transposeColumnString='" + this.transposeColumnString + '\'' + ", parentGroupReferredByParameter=" + (this.parentGroupReferredByParameter.isPresent() ? ((String)this.parentGroupReferredByParameter.get()) : "<empty>") + ", referredToGroup=" + (this.referredToGroup.isPresent() ? ((String)this.referredToGroup.get()) : "<empty>") + ", conversionScript=" + (this.conversionScript.isPresent() ? ((String)this.conversionScript.get()) : "<empty>") + '}';
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ParamMetaData that = (ParamMetaData)o;
        return this.id == that.id;
    }
    
    @Override
    public int hashCode() {
        return this.id;
    }
    
    static {
        SHORTHAND_PATTERN = Pattern.compile("\\$\\{(.*)\\}(.*)");
        ParamMetaData.LOG = Logger.getLogger(ParamMetaData.class);
    }
    
    public static class IdGenerator
    {
        private static final AtomicInteger counter;
        
        public static int getId() {
            return IdGenerator.counter.incrementAndGet();
        }
        
        static {
            counter = new AtomicInteger();
        }
    }
}
