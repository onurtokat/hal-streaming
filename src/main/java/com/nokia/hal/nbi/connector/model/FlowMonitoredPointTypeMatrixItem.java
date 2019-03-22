// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "Flows information.")
public class FlowMonitoredPointTypeMatrixItem
{
    @SerializedName("flow")
    private String flow;
    @SerializedName("flowName")
    private String flowName;
    @SerializedName("monitoredPointTypes")
    private List<FlowMonitoredPointType> monitoredPointTypes;
    
    public FlowMonitoredPointTypeMatrixItem() {
        this.flow = null;
        this.flowName = null;
        this.monitoredPointTypes = new ArrayList<FlowMonitoredPointType>();
    }
    
    public FlowMonitoredPointTypeMatrixItem flow(final String flow) {
        this.flow = flow;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "Unique identifier representing a flow.")
    public String getFlow() {
        return this.flow;
    }
    
    public void setFlow(final String flow) {
        this.flow = flow;
    }
    
    public FlowMonitoredPointTypeMatrixItem flowName(final String flowName) {
        this.flowName = flowName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "flow name.")
    public String getFlowName() {
        return this.flowName;
    }
    
    public void setFlowName(final String flowName) {
        this.flowName = flowName;
    }
    
    public FlowMonitoredPointTypeMatrixItem monitoredPointTypes(final List<FlowMonitoredPointType> monitoredPointTypes) {
        this.monitoredPointTypes = monitoredPointTypes;
        return this;
    }
    
    public FlowMonitoredPointTypeMatrixItem addMonitoredPointTypesItem(final FlowMonitoredPointType monitoredPointTypesItem) {
        this.monitoredPointTypes.add(monitoredPointTypesItem);
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public List<FlowMonitoredPointType> getMonitoredPointTypes() {
        return this.monitoredPointTypes;
    }
    
    public void setMonitoredPointTypes(final List<FlowMonitoredPointType> monitoredPointTypes) {
        this.monitoredPointTypes = monitoredPointTypes;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FlowMonitoredPointTypeMatrixItem flowMonitoredPointTypeMatrixItem = (FlowMonitoredPointTypeMatrixItem)o;
        return Objects.equals(this.flow, flowMonitoredPointTypeMatrixItem.flow) && Objects.equals(this.flowName, flowMonitoredPointTypeMatrixItem.flowName) && Objects.equals(this.monitoredPointTypes, flowMonitoredPointTypeMatrixItem.monitoredPointTypes);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.flow, this.flowName, this.monitoredPointTypes);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class FlowMonitoredPointTypeMatrixItem {\n");
        sb.append("    flow: ").append(this.toIndentedString(this.flow)).append("\n");
        sb.append("    flowName: ").append(this.toIndentedString(this.flowName)).append("\n");
        sb.append("    monitoredPointTypes: ").append(this.toIndentedString(this.monitoredPointTypes)).append("\n");
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
