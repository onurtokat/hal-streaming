// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class InsightsFormula
{
    @SerializedName("flow")
    private String flow;
    @SerializedName("flowName")
    private String flowName;
    @SerializedName("monitoredPointTypes")
    private List<MonitoredPointTypesFormulas> monitoredPointTypes;
    
    public InsightsFormula() {
        this.flow = null;
        this.flowName = null;
        this.monitoredPointTypes = new ArrayList<MonitoredPointTypesFormulas>();
    }
    
    public InsightsFormula flow(final String flow) {
        this.flow = flow;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public String getFlow() {
        return this.flow;
    }
    
    public void setFlow(final String flow) {
        this.flow = flow;
    }
    
    public InsightsFormula flowName(final String flowName) {
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
    
    public InsightsFormula monitoredPointTypes(final List<MonitoredPointTypesFormulas> monitoredPointTypes) {
        this.monitoredPointTypes = monitoredPointTypes;
        return this;
    }
    
    public InsightsFormula addMonitoredPointTypesItem(final MonitoredPointTypesFormulas monitoredPointTypesItem) {
        this.monitoredPointTypes.add(monitoredPointTypesItem);
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public List<MonitoredPointTypesFormulas> getMonitoredPointTypes() {
        return this.monitoredPointTypes;
    }
    
    public void setMonitoredPointTypes(final List<MonitoredPointTypesFormulas> monitoredPointTypes) {
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
        final InsightsFormula insightsFormula = (InsightsFormula)o;
        return Objects.equals(this.flow, insightsFormula.flow) && Objects.equals(this.flowName, insightsFormula.flowName) && Objects.equals(this.monitoredPointTypes, insightsFormula.monitoredPointTypes);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.flow, this.flowName, this.monitoredPointTypes);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class InsightsFormula {\n");
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
