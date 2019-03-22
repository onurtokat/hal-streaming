// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.spark.api.java.Optional;
import java.io.Serializable;

public class CounterMetaData implements Serializable
{
    private Optional<String> devUpTimeParameterRef;
    private final Long maxValueThreshold;
    private List<ParamMetaData> devUpTimeParamMetaDataList;
    
    public CounterMetaData(final Optional<String> devUpTimeParameterRef, final Long maxValueThreshold) {
        this.devUpTimeParamMetaDataList = new ArrayList<ParamMetaData>();
        this.devUpTimeParameterRef = devUpTimeParameterRef;
        this.maxValueThreshold = maxValueThreshold;
    }
    
    public Optional<String> getDevUpTimeParameterRef() {
        return this.devUpTimeParameterRef;
    }
    
    public Long getMaxValueThreshold() {
        return this.maxValueThreshold;
    }
    
    public List<ParamMetaData> getDevUpTimeParamMetaDataList() {
        return this.devUpTimeParamMetaDataList;
    }
    
    public void setDevUpTimeParameterRef(final Optional<String> devUpTimeParameterRef) {
        this.devUpTimeParameterRef = devUpTimeParameterRef;
    }
    
    public void setDevUpTimeParamMetaList(final List<ParamMetaData> devUpTimeParamMetaDataList) {
        this.devUpTimeParamMetaDataList = devUpTimeParamMetaDataList;
    }
    
    public boolean hasDevUpTimeReference() {
        return this.devUpTimeParameterRef.isPresent() && this.devUpTimeParamMetaDataList.size() > 0;
    }
    
    @Override
    public String toString() {
        return "CounterMetaData{devUpTimeParameterRef=" + (this.devUpTimeParameterRef.isPresent() ? ((String)this.devUpTimeParameterRef.get()) : "<empty>") + ", maxValueThreshold=" + this.maxValueThreshold + ", devUpTimeParamMetaDataList=" + this.devUpTimeParamMetaDataList + '}';
    }
}
