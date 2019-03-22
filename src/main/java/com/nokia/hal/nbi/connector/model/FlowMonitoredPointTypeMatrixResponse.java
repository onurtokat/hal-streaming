// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class FlowMonitoredPointTypeMatrixResponse
{
    @SerializedName("_embedded")
    private FlowMonitoredPointTypeMatrixResponseEmbedded embedded;
    
    public FlowMonitoredPointTypeMatrixResponse() {
        this.embedded = null;
    }
    
    public FlowMonitoredPointTypeMatrixResponse embedded(final FlowMonitoredPointTypeMatrixResponseEmbedded embedded) {
        this.embedded = embedded;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public FlowMonitoredPointTypeMatrixResponseEmbedded getEmbedded() {
        return this.embedded;
    }
    
    public void setEmbedded(final FlowMonitoredPointTypeMatrixResponseEmbedded embedded) {
        this.embedded = embedded;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FlowMonitoredPointTypeMatrixResponse flowMonitoredPointTypeMatrixResponse = (FlowMonitoredPointTypeMatrixResponse)o;
        return Objects.equals(this.embedded, flowMonitoredPointTypeMatrixResponse.embedded);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.embedded);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class FlowMonitoredPointTypeMatrixResponse {\n");
        sb.append("    embedded: ").append(this.toIndentedString(this.embedded)).append("\n");
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
