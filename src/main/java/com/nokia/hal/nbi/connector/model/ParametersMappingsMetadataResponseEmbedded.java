// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class ParametersMappingsMetadataResponseEmbedded
{
    @SerializedName("parametersMappingsMetadata")
    private ParametersMappingsMetadata parametersMappingsMetadata;
    
    public ParametersMappingsMetadataResponseEmbedded() {
        this.parametersMappingsMetadata = null;
    }
    
    public ParametersMappingsMetadataResponseEmbedded parametersMappingsMetadata(final ParametersMappingsMetadata parametersMappingsMetadata) {
        this.parametersMappingsMetadata = parametersMappingsMetadata;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public ParametersMappingsMetadata getParametersMappingsMetadata() {
        return this.parametersMappingsMetadata;
    }
    
    public void setParametersMappingsMetadata(final ParametersMappingsMetadata parametersMappingsMetadata) {
        this.parametersMappingsMetadata = parametersMappingsMetadata;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ParametersMappingsMetadataResponseEmbedded parametersMappingsMetadataResponseEmbedded = (ParametersMappingsMetadataResponseEmbedded)o;
        return Objects.equals(this.parametersMappingsMetadata, parametersMappingsMetadataResponseEmbedded.parametersMappingsMetadata);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.parametersMappingsMetadata);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class ParametersMappingsMetadataResponseEmbedded {\n");
        sb.append("    parametersMappingsMetadata: ").append(this.toIndentedString(this.parametersMappingsMetadata)).append("\n");
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
