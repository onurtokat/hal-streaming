// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.function.Function;
import com.alu.motive.hal.commons.dto.ParameterDTO;
import java.util.List;
import org.apache.spark.api.java.Optional;
import com.alu.hal.streaming.config.WifiSparkConfig;
import java.util.Map;
import com.alu.motive.hal.commons.dto.DeviceIdDTO;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import java.util.Comparator;
import java.io.Serializable;

public class MgdDevReport implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final Comparator<MgdDevReport> COMPARE_BY_COLLECTION_TIME;
    private static Logger LOG;
    private static final String COL_SEPARATOR = "\\^";
    private static Pattern INDEX_PATTERN;
    private final DeviceIdDTO deviceId;
    private final long collectionTimeStamp;
    private final ModelType modelType;
    private final Map<DecoratedParameter.Id, DecoratedParameter> decoratedParamsMap;
    
    public MgdDevReport(final DeviceIdDTO deviceId, final long collectionTimeStamp, final ModelType modelType, final Map<DecoratedParameter.Id, DecoratedParameter> decoratedParamsMap) {
        this.deviceId = deviceId;
        this.collectionTimeStamp = collectionTimeStamp;
        this.modelType = modelType;
        this.decoratedParamsMap = decoratedParamsMap;
    }
    
    public static Optional<MgdDevReport> create(final MgdDevTransposeColumnState report, final WifiSparkConfig config) {
        final Optional<ModelType> modelType = config.getModelTypeByDeviceId(report.getDeviceId());
        final ModelMetadata modelMetadata = config.getModelMetaDataByModelType((ModelType)modelType.get());
        final Map<DecoratedParameter.Id, DecoratedParameter> decoratedParamsMap = createDecoratedParameterMap(report.getDeviceId(), report.getencodedParameterList(), modelMetadata);
        final MgdDevReport mgdDevReport = new MgdDevReport(report.getDeviceId(), report.getCollectionTimeStamp(), (ModelType)modelType.get(), decoratedParamsMap);
        mgdDevReport.parseRollingOverRefParams(modelMetadata.getDevUpTimeParamMetaDataSet());
        return (Optional<MgdDevReport>)Optional.of((Object)mgdDevReport);
    }
    
    private static Map<DecoratedParameter.Id, DecoratedParameter> createDecoratedParameterMap(final DeviceIdDTO deviceId, final List<ParameterDTO> metaDataEncodedParameters, final ModelMetadata modelMetadata) {
        final Map<DecoratedParameter.Id, DecoratedParameter> decoratedParameterMap = metaDataEncodedParameters.stream().map(shortParameterDTO -> toDecoratedParameter(shortParameterDTO, modelMetadata, deviceId)).filter(java.util.Optional::isPresent).map((Function<? super Object, ?>)java.util.Optional::get).collect(Collectors.toMap((Function<? super Object, ? extends DecoratedParameter.Id>)DecoratedParameter::getId, (Function<? super Object, ? extends DecoratedParameter>)Function.identity(), (decoratedParameter1, decoratedParameter2) -> {
            MgdDevReport.LOG.warn("TR69 report of " + deviceId + "contains duplicated entries for parameter " + decoratedParameter1.getParameterName() + " One parameter instance removed");
            return decoratedParameter1;
        }));
        if (decoratedParameterMap.values().size() != metaDataEncodedParameters.size()) {
            MgdDevReport.LOG.warn("Unable to decode all metadata encoded parameters for deviceId : " + deviceId + " - Encoded nbr of params : " + metaDataEncodedParameters.size() + " - Decoded nbr of params : " + decoratedParameterMap.values().size());
        }
        return decoratedParameterMap;
    }
    
    private static java.util.Optional<DecoratedParameter> toDecoratedParameter(final ParameterDTO encodedParameterDTO, final ModelMetadata modelMetadata, final DeviceIdDTO deviceId) {
        final int paramMetadataId = Integer.parseInt(encodedParameterDTO.getName());
        final java.util.Optional<ParamMetaData> paramMetaData = modelMetadata.getParamMetaData(paramMetadataId);
        if (!paramMetaData.isPresent()) {
            MgdDevReport.LOG.warn("Unable to decode metadata encoded parameter : " + encodedParameterDTO + "of device id : " + deviceId);
            return java.util.Optional.empty();
        }
        final ParameterDTO decodedParameterDTO = new ParameterDTO();
        final String[] values = encodedParameterDTO.getValue().split("\\^");
        decodedParameterDTO.setName(decodeParamName(paramMetaData.get(), values));
        decodedParameterDTO.setValue(values[0]);
        return java.util.Optional.of(new DecoratedParameter(decodedParameterDTO, paramMetaData.get()));
    }
    
    private static String decodeParamName(final ParamMetaData paramMetaData, final String[] values) {
        String name = paramMetaData.getCompiledParamWithPathPattern().pattern();
        final Matcher indexMatcher = MgdDevReport.INDEX_PATTERN.matcher(name);
        if (indexMatcher.find()) {
            for (int i = 1; i < values.length; ++i) {
                name = indexMatcher.replaceFirst(String.valueOf(values[i]));
                indexMatcher.reset(name);
            }
        }
        return name;
    }
    
    private void parseRollingOverRefParams(final Set<ParamMetaData> devUpTimeParamMetaDataSet) {
        final Map<Integer, DecoratedParameter> cachedDevUpTimeParamsByParamMetaDataId = new HashMap<Integer, DecoratedParameter>();
        final Map<Integer, DecoratedParameter> map;
        final List<DecoratedParameter> decoratedParametersWithUnFoundDevUpTimeRef = this.decoratedParamsMap.values().stream().filter(decoratedParameter -> decoratedParameter.hasDevUpTimeRefParameter()).map(decoratedParameterWithDevUpTimeRef -> {
            if (!this.matchWithCachedDevUpTimeDecoratedParameterAndProcess(decoratedParameterWithDevUpTimeRef, map)) {
                this.matchWithDecoratedParametersAndProcess(decoratedParameterWithDevUpTimeRef, map);
            }
            return decoratedParameterWithDevUpTimeRef;
        }).filter(decoratedParameter -> !decoratedParameter.hasDevUpTimeValueSet()).collect((Collector<? super Object, ?, List<DecoratedParameter>>)Collectors.toList());
        decoratedParametersWithUnFoundDevUpTimeRef.forEach(decoratedParameterWithMissingRebootRef -> {
            MgdDevReport.LOG.warn("Unable to process parameter " + decoratedParameterWithMissingRebootRef.getParameterName() + " in report for deviceId: " + this.deviceId.toString() + "@TS : " + this.collectionTimeStamp + ": Configured uptime parameter reference " + (String)decoratedParameterWithMissingRebootRef.getDevUpTimeRefParameterName().get() + " is missing in the report. Parameter will be removed.");
            this.decoratedParamsMap.remove(decoratedParameterWithMissingRebootRef.getId(), decoratedParameterWithMissingRebootRef);
        });
    }
    
    private boolean matchWithCachedDevUpTimeDecoratedParameterAndProcess(final DecoratedParameter counterParameterWithDevUpTimeRef, final Map<Integer, DecoratedParameter> foundDevUpTimeParamsByParamMetaDataId) {
        final List<ParamMetaData> devUpTimeParamMetaDataList = ((CounterMetaData)counterParameterWithDevUpTimeRef.getParamMetaData().getCounterMetaData().get()).getDevUpTimeParamMetaDataList();
        final java.util.Optional<DecoratedParameter> devUpTimeDecoratedParameter = devUpTimeParamMetaDataList.stream().map(devUpTimeReferenceParamMetada -> java.util.Optional.ofNullable(foundDevUpTimeParamsByParamMetaDataId.get(devUpTimeReferenceParamMetada.getId()))).filter(java.util.Optional::isPresent).map((Function<? super Object, ? extends DecoratedParameter>)java.util.Optional::get).findAny();
        if (devUpTimeDecoratedParameter.isPresent()) {
            counterParameterWithDevUpTimeRef.setDevUpTimeRefValue(devUpTimeDecoratedParameter.get());
            return true;
        }
        return false;
    }
    
    private boolean matchWithDecoratedParametersAndProcess(final DecoratedParameter counterParameterWithDevUpTimeRef, final Map<Integer, DecoratedParameter> foundDevUpTimeParamsByParamMetaDataId) {
        return this.decoratedParamsMap.values().stream().filter(decoratedParameter -> ((CounterMetaData)counterParameterWithDevUpTimeRef.getParamMetaData().getCounterMetaData().get()).getDevUpTimeParamMetaDataList().stream().anyMatch(paramMetaDataOfUptimeRef -> paramMetaDataOfUptimeRef.getCompiledParamWithPathPattern().matcher(decoratedParameter.getParameterName()).matches())).map(devUpTimeDecoratedParameter -> {
            counterParameterWithDevUpTimeRef.setDevUpTimeRefValue(devUpTimeDecoratedParameter);
            foundDevUpTimeParamsByParamMetaDataId.putIfAbsent(devUpTimeDecoratedParameter.getParamMetaData().getId(), devUpTimeDecoratedParameter);
            return devUpTimeDecoratedParameter;
        }).findAny().isPresent();
    }
    
    public void computeDelta(final MgdDevReport prevMgdDevReport) {
        final DecoratedParameter prevDecoratedParameter;
        this.decoratedParamsMap.values().stream().filter(DecoratedParameter::isCounterParameter).forEach(decoratedParameter -> {
            prevDecoratedParameter = prevMgdDevReport.decoratedParamsMap.get(decoratedParameter.getId());
            if (prevDecoratedParameter != null) {
                decoratedParameter.computeAndUpdateDelta(prevDecoratedParameter);
            }
            else {
                MgdDevReport.LOG.warn("Unable to normalize counter parameter due to missing parameter in prev report for deviceId: " + prevMgdDevReport.getDeviceId().getDeviceIdString() + ", parameter: " + decoratedParameter.getParameterName());
            }
        });
    }
    
    public DeviceIdDTO getDeviceId() {
        return this.deviceId;
    }
    
    public long getCollectionTimeStamp() {
        return this.collectionTimeStamp;
    }
    
    public ModelType getModelType() {
        return this.modelType;
    }
    
    public Collection<DecoratedParameter> getDecoratedParameters() {
        return this.decoratedParamsMap.values();
    }
    
    public String getSessionId() {
        return "";
    }
    
    @Override
    public String toString() {
        return "MgdDevReport [deviceId=" + this.deviceId + ", collectionTimeStamp=" + this.collectionTimeStamp + ", modelType=" + this.modelType + ", decoratedParamsMap=" + this.decoratedParamsMap + "]";
    }
    
    static {
        COMPARE_BY_COLLECTION_TIME = Comparator.comparing((Function<? super MgdDevReport, ? extends Comparable>)MgdDevReport::getCollectionTimeStamp);
        MgdDevReport.LOG = Logger.getLogger(MgdDevReport.class);
        MgdDevReport.INDEX_PATTERN = Pattern.compile("(\\(\\[0-9\\]\\+\\))+");
    }
}
