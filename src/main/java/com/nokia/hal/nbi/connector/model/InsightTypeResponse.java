// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class InsightTypeResponse
{
    @SerializedName("insightTypes")
    private List<String> insightTypes;
    
    public InsightTypeResponse() {
        this.insightTypes = new ArrayList<String>();
    }
    
    public InsightTypeResponse insightTypes(final List<String> insightTypes) {
        this.insightTypes = insightTypes;
        return this;
    }
    
    public InsightTypeResponse addInsightTypesItem(final String insightTypesItem) {
        this.insightTypes.add(insightTypesItem);
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Array of insightType.")
    public List<String> getInsightTypes() {
        return this.insightTypes;
    }
    
    public void setInsightTypes(final List<String> insightTypes) {
        this.insightTypes = insightTypes;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final InsightTypeResponse insightTypeResponse = (InsightTypeResponse)o;
        return Objects.equals(this.insightTypes, insightTypeResponse.insightTypes);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.insightTypes);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class InsightTypeResponse {\n");
        sb.append("    insightTypes: ").append(this.toIndentedString(this.insightTypes)).append("\n");
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
