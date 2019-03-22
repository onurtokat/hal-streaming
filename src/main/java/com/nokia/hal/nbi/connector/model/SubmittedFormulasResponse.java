// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class SubmittedFormulasResponse
{
    @SerializedName("_embedded")
    private SubmittedFormulasResponseEmbedded embedded;
    
    public SubmittedFormulasResponse() {
        this.embedded = null;
    }
    
    public SubmittedFormulasResponse embedded(final SubmittedFormulasResponseEmbedded embedded) {
        this.embedded = embedded;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public SubmittedFormulasResponseEmbedded getEmbedded() {
        return this.embedded;
    }
    
    public void setEmbedded(final SubmittedFormulasResponseEmbedded embedded) {
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
        final SubmittedFormulasResponse submittedFormulasResponse = (SubmittedFormulasResponse)o;
        return Objects.equals(this.embedded, submittedFormulasResponse.embedded);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.embedded);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class SubmittedFormulasResponse {\n");
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
