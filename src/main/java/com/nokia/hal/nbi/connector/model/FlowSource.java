// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class FlowSource
{
    @SerializedName("constraintSourceUID")
    private String constraintSourceUID;
    @SerializedName("type")
    private String type;
    @SerializedName("monitoredPointType")
    private String monitoredPointType;
    @SerializedName("monitoredPointTypeName")
    private String monitoredPointTypeName;
    @SerializedName("alias")
    private String alias;
    @SerializedName("flow")
    private String flow;
    
    public FlowSource() {
        this.constraintSourceUID = null;
        this.type = null;
        this.monitoredPointType = null;
        this.monitoredPointTypeName = null;
        this.alias = null;
        this.flow = null;
    }
    
    public FlowSource constraintSourceUID(final String constraintSourceUID) {
        this.constraintSourceUID = constraintSourceUID;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "unique ID for this source")
    public String getConstraintSourceUID() {
        return this.constraintSourceUID;
    }
    
    public void setConstraintSourceUID(final String constraintSourceUID) {
        this.constraintSourceUID = constraintSourceUID;
    }
    
    public FlowSource type(final String type) {
        this.type = type;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "source <insights category>|Mappedtem|Attribute")
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public FlowSource monitoredPointType(final String monitoredPointType) {
        this.monitoredPointType = monitoredPointType;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "source monitored point type ID.")
    public String getMonitoredPointType() {
        return this.monitoredPointType;
    }
    
    public void setMonitoredPointType(final String monitoredPointType) {
        this.monitoredPointType = monitoredPointType;
    }
    
    public FlowSource monitoredPointTypeName(final String monitoredPointTypeName) {
        this.monitoredPointTypeName = monitoredPointTypeName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "source monitored point type name.")
    public String getMonitoredPointTypeName() {
        return this.monitoredPointTypeName;
    }
    
    public void setMonitoredPointTypeName(final String monitoredPointTypeName) {
        this.monitoredPointTypeName = monitoredPointTypeName;
    }
    
    public FlowSource alias(final String alias) {
        this.alias = alias;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "data source alias (example table alias).")
    public String getAlias() {
        return this.alias;
    }
    
    public void setAlias(final String alias) {
        this.alias = alias;
    }
    
    public FlowSource flow(final String flow) {
        this.flow = flow;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "filter on source flow.")
    public String getFlow() {
        return this.flow;
    }
    
    public void setFlow(final String flow) {
        this.flow = flow;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FlowSource flowSource = (FlowSource)o;
        return Objects.equals(this.constraintSourceUID, flowSource.constraintSourceUID) && Objects.equals(this.type, flowSource.type) && Objects.equals(this.monitoredPointType, flowSource.monitoredPointType) && Objects.equals(this.monitoredPointTypeName, flowSource.monitoredPointTypeName) && Objects.equals(this.alias, flowSource.alias) && Objects.equals(this.flow, flowSource.flow);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.constraintSourceUID, this.type, this.monitoredPointType, this.monitoredPointTypeName, this.alias, this.flow);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class FlowSource {\n");
        sb.append("    constraintSourceUID: ").append(this.toIndentedString(this.constraintSourceUID)).append("\n");
        sb.append("    type: ").append(this.toIndentedString(this.type)).append("\n");
        sb.append("    monitoredPointType: ").append(this.toIndentedString(this.monitoredPointType)).append("\n");
        sb.append("    monitoredPointTypeName: ").append(this.toIndentedString(this.monitoredPointTypeName)).append("\n");
        sb.append("    alias: ").append(this.toIndentedString(this.alias)).append("\n");
        sb.append("    flow: ").append(this.toIndentedString(this.flow)).append("\n");
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
