// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModel;
import java.util.ArrayList;

@ApiModel(description = "Array of insightsMetadata.")
public class InsightsMetadata extends ArrayList<InsightsMetadataItem>
{
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && super.equals(o));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class InsightsMetadata {\n");
        sb.append("    ").append(this.toIndentedString(super.toString())).append("\n");
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
