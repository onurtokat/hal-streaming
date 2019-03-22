// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "flows state information.")
public class FlowsStatesItem
{
    @SerializedName("flowStateUID")
    private String flowStateUID;
    @SerializedName("domain")
    private String domain;
    @SerializedName("flow")
    private String flow;
    @SerializedName("flowState")
    private String flowState;
    @SerializedName("submissionTS")
    private Long submissionTS;
    @SerializedName("lastUpdateTS")
    private Long lastUpdateTS;
    @SerializedName("latestTasktype")
    private String latestTasktype;
    @SerializedName("latestTaskResult")
    private String latestTaskResult;
    @SerializedName("log")
    private String log;
    
    public FlowsStatesItem() {
        this.flowStateUID = null;
        this.domain = null;
        this.flow = null;
        this.flowState = null;
        this.submissionTS = null;
        this.lastUpdateTS = null;
        this.latestTasktype = null;
        this.latestTaskResult = null;
        this.log = null;
    }
    
    public FlowsStatesItem flowStateUID(final String flowStateUID) {
        this.flowStateUID = flowStateUID;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "Unique identifier representing a flowState.")
    public String getFlowStateUID() {
        return this.flowStateUID;
    }
    
    public void setFlowStateUID(final String flowStateUID) {
        this.flowStateUID = flowStateUID;
    }
    
    public FlowsStatesItem domain(final String domain) {
        this.domain = domain;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "insight's domain.")
    public String getDomain() {
        return this.domain;
    }
    
    public void setDomain(final String domain) {
        this.domain = domain;
    }
    
    public FlowsStatesItem flow(final String flow) {
        this.flow = flow;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "Processing flow.")
    public String getFlow() {
        return this.flow;
    }
    
    public void setFlow(final String flow) {
        this.flow = flow;
    }
    
    public FlowsStatesItem flowState(final String flowState) {
        this.flowState = flowState;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Latest flow state (examples: Modified, Submitted, Applying Changes, Changes Applied).")
    public String getFlowState() {
        return this.flowState;
    }
    
    public void setFlowState(final String flowState) {
        this.flowState = flowState;
    }
    
    public FlowsStatesItem submissionTS(final Long submissionTS) {
        this.submissionTS = submissionTS;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "flow's metadata submission timestamp.")
    public Long getSubmissionTS() {
        return this.submissionTS;
    }
    
    public void setSubmissionTS(final Long submissionTS) {
        this.submissionTS = submissionTS;
    }
    
    public FlowsStatesItem lastUpdateTS(final Long lastUpdateTS) {
        this.lastUpdateTS = lastUpdateTS;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "latest state update timestamp.")
    public Long getLastUpdateTS() {
        return this.lastUpdateTS;
    }
    
    public void setLastUpdateTS(final Long lastUpdateTS) {
        this.lastUpdateTS = lastUpdateTS;
    }
    
    public FlowsStatesItem latestTasktype(final String latestTasktype) {
        this.latestTasktype = latestTasktype;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Latest task type.")
    public String getLatestTasktype() {
        return this.latestTasktype;
    }
    
    public void setLatestTasktype(final String latestTasktype) {
        this.latestTasktype = latestTasktype;
    }
    
    public FlowsStatesItem latestTaskResult(final String latestTaskResult) {
        this.latestTaskResult = latestTaskResult;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Latest task result (Success, Failed).")
    public String getLatestTaskResult() {
        return this.latestTaskResult;
    }
    
    public void setLatestTaskResult(final String latestTaskResult) {
        this.latestTaskResult = latestTaskResult;
    }
    
    public FlowsStatesItem log(final String log) {
        this.log = log;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "additional information")
    public String getLog() {
        return this.log;
    }
    
    public void setLog(final String log) {
        this.log = log;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FlowsStatesItem flowsStatesItem = (FlowsStatesItem)o;
        return Objects.equals(this.flowStateUID, flowsStatesItem.flowStateUID) && Objects.equals(this.domain, flowsStatesItem.domain) && Objects.equals(this.flow, flowsStatesItem.flow) && Objects.equals(this.flowState, flowsStatesItem.flowState) && Objects.equals(this.submissionTS, flowsStatesItem.submissionTS) && Objects.equals(this.lastUpdateTS, flowsStatesItem.lastUpdateTS) && Objects.equals(this.latestTasktype, flowsStatesItem.latestTasktype) && Objects.equals(this.latestTaskResult, flowsStatesItem.latestTaskResult) && Objects.equals(this.log, flowsStatesItem.log);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.flowStateUID, this.domain, this.flow, this.flowState, this.submissionTS, this.lastUpdateTS, this.latestTasktype, this.latestTaskResult, this.log);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class FlowsStatesItem {\n");
        sb.append("    flowStateUID: ").append(this.toIndentedString(this.flowStateUID)).append("\n");
        sb.append("    domain: ").append(this.toIndentedString(this.domain)).append("\n");
        sb.append("    flow: ").append(this.toIndentedString(this.flow)).append("\n");
        sb.append("    flowState: ").append(this.toIndentedString(this.flowState)).append("\n");
        sb.append("    submissionTS: ").append(this.toIndentedString(this.submissionTS)).append("\n");
        sb.append("    lastUpdateTS: ").append(this.toIndentedString(this.lastUpdateTS)).append("\n");
        sb.append("    latestTasktype: ").append(this.toIndentedString(this.latestTasktype)).append("\n");
        sb.append("    latestTaskResult: ").append(this.toIndentedString(this.latestTaskResult)).append("\n");
        sb.append("    log: ").append(this.toIndentedString(this.log)).append("\n");
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
