// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class FlowsStatesResponseEmbedded
{
    @SerializedName("tasks")
    private FlowsStates tasks;
    
    public FlowsStatesResponseEmbedded() {
        this.tasks = null;
    }
    
    public FlowsStatesResponseEmbedded tasks(final FlowsStates tasks) {
        this.tasks = tasks;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public FlowsStates getTasks() {
        return this.tasks;
    }
    
    public void setTasks(final FlowsStates tasks) {
        this.tasks = tasks;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FlowsStatesResponseEmbedded flowsStatesResponseEmbedded = (FlowsStatesResponseEmbedded)o;
        return Objects.equals(this.tasks, flowsStatesResponseEmbedded.tasks);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.tasks);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class FlowsStatesResponseEmbedded {\n");
        sb.append("    tasks: ").append(this.toIndentedString(this.tasks)).append("\n");
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
