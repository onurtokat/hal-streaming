// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.util.Collections;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import java.util.Iterator;
import com.alu.motive.hal.commons.generated.parser.templates.DeviceParameter;
import com.alu.motive.hal.commons.generated.parser.templates.TypeEnum;
import java.util.Collection;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import com.alu.motive.hal.commons.generated.parser.templates.Group;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Matcher;
import com.alu.hal.streaming.exception.ParameterFormatException;
import org.apache.spark.api.java.Optional;
import java.util.HashMap;
import com.alu.motive.hal.commons.generated.parser.templates.DataMappingTemplate;
import org.apache.spark.sql.types.StructType;
import java.util.List;
import org.apache.log4j.Logger;
import java.util.Map;
import java.io.Serializable;

public class ModelMetadataFactory implements Serializable
{
    public static final String DEVICE_ID_COLUMN_NAME = "DeviceId";
    public static final String COLLECTION_TS_COLUMN_NAME = "TS";
    public static final String IDX = "Idx";
    public static final String COLUMNNAME_GROUP_PARAMETER_DELIM = "_";
    public static final ColumnInfo COLLECTION_TS_COLUMN;
    public static final ColumnInfo DEVICE_ID_COLUMN;
    public static final Map<String, ColumnInfo> PREDEFINED_COLUMNS;
    private static Logger LOG;
    private final Map<String, ColumnInfo> transposeTableColumnInfos;
    private final Map<Integer, ParamMetaData> paramMetaDataMap;
    private final Map<ParamMetaData, List<ParamMetaData>> deviceParamMetaDataByStdParamMetaData;
    private final Map<String, String> groupNamePatternMap;
    private final StructType transposeSchema;
    private final StructType transposeSchemaWithoutIndexes;
    private final ModelType modelType;
    
    public ModelMetadataFactory(final ModelType modelType, final DataMappingTemplate template) {
        this.transposeTableColumnInfos = new HashMap<String, ColumnInfo>();
        this.paramMetaDataMap = new HashMap<Integer, ParamMetaData>();
        this.deviceParamMetaDataByStdParamMetaData = new HashMap<ParamMetaData, List<ParamMetaData>>();
        this.groupNamePatternMap = new HashMap<String, String>();
        this.modelType = modelType;
        this.createTransposeColumnInfos(template);
        this.transposeSchema = this.createTransposeSchema();
        this.transposeSchemaWithoutIndexes = this.createTransposeSchemaWithoutIndexes();
        try {
            template.getGroup().forEach(group -> this.buildParamMetaDataList((Optional<List<ColumnInfo>>)Optional.absent(), null, null, group, (Optional<String>)Optional.fromNullable((Object)group.getReferredByParameter())));
        }
        catch (ParameterFormatException e) {
            ModelMetadataFactory.LOG.error("Cannot create modelmetadata for modeltype " + modelType + "\n" + e);
            throw e;
        }
        this.paramMetaDataMap.values().forEach(paramMetaData -> {
            this.parseCounterDevUpTimeReferenceParameter(paramMetaData);
            this.parseParentGroupReferredBy(paramMetaData);
            this.parseReferredGroup(paramMetaData);
        });
    }
    
    private String parseToPatternAndValidate(final String shorthandParameterOrGroupRef) {
        final Matcher matcher = ParamMetaData.SHORTHAND_PATTERN.matcher(shorthandParameterOrGroupRef);
        if (matcher.find()) {
            final String groupName = matcher.group(1);
            final String groupPattern = this.groupNamePatternMap.get(groupName);
            return shorthandParameterOrGroupRef.replace("${" + groupName + "}", groupPattern);
        }
        return null;
    }
    
    private void parseCounterDevUpTimeReferenceParameter(final ParamMetaData paramMetaDataOfCounter) {
        if (paramMetaDataOfCounter.isCounter()) {
            final CounterMetaData counterMetaData = (CounterMetaData)paramMetaDataOfCounter.getCounterMetaData().get();
            if (counterMetaData.getDevUpTimeParameterRef().isPresent()) {
                final String devUpTimeParameterWithAsteriskPath = this.parseToPatternAndValidate((String)counterMetaData.getDevUpTimeParameterRef().get());
                counterMetaData.setDevUpTimeParameterRef((Optional<String>)Optional.of((Object)devUpTimeParameterWithAsteriskPath));
                final List<ParamMetaData> paramMetaDataListOfDevUpTimes = this.ParamMetaDataWithAllAssociatedDeviceParamMetaDataOf(devUpTimeParameterWithAsteriskPath);
                counterMetaData.setDevUpTimeParamMetaList(paramMetaDataListOfDevUpTimes);
            }
        }
    }
    
    List<ParamMetaData> ParamMetaDataWithAllAssociatedDeviceParamMetaDataOf(final String parameterName) {
        return this.deviceParamMetaDataByStdParamMetaData.entrySet().stream().filter(entry -> entry.getKey().hasParameterWithAsteriskPath(parameterName)).flatMap(entry -> Stream.concat((Stream<?>)Stream.of((T)entry.getKey()), (Stream<?>)((List)entry.getValue()).stream())).distinct().collect((Collector<? super Object, ?, List<ParamMetaData>>)Collectors.toList());
    }
    
    private void parseParentGroupReferredBy(final ParamMetaData paramMetaData) {
        if (paramMetaData.isParameterOfReferredGroup()) {
            paramMetaData.setParameterReferringParentGroup((Optional<String>)Optional.of((Object)this.parseToPatternAndValidate(paramMetaData.getParameterReferringParentGroup())));
        }
    }
    
    private void parseReferredGroup(final ParamMetaData paramMetaData) {
        if (paramMetaData.isReferenceParameter()) {
            paramMetaData.setReferredToGroup((Optional<String>)Optional.of((Object)this.parseToPatternAndValidate(paramMetaData.getReferredToGroup())));
        }
    }
    
    private Optional<List<ColumnInfo>> createIndexColumns(final Group group, final Optional<List<ColumnInfo>> parentIndexColumns) {
        final int indexCount = StringUtils.countMatches(group.getPath(), ".*.");
        if (indexCount == 0 && !parentIndexColumns.isPresent()) {
            return (Optional<List<ColumnInfo>>)Optional.absent();
        }
        final List<ColumnInfo> indexColumnInfos = new ArrayList<ColumnInfo>();
        if (parentIndexColumns.isPresent()) {
            indexColumnInfos.addAll((Collection<? extends ColumnInfo>)parentIndexColumns.get());
        }
        for (int i = 0; i < indexCount; ++i) {
            final String indexColumnName = getIndexColumnName(group.getName(), i + 1);
            indexColumnInfos.add(new ColumnInfo(indexColumnName, this.transposeTableColumnInfos.get(indexColumnName).getColPosition(), ParamType.fromTypeEnum(TypeEnum.INT).getDataType(), true));
        }
        return (Optional<List<ColumnInfo>>)Optional.of((Object)indexColumnInfos);
    }
    
    public static String getParameterColumnName(final String groupName, final String parameterName) {
        return groupName + "_" + parameterName;
    }
    
    public static String getIndexColumnName(final String groupName, final int index) {
        return groupName + "_" + "Idx" + index;
    }
    
    private ColumnInfo createValueColumnInfo(final Group group, final Group.Parameter parameter) {
        final String parameterColumnName = getParameterColumnName(group.getName(), parameter.getName());
        return new ColumnInfo(parameterColumnName, this.transposeTableColumnInfos.get(parameterColumnName).getColPosition(), ParamType.fromTypeEnum(parameter.getType()).getDataType(), false);
    }
    
    private void buildParamMetaDataList(final Optional<List<ColumnInfo>> parentTransposeIndexColumns, final String parentGroupName, final String parentPattern, final Group group, final Optional<String> parameterReferencingParentGroup) {
        final Optional<List<ColumnInfo>> transposeIndexColumns = this.createIndexColumns(group, parentTransposeIndexColumns);
        final String groupPattern = buildParameterPattern(parentGroupName, parentPattern, group.getPath());
        this.groupNamePatternMap.put(group.getName(), groupPattern);
        final ColumnInfo valueColumn;
        final Optional optional;
        final TransposeParamColumnMapping transposeParamColumnMapping;
        final String parameterPattern;
        final List<ParamMetaData> deviceParamMetaDataList;
        final Iterator<DeviceParameter> \u0131terator;
        DeviceParameter deviceParameter;
        final String s;
        String parameterPattern2;
        String parameterPattern3;
        Optional<String> conversionScript;
        ParamMetaData paramMetaData;
        final String parameterPattern4;
        final Optional<String> conversionScriptFromSameDeviceParameter;
        final ParamMetaData paramMetaData2;
        group.getParameter().forEach(param -> {
            valueColumn = this.createValueColumnInfo(group, param);
            transposeParamColumnMapping = new TransposeParamColumnMapping((List<ColumnInfo>)optional.orNull(), valueColumn);
            parameterPattern = null;
            deviceParamMetaDataList = new ArrayList<ParamMetaData>();
            if (param.getDeviceParameters() != null) {
                param.getDeviceParameters().getDeviceParameter().iterator();
                while (\u0131terator.hasNext()) {
                    deviceParameter = \u0131terator.next();
                    parameterPattern2 = s;
                    if (deviceParameter.getPath() != null) {
                        parameterPattern2 = buildParameterPattern(parentGroupName, parentPattern, deviceParameter.getPath());
                    }
                    parameterPattern3 = parameterPattern2 + deviceParameter.getName();
                    conversionScript = (Optional<String>)Optional.fromNullable((Object)deviceParameter.getConversionScript());
                    paramMetaData = new ParamMetaData(param, parameterPattern3, transposeParamColumnMapping, parameterReferencingParentGroup, conversionScript);
                    this.paramMetaDataMap.put(paramMetaData.getId(), paramMetaData);
                    deviceParamMetaDataList.add(paramMetaData);
                }
            }
            parameterPattern4 = s + param.getName();
            conversionScriptFromSameDeviceParameter = this.getConversionScriptFromSameDeviceParameter(group, param);
            ModelMetadataFactory.LOG.debug("ConversionScript from DeviceParameter with same Path and same Name for Parameter with : " + group.getPath() + " and name: " + param.getName() + " : " + conversionScriptFromSameDeviceParameter);
            paramMetaData2 = new ParamMetaData(param, parameterPattern4, transposeParamColumnMapping, parameterReferencingParentGroup, conversionScriptFromSameDeviceParameter);
            this.paramMetaDataMap.put(paramMetaData2.getId(), paramMetaData2);
            this.deviceParamMetaDataByStdParamMetaData.put(paramMetaData2, deviceParamMetaDataList);
            return;
        });
        if (group.getGroup() != null) {
            group.getGroup().forEach(subgroup -> this.buildParamMetaDataList(transposeIndexColumns, group.getName(), groupPattern, subgroup, parameterReferencingParentGroup));
        }
    }
    
    private Optional<String> getConversionScriptFromSameDeviceParameter(final Group group, final Group.Parameter param) {
        Optional<String> conversionScript = (Optional<String>)Optional.absent();
        ModelMetadataFactory.LOG.debug("get ConversionScript from DeviceParameter with same Path and same Name for Parameter with group path: " + group.getPath() + " and name: " + param.getName());
        if (param.getDeviceParameters() != null) {
            for (final DeviceParameter deviceParameter : param.getDeviceParameters().getDeviceParameter()) {
                if (deviceParameter.getPath() != null && !group.getPath().equals(deviceParameter.getPath())) {
                    continue;
                }
                if (!deviceParameter.getName().equals(param.getName())) {
                    continue;
                }
                conversionScript = (Optional<String>)Optional.fromNullable((Object)deviceParameter.getConversionScript());
            }
        }
        return conversionScript;
    }
    
    private static String buildParameterPattern(final String parentGroupName, final String parentPattern, final String path) {
        String patternString = path;
        if (parentGroupName != null) {
            final String[] split = path.split("\\$\\{" + parentGroupName + "\\}");
            patternString = ((split.length > 1) ? (parentPattern + split[1]) : ((split.length == 1) ? split[0] : parentPattern));
        }
        return patternString;
    }
    
    private void createTransposeColumnInfos(final DataMappingTemplate template) {
        this.transposeTableColumnInfos.putAll(ModelMetadataFactory.PREDEFINED_COLUMNS);
        template.getGroup().stream().forEachOrdered(group -> this.createTransposeTableColumnInfos(group));
    }
    
    private void createTransposeTableColumnInfos(final Group group) {
        final int indexCount = StringUtils.countMatches(group.getPath(), ".*.");
        final String groupName = group.getName();
        for (int i = 0; i < indexCount; ++i) {
            final ColumnInfo columnInfo = new ColumnInfo(getIndexColumnName(groupName, i + 1), this.transposeTableColumnInfos.size(), ParamType.fromTypeEnum(TypeEnum.INT).getDataType(), true);
            this.transposeTableColumnInfos.put(columnInfo.getName(), columnInfo);
        }
        final ColumnInfo columnInfo2;
        group.getParameter().stream().forEachOrdered(param -> {
            columnInfo2 = new ColumnInfo(getParameterColumnName(groupName, param.getName()), this.transposeTableColumnInfos.size(), ParamType.fromTypeEnum(param.getType()).getDataType(), false);
            this.transposeTableColumnInfos.put(columnInfo2.getName(), columnInfo2);
            return;
        });
        if (group.getGroup() != null) {
            group.getGroup().forEach(subgroup -> this.createTransposeTableColumnInfos(subgroup));
        }
    }
    
    private StructType createTransposeSchema() {
        final List<StructField> fieldsArray = new ArrayList<StructField>();
        this.getSortedColumnInfos().stream().forEachOrdered(columnInfo -> fieldsArray.add(DataTypes.createStructField(columnInfo.getName(), columnInfo.getType(), true)));
        return DataTypes.createStructType((List)fieldsArray);
    }
    
    private StructType createTransposeSchemaWithoutIndexes() {
        final List<StructField> fieldsArray = new ArrayList<StructField>();
        this.getSortedColumnInfos().stream().filter(columnInfo -> !columnInfo.isIndex()).forEach(columnInfo -> fieldsArray.add(DataTypes.createStructField(columnInfo.getName(), columnInfo.getType(), true)));
        return DataTypes.createStructType((List)fieldsArray);
    }
    
    private List<ColumnInfo> getSortedColumnInfos() {
        final ArrayList<ColumnInfo> columnInfosList = new ArrayList<ColumnInfo>(this.transposeTableColumnInfos.values());
        Collections.sort(columnInfosList);
        return columnInfosList;
    }
    
    public Map<String, ColumnInfo> getTransposeTableColumnInfos() {
        return this.transposeTableColumnInfos;
    }
    
    public StructType getTransposeSchema() {
        return this.transposeSchema;
    }
    
    public ModelMetadata create() {
        return new ModelMetadata(new TransposeTableMetadata(this.transposeTableColumnInfos, this.transposeSchema, this.transposeSchemaWithoutIndexes, this.modelType), this.paramMetaDataMap, ParamMetaData.setOfDevUpTimeParamMetaData(this.paramMetaDataMap.values()));
    }
    
    static {
        COLLECTION_TS_COLUMN = new ColumnInfo("TS", 0, DataTypes.LongType, false);
        DEVICE_ID_COLUMN = new ColumnInfo("DeviceId", 1, DataTypes.StringType, false);
        (PREDEFINED_COLUMNS = new HashMap<String, ColumnInfo>()).put(ModelMetadataFactory.COLLECTION_TS_COLUMN.getName(), ModelMetadataFactory.COLLECTION_TS_COLUMN);
        ModelMetadataFactory.PREDEFINED_COLUMNS.put(ModelMetadataFactory.DEVICE_ID_COLUMN.getName(), ModelMetadataFactory.DEVICE_ID_COLUMN);
        ModelMetadataFactory.LOG = Logger.getLogger(ModelMetadataFactory.class);
    }
}
