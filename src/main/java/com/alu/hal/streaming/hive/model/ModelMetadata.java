// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.util.Optional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.io.Serializable;

public class ModelMetadata implements Serializable
{
    private final Map<Integer, ParamMetaData> paramMetaDataMap;
    private final TransposeTableMetadata transposeTableMetadata;
    private final Set<ParamMetaData> devUpTimeParamMetaDataSet;
    private Map<String, List<ParamMetaData>> paramMetadatabyParamNameWithoutPath;
    
    public ModelMetadata(final TransposeTableMetadata transposeTableMetadata, final Map<Integer, ParamMetaData> paramMetaDataMap, final Set<ParamMetaData> devUpTimeParamMetaDataSet) {
        this.transposeTableMetadata = transposeTableMetadata;
        this.paramMetaDataMap = paramMetaDataMap;
        this.devUpTimeParamMetaDataSet = devUpTimeParamMetaDataSet;
        this.paramMetadatabyParamNameWithoutPath = ParamMetaData.byParameterNameWithoutPath(paramMetaDataMap.values());
    }
    
    public Collection<ParamMetaData> getParamMetaDataList() {
        return this.paramMetaDataMap.values();
    }
    
    public Optional<ParamMetaData> getParamMetaData(final Integer paramMetaDataId) {
        return this.paramMetaDataMap.containsKey(paramMetaDataId) ? Optional.of(this.paramMetaDataMap.get(paramMetaDataId)) : Optional.empty();
    }
    
    public Map<Integer, ParamMetaData> getParamMetaDataMap() {
        return this.paramMetaDataMap;
    }
    
    public TransposeTableMetadata getTransposeTableMetadata() {
        return this.transposeTableMetadata;
    }
    
    public Set<ParamMetaData> getDevUpTimeParamMetaDataSet() {
        return this.devUpTimeParamMetaDataSet;
    }
    
    public Map<String, List<ParamMetaData>> getParamMetadatabyParamNameWithoutPath() {
        return this.paramMetadatabyParamNameWithoutPath;
    }
    
    @Override
    public String toString() {
        return "ModelMetadata{paramMetaDataMap=" + this.paramMetaDataMap + ", transposeTableMetadata=" + this.transposeTableMetadata + ", devUpTimeParamMetaDataSet=" + this.devUpTimeParamMetaDataSet + ", paramMetadatabyParamNameWithoutPath=" + this.paramMetadatabyParamNameWithoutPath + '}';
    }
}
