// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class ConfiguredFlowsResponseEmbedded
{
    @SerializedName("flows")
    private ConfiguredFlowsMetadata flows;
    
    public ConfiguredFlowsResponseEmbedded() {
        this.flows = null;
    }
    
    public ConfiguredFlowsResponseEmbedded flows(final ConfiguredFlowsMetadata flows) {
        this.flows = flows;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public ConfiguredFlowsMetadata getFlows() {
        return this.flows;
    }
    
    public void setFlows(final ConfiguredFlowsMetadata flows) {
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
        final ConfiguredFlowsResponseEmbedded configuredFlowsResponseEmbedded = (ConfiguredFlowsResponseEmbedded)o;
        return Objects.equals(this.flows, configuredFlowsResponseEmbedded.flows);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.flows);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class ConfiguredFlowsResponseEmbedded {\n");
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
