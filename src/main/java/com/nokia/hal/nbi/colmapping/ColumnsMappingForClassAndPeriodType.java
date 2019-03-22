// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import java.util.HashMap;
import java.util.Map;

public class ColumnsMappingForClassAndPeriodType
{
    private String entityClass;
    private PeriodType periodType;
    private Integer gpDuration;
    private Map<String, InsightAdditionalInfo> mappings;
    
    public ColumnsMappingForClassAndPeriodType() {
        this.mappings = new HashMap<String, InsightAdditionalInfo>();
    }
    
    ColumnsMappingForClassAndPeriodType(final String entityClass, final PeriodType periodType, final Integer gpDuration) {
        this.mappings = new HashMap<String, InsightAdditionalInfo>();
        this.entityClass = entityClass;
        this.periodType = periodType;
        this.gpDuration = gpDuration;
    }
    
    public String getEntityClass() {
        return this.entityClass;
    }
    
    public void setEntityClass(final String entityClass) {
        this.entityClass = entityClass;
    }
    
    public PeriodType getPeriodType() {
        return this.periodType;
    }
    
    public void setPeriodType(final PeriodType periodType) {
        this.periodType = periodType;
    }
    
    public Integer getGpDuration() {
        return this.gpDuration;
    }
    
    public void setGpDuration(final Integer gpDuration) {
        this.gpDuration = gpDuration;
    }
    
    public Map<String, InsightAdditionalInfo> getMappings() {
        return this.mappings;
    }
    
    public void setMappings(final Map<String, InsightAdditionalInfo> mappings) {
        this.mappings = mappings;
    }
    
    public ColumnsMappingForClassAndPeriodType addMappings(final Map<String, InsightAdditionalInfo> mappings) {
        this.getMappings().putAll(mappings);
        return this;
    }
    
    public ColumnsMappingForClassAndPeriodType mergeWith(final ColumnsMappingForClassAndPeriodType b) {
        this.entityClass = b.getEntityClass();
        this.periodType = b.getPeriodType();
        this.gpDuration = b.getGpDuration();
        this.mappings.putAll(b.getMappings());
        return this;
    }
}
