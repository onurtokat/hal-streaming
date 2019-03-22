// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.util.function.Function;
import java.util.Map;
import java.util.Collections;
import com.alu.hal.streaming.utils.ParamUtils;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.alu.hal.streaming.exception.ParameterFormatException;
import org.apache.spark.api.java.Optional;
import com.alu.hal.streaming.config.WifiSparkConfig;
import com.alu.motive.hal.commons.dto.TR69DTO;
import com.alu.motive.hal.commons.dto.ParameterDTO;
import java.util.List;
import com.alu.motive.hal.commons.dto.DeviceIdDTO;
import org.apache.log4j.Logger;
import java.util.Comparator;
import java.io.Serializable;

public class MgdDevTransposeColumnState implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final String DIGIT_PATTERN = "\\d+";
    public static final Comparator<MgdDevTransposeColumnState> COMPARE_BY_COLLECTION_TIME;
    private static Logger LOG;
    private final DeviceIdDTO deviceId;
    private final long collectionTimeStamp;
    private final ModelType modelType;
    private final List<ParameterDTO> parameters;
    
    public MgdDevTransposeColumnState(final DeviceIdDTO deviceId, final long collectionTimeStamp, final ModelType modelType, final List<ParameterDTO> params) {
        this.deviceId = deviceId;
        this.collectionTimeStamp = collectionTimeStamp;
        this.modelType = modelType;
        this.parameters = params;
    }
    
    public static Optional<MgdDevTransposeColumnState> create(final TR69DTO tr69DTO, final WifiSparkConfig config) {
        final Optional<ModelType> modelType = config.getModelTypeByDeviceId(tr69DTO.getDeviceId());
        MgdDevTransposeColumnState.LOG.debug("Nbr of parameters received for" + tr69DTO.getDeviceId() + " - Nbr of encoded params : " + tr69DTO.getParameterList().size());
        MgdDevTransposeColumnState.LOG.trace("tr69DTO received: " + tr69DTO);
        if (!modelType.isPresent()) {
            MgdDevTransposeColumnState.LOG.warn("TR-69 report received with unconfigured device id : " + tr69DTO.getDeviceId());
            return (Optional<MgdDevTransposeColumnState>)Optional.absent();
        }
        final ModelMetadata modelMetaDataByModelType = config.getModelMetaDataByModelType((ModelType)modelType.get());
        List<ParameterDTO> shortDTOs;
        try {
            shortDTOs = createShortParamDTOList(tr69DTO.getParameterList(), modelMetaDataByModelType);
        }
        catch (ParameterFormatException e) {
            MgdDevTransposeColumnState.LOG.warn("Unable to process report for deviceId " + tr69DTO.getDeviceId() + " due to " + e.getMessage());
            return (Optional<MgdDevTransposeColumnState>)Optional.empty();
        }
        MgdDevTransposeColumnState.LOG.debug("Encoded and filtered parameters for" + tr69DTO.getDeviceId() + " - Nbr of encoded params : " + tr69DTO.getParameterList().size());
        final MgdDevTransposeColumnState mgdDevReport = new MgdDevTransposeColumnState(tr69DTO.getDeviceId(), tr69DTO.getTimeStamp() / 1000L, (ModelType)modelType.get(), shortDTOs);
        return (Optional<MgdDevTransposeColumnState>)Optional.of((Object)mgdDevReport);
    }
    
    private static List<ParameterDTO> createShortParamDTOList(final List<ParameterDTO> parameters, final ModelMetadata modelMetaData) throws ParameterFormatException {
        MgdDevTransposeColumnState.LOG.info("received in tr69DTO parameters.size=" + parameters.size());
        return parameters.stream().flatMap(parameter -> matchAndEncodeWithMetadata(parameter, modelMetaData).stream()).collect((Collector<? super Object, ?, List<ParameterDTO>>)Collectors.toList());
    }
    
    private static List<ParameterDTO> matchAndEncodeWithMetadata(final ParameterDTO parameter, final ModelMetadata modelMetadata) throws ParameterFormatException {
        final String[] parameterPathAndName = ParamUtils.splitAtLatestDot(parameter.getName());
        final Map<String, List<ParamMetaData>> paramMetadatabyParamNameWithoutPath = modelMetadata.getParamMetadatabyParamNameWithoutPath();
        final List<ParamMetaData> matchingParamMetaDataList = paramMetadatabyParamNameWithoutPath.getOrDefault(parameterPathAndName[1], Collections.emptyList());
        return matchingParamMetaDataList.stream().filter(paramMetaData -> paramMetaData.getCompiledPathPattern().matcher(parameterPathAndName[0]).matches()).map(matchedParamMetaData -> normalizeValueAndEncode(parameter, matchedParamMetaData)).collect((Collector<? super Object, ?, List<ParameterDTO>>)Collectors.toList());
    }
    
    private static ParameterDTO normalizeValueAndEncode(final ParameterDTO parameterDTO, final ParamMetaData paramMetaData) {
        final ParameterDTO encodedParameterDTO = new ParameterDTO();
        encodedParameterDTO.setName(Integer.toString(paramMetaData.getId()));
        final StringBuffer value = encodeAndNormalizeParamValue(parameterDTO, paramMetaData);
        encodedParameterDTO.setValue(value.toString());
        return encodedParameterDTO;
    }
    
    private static StringBuffer encodeAndNormalizeParamValue(final ParameterDTO matchedParam, final ParamMetaData paramMetaData) {
        final StringBuffer value = new StringBuffer().append(ParamUtils.normalizeValue(paramMetaData, matchedParam.getValue()));
        final List<String> indexes = ParamUtils.getIndexes(matchedParam.getName(), paramMetaData.getCompiledParamWithPathPattern());
        if (indexes.size() > 0) {
            value.append("^");
            value.append(indexes.stream().collect((Collector<? super Object, ?, String>)Collectors.joining("^")));
        }
        return value;
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
    
    public String getSessionId() {
        return "";
    }
    
    @Override
    public String toString() {
        return "MgdDevReportState [deviceId=" + this.deviceId + ", collectionTimeStamp=" + this.collectionTimeStamp + ", modelType=" + this.modelType + ", parameters=" + this.parameters + "]";
    }
    
    public List<ParameterDTO> getencodedParameterList() {
        return this.parameters;
    }
    
    static {
        COMPARE_BY_COLLECTION_TIME = Comparator.comparing((Function<? super MgdDevTransposeColumnState, ? extends Comparable>)MgdDevTransposeColumnState::getCollectionTimeStamp);
        MgdDevTransposeColumnState.LOG = Logger.getLogger(MgdDevTransposeColumnState.class);
    }
}
