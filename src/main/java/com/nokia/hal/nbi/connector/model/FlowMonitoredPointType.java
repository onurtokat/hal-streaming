// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class FlowMonitoredPointType
{
    @SerializedName("monitoredPointType")
    private String monitoredPointType;
    @SerializedName("monitoredPointTypeName")
    private String monitoredPointTypeName;
    @SerializedName("isSupported")
    private Boolean isSupported;
    
    public FlowMonitoredPointType() {
        this.monitoredPointType = null;
        this.monitoredPointTypeName = null;
        this.isSupported = null;
    }
    
    public FlowMonitoredPointType monitoredPointType(final String monitoredPointType) {
        this.monitoredPointType = monitoredPointType;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public String getMonitoredPointType() {
        return this.monitoredPointType;
    }
    
    public void setMonitoredPointType(final String monitoredPointType) {
        this.monitoredPointType = monitoredPointType;
    }
    
    public FlowMonitoredPointType monitoredPointTypeName(final String monitoredPointTypeName) {
        this.monitoredPointTypeName = monitoredPointTypeName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "monitored point type name.")
    public String getMonitoredPointTypeName() {
        return this.monitoredPointTypeName;
    }
    
    public void setMonitoredPointTypeName(final String monitoredPointTypeName) {
        this.monitoredPointTypeName = monitoredPointTypeName;
    }
    
    public FlowMonitoredPointType isSupported(final Boolean isSupported) {
        this.isSupported = isSupported;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "isSupported flag (true if this is not supported).")
    public Boolean getIsSupported() {
        return this.isSupported;
    }
    
    public void setIsSupported(final Boolean isSupported) {
        this.isSupported = isSupported;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FlowMonitoredPointType flowMonitoredPointType = (FlowMonitoredPointType)o;
        return Objects.equals(this.monitoredPointType, flowMonitoredPointType.monitoredPointType) && Objects.equals(this.monitoredPointTypeName, flowMonitoredPointType.monitoredPointTypeName) && Objects.equals(this.isSupported, flowMonitoredPointType.isSupported);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.monitoredPointType, this.monitoredPointTypeName, this.isSupported);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class FlowMonitoredPointType {\n");
        sb.append("    monitoredPointType: ").append(this.toIndentedString(this.monitoredPointType)).append("\n");
        sb.append("    monitoredPointTypeName: ").append(this.toIndentedString(this.monitoredPointTypeName)).append("\n");
        sb.append("    isSupported: ").append(this.toIndentedString(this.isSupported)).append("\n");
        sb.append("}");
        return sb.toString();
    }
    
    private String toIndentedString(final Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
