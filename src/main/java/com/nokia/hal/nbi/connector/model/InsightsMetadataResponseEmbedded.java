// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class InsightsMetadataResponseEmbedded
{
    @SerializedName("insightsMetadata")
    private InsightsMetadata insightsMetadata;
    
    public InsightsMetadataResponseEmbedded() {
        this.insightsMetadata = null;
    }
    
    public InsightsMetadataResponseEmbedded insightsMetadata(final InsightsMetadata insightsMetadata) {
        this.insightsMetadata = insightsMetadata;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public InsightsMetadata getInsightsMetadata() {
        return this.insightsMetadata;
    }
    
    public void setInsightsMetadata(final InsightsMetadata insightsMetadata) {
        this.insightsMetadata = insightsMetadata;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final InsightsMetadataResponseEmbedded insightsMetadataResponseEmbedded = (InsightsMetadataResponseEmbedded)o;
        return Objects.equals(this.insightsMetadata, insightsMetadataResponseEmbedded.insightsMetadata);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.insightsMetadata);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class InsightsMetadataResponseEmbedded {\n");
        sb.append("    insightsMetadata: ").append(this.toIndentedString(this.insightsMetadata)).append("\n");
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
