// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class AttributesMetadataResponseEmbedded
{
    @SerializedName("attributesMetadata")
    private AttributesMetadata attributesMetadata;
    
    public AttributesMetadataResponseEmbedded() {
        this.attributesMetadata = null;
    }
    
    public AttributesMetadataResponseEmbedded attributesMetadata(final AttributesMetadata attributesMetadata) {
        this.attributesMetadata = attributesMetadata;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public AttributesMetadata getAttributesMetadata() {
        return this.attributesMetadata;
    }
    
    public void setAttributesMetadata(final AttributesMetadata attributesMetadata) {
        this.attributesMetadata = attributesMetadata;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final AttributesMetadataResponseEmbedded attributesMetadataResponseEmbedded = (AttributesMetadataResponseEmbedded)o;
        return Objects.equals(this.attributesMetadata, attributesMetadataResponseEmbedded.attributesMetadata);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.attributesMetadata);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class AttributesMetadataResponseEmbedded {\n");
        sb.append("    attributesMetadata: ").append(this.toIndentedString(this.attributesMetadata)).append("\n");
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
