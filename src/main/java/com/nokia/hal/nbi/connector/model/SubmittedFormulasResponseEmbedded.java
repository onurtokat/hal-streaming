// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class SubmittedFormulasResponseEmbedded
{
    @SerializedName("submittedFormulas")
    private SubmittedFormulas submittedFormulas;
    
    public SubmittedFormulasResponseEmbedded() {
        this.submittedFormulas = null;
    }
    
    public SubmittedFormulasResponseEmbedded submittedFormulas(final SubmittedFormulas submittedFormulas) {
        this.submittedFormulas = submittedFormulas;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public SubmittedFormulas getSubmittedFormulas() {
        return this.submittedFormulas;
    }
    
    public void setSubmittedFormulas(final SubmittedFormulas submittedFormulas) {
        this.submittedFormulas = submittedFormulas;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SubmittedFormulasResponseEmbedded submittedFormulasResponseEmbedded = (SubmittedFormulasResponseEmbedded)o;
        return Objects.equals(this.submittedFormulas, submittedFormulasResponseEmbedded.submittedFormulas);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.submittedFormulas);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class SubmittedFormulasResponseEmbedded {\n");
        sb.append("    submittedFormulas: ").append(this.toIndentedString(this.submittedFormulas)).append("\n");
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
