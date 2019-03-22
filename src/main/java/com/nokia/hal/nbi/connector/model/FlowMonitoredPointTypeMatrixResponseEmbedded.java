// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class FlowMonitoredPointTypeMatrixResponseEmbedded
{
    @SerializedName("flows")
    private FlowMonitoredPointTypeMatrixMetadata flows;
    
    public FlowMonitoredPointTypeMatrixResponseEmbedded() {
        this.flows = null;
    }
    
    public FlowMonitoredPointTypeMatrixResponseEmbedded flows(final FlowMonitoredPointTypeMatrixMetadata flows) {
        this.flows = flows;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public FlowMonitoredPointTypeMatrixMetadata getFlows() {
        return this.flows;
    }
    
    public void setFlows(final FlowMonitoredPointTypeMatrixMetadata flows) {
        this.flows = flows;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FlowMonitoredPointTypeMatrixResponseEmbedded flowMonitoredPointTypeMatrixResponseEmbedded = (FlowMonitoredPointTypeMatrixResponseEmbedded)o;
        return Objects.equals(this.flows, flowMonitoredPointTypeMatrixResponseEmbedded.flows);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.flows);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class FlowMonitoredPointTypeMatrixResponseEmbedded {\n");
        sb.append("    flows: ").append(this.toIndentedString(this.flows)).append("\n");
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
