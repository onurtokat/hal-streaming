// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "Configured flow information.")
public class ConfiguredFlowsItem
{
    @SerializedName("constraintFlowUID")
    private String constraintFlowUID;
    @SerializedName("flow")
    private String flow;
    @SerializedName("flowName")
    private String flowName;
    @SerializedName("domain")
    private String domain;
    @SerializedName("domainName")
    private String domainName;
    @SerializedName("category")
    private String category;
    @SerializedName("monitoredPointType")
    private String monitoredPointType;
    @SerializedName("monitoredPointTypeName")
    private String monitoredPointTypeName;
    @SerializedName("nbrCustomInsights")
    private Integer nbrCustomInsights;
    @SerializedName("handlerUrl")
    private String handlerUrl;
    @SerializedName("flowSources")
    private List<FlowSource> flowSources;
    
    public ConfiguredFlowsItem() {
        this.constraintFlowUID = null;
        this.flow = null;
        this.flowName = null;
        this.domain = null;
        this.domainName = null;
        this.category = null;
        this.monitoredPointType = null;
        this.monitoredPointTypeName = null;
        this.nbrCustomInsights = null;
        this.handlerUrl = null;
        this.flowSources = new ArrayList<FlowSource>();
    }
    
    public ConfiguredFlowsItem constraintFlowUID(final String constraintFlowUID) {
        this.constraintFlowUID = constraintFlowUID;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "unique ID for this flow constraint")
    public String getConstraintFlowUID() {
        return this.constraintFlowUID;
    }
    
    public void setConstraintFlowUID(final String constraintFlowUID) {
        this.constraintFlowUID = constraintFlowUID;
    }
    
    public ConfiguredFlowsItem flow(final String flow) {
        this.flow = flow;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "Unique identifier representing a flow.")
    public String getFlow() {
        return this.flow;
    }
    
    public void setFlow(final String flow) {
        this.flow = flow;
    }
    
    public ConfiguredFlowsItem flowName(final String flowName) {
        this.flowName = flowName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "flow name.")
    public String getFlowName() {
        return this.flowName;
    }
    
    public void setFlowName(final String flowName) {
        this.flowName = flowName;
    }
    
    public ConfiguredFlowsItem domain(final String domain) {
        this.domain = domain;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "Unique identifier representing a domain.")
    public String getDomain() {
        return this.domain;
    }
    
    public void setDomain(final String domain) {
        this.domain = domain;
    }
    
    public ConfiguredFlowsItem domainName(final String domainName) {
        this.domainName = domainName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "domain name.")
    public String getDomainName() {
        return this.domainName;
    }
    
    public void setDomainName(final String domainName) {
        this.domainName = domainName;
    }
    
    public ConfiguredFlowsItem category(final String category) {
        this.category = category;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "category (Counter,KPI,KQI,Observation).")
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(final String category) {
        this.category = category;
    }
    
    public ConfiguredFlowsItem monitoredPointType(final String monitoredPointType) {
        this.monitoredPointType = monitoredPointType;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "monitored point type ID.")
    public String getMonitoredPointType() {
        return this.monitoredPointType;
    }
    
    public void setMonitoredPointType(final String monitoredPointType) {
        this.monitoredPointType = monitoredPointType;
    }
    
    public ConfiguredFlowsItem monitoredPointTypeName(final String monitoredPointTypeName) {
        this.monitoredPointTypeName = monitoredPointTypeName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "monitored point type name.")
    public String getMonitoredPointTypeName() {
        return this.monitoredPointTypeName;
    }
    
    public void setMonitoredPointTypeName(final String monitoredPointTypeName) {
        this.monitoredPointTypeName = monitoredPointTypeName;
    }
    
    public ConfiguredFlowsItem nbrCustomInsights(final Integer nbrCustomInsights) {
        this.nbrCustomInsights = nbrCustomInsights;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "nbr of custom insights that can be created for this category, domain, flow and monitoredpointtype .")
    public Integer getNbrCustomInsights() {
        return this.nbrCustomInsights;
    }
    
    public void setNbrCustomInsights(final Integer nbrCustomInsights) {
        this.nbrCustomInsights = nbrCustomInsights;
    }
    
    public ConfiguredFlowsItem handlerUrl(final String handlerUrl) {
        this.handlerUrl = handlerUrl;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "the url of the backend application that handles this flow.")
    public String getHandlerUrl() {
        return this.handlerUrl;
    }
    
    public void setHandlerUrl(final String handlerUrl) {
        this.handlerUrl = handlerUrl;
    }
    
    public ConfiguredFlowsItem flowSources(final List<FlowSource> flowSources) {
        this.flowSources = flowSources;
        return this;
    }
    
    public ConfiguredFlowsItem addFlowSourcesItem(final FlowSource flowSourcesItem) {
        this.flowSources.add(flowSourcesItem);
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public List<FlowSource> getFlowSources() {
        return this.flowSources;
    }
    
    public void setFlowSources(final List<FlowSource> flowSources) {
        this.flowSources = flowSources;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ConfiguredFlowsItem configuredFlowsItem = (ConfiguredFlowsItem)o;
        return Objects.equals(this.constraintFlowUID, configuredFlowsItem.constraintFlowUID) && Objects.equals(this.flow, configuredFlowsItem.flow) && Objects.equals(this.flowName, configuredFlowsItem.flowName) && Objects.equals(this.domain, configuredFlowsItem.domain) && Objects.equals(this.domainName, configuredFlowsItem.domainName) && Objects.equals(this.category, configuredFlowsItem.category) && Objects.equals(this.monitoredPointType, configuredFlowsItem.monitoredPointType) && Objects.equals(this.monitoredPointTypeName, configuredFlowsItem.monitoredPointTypeName) && Objects.equals(this.nbrCustomInsights, configuredFlowsItem.nbrCustomInsights) && Objects.equals(this.handlerUrl, configuredFlowsItem.handlerUrl) && Objects.equals(this.flowSources, configuredFlowsItem.flowSources);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.constraintFlowUID, this.flow, this.flowName, this.domain, this.domainName, this.category, this.monitoredPointType, this.monitoredPointTypeName, this.nbrCustomInsights, this.handlerUrl, this.flowSources);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class ConfiguredFlowsItem {\n");
        sb.append("    constraintFlowUID: ").append(this.toIndentedString(this.constraintFlowUID)).append("\n");
        sb.append("    flow: ").append(this.toIndentedString(this.flow)).append("\n");
        sb.append("    flowName: ").append(this.toIndentedString(this.flowName)).append("\n");
        sb.append("    domain: ").append(this.toIndentedString(this.domain)).append("\n");
        sb.append("    domainName: ").append(this.toIndentedString(this.domainName)).append("\n");
        sb.append("    category: ").append(this.toIndentedString(this.category)).append("\n");
        sb.append("    monitoredPointType: ").append(this.toIndentedString(this.monitoredPointType)).append("\n");
        sb.append("    monitoredPointTypeName: ").append(this.toIndentedString(this.monitoredPointTypeName)).append("\n");
        sb.append("    nbrCustomInsights: ").append(this.toIndentedString(this.nbrCustomInsights)).append("\n");
        sb.append("    handlerUrl: ").append(this.toIndentedString(this.handlerUrl)).append("\n");
        sb.append("    flowSources: ").append(this.toIndentedString(this.flowSources)).append("\n");
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
