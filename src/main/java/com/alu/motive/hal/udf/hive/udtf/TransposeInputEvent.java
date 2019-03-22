// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udtf;

import java.util.HashMap;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import java.util.Map;

public class TransposeInputEvent
{
    private String monitoredPointId;
    private String monitoredPointType;
    private String accountId;
    private String periodType;
    private Long timestamp;
    private Map<String, String> useCasesToSeverity;
    
    public static TransposeInputEvent createEvent(final PrimitiveObjectInspector[] inputOI, final Object[] args) {
        int i = 2;
        final String mpId = (String)inputOI[i].getPrimitiveJavaObject(args[i]);
        ++i;
        final String accountId = (String)inputOI[i].getPrimitiveJavaObject(args[i]);
        ++i;
        final Long timeStamp = (Long)inputOI[i].getPrimitiveJavaObject(args[i]);
        ++i;
        final String mpType = (String)inputOI[i].getPrimitiveJavaObject(args[i]);
        ++i;
        final String periodType = (String)inputOI[i].getPrimitiveJavaObject(args[i]);
        ++i;
        final String ucId = (String)inputOI[i].getPrimitiveJavaObject(args[i]);
        ++i;
        final String ucSeverity = (String)inputOI[i].getPrimitiveJavaObject(args[i]);
        ++i;
        final TransposeInputEvent event = new TransposeInputEvent(mpId, accountId, mpType, timeStamp, periodType);
        event.addUsecase(ucId, ucSeverity);
        return event;
    }
    
    protected TransposeInputEvent(final String monitoredPointId, final String accountId, final String monitoredPointType, final Long timestamp, final String periodType) {
        this.monitoredPointId = monitoredPointId;
        this.monitoredPointType = monitoredPointType;
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.periodType = periodType;
        this.useCasesToSeverity = new HashMap<String, String>();
    }
    
    public void addUsecase(final String uc, final String sev) {
        this.useCasesToSeverity.put(uc, sev);
    }
    
    public Map.Entry<String, String> getUsecase() {
        Map.Entry<String, String> uc = null;
        if (!this.useCasesToSeverity.keySet().isEmpty()) {
            uc = this.useCasesToSeverity.entrySet().iterator().next();
        }
        return uc;
    }
    
    public String getMonitoredPointId() {
        return this.monitoredPointId;
    }
    
    public String getMonitoredPointType() {
        return this.monitoredPointType;
    }
    
    public String getAccountId() {
        return this.accountId;
    }
    
    public Long getTimestamp() {
        return this.timestamp;
    }
    
    public String getPeriodType() {
        return this.periodType;
    }
    
    public Map<String, String> getUsecaseToSeveritiesMap() {
        return this.useCasesToSeverity;
    }
    
    public Boolean hasTheSameMonitoredPoint(final TransposeInputEvent otherEvent) {
        return this.monitoredPointId.equals(otherEvent.getMonitoredPointId());
    }
}
