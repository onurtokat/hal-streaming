// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class ParametersMappingsMetadataResponse
{
    @SerializedName("_embedded")
    private ParametersMappingsMetadataResponseEmbedded embedded;
    
    public ParametersMappingsMetadataResponse() {
        this.embedded = null;
    }
    
    public ParametersMappingsMetadataResponse embedded(final ParametersMappingsMetadataResponseEmbedded embedded) {
        this.embedded = embedded;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public ParametersMappingsMetadataResponseEmbedded getEmbedded() {
        return this.embedded;
    }
    
    public void setEmbedded(final ParametersMappingsMetadataResponseEmbedded embedded) {
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
        final ParametersMappingsMetadataResponse parametersMappingsMetadataResponse = (ParametersMappingsMetadataResponse)o;
        return Objects.equals(this.embedded, parametersMappingsMetadataResponse.embedded);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.embedded);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class ParametersMappingsMetadataResponse {\n");
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
