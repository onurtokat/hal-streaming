// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class ConfiguredFlowsResponse
{
    @SerializedName("_embedded")
    private ConfiguredFlowsResponseEmbedded embedded;
    
    public ConfiguredFlowsResponse() {
        this.embedded = null;
    }
    
    public ConfiguredFlowsResponse embedded(final ConfiguredFlowsResponseEmbedded embedded) {
        this.embedded = embedded;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public ConfiguredFlowsResponseEmbedded getEmbedded() {
        return this.embedded;
    }
    
    public void setEmbedded(final ConfiguredFlowsResponseEmbedded embedded) {
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
        final ConfiguredFlowsResponse configuredFlowsResponse = (ConfiguredFlowsResponse)o;
        return Objects.equals(this.embedded, configuredFlowsResponse.embedded);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.embedded);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class ConfiguredFlowsResponse {\n");
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
