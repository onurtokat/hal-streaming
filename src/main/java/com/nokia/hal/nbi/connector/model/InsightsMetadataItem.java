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

@ApiModel(description = "Insights metadata information.")
public class InsightsMetadataItem
{
    @SerializedName("insightUID")
    private Long insightUID;
    @SerializedName("insightId")
    private String insightId;
    @SerializedName("domain")
    private String domain;
    @SerializedName("domainName")
    private String domainName;
    @SerializedName("insightName")
    private String insightName;
    @SerializedName("description")
    private String description;
    @SerializedName("category")
    private String category;
    @SerializedName("unit")
    private String unit;
    @SerializedName("type")
    private String type;
    @SerializedName("format")
    private String format;
    @SerializedName("restName")
    private String restName;
    @SerializedName("isDerived")
    private Boolean isDerived;
    @SerializedName("toBeExported")
    private Boolean toBeExported;
    @SerializedName("isSensitive")
    private Boolean isSensitive;
    @SerializedName("isStandard")
    private Boolean isStandard;
    @SerializedName("state")
    private String state;
    @SerializedName("physicalTarget")
    private String physicalTarget;
    @SerializedName("numberOfDefinedFlows")
    private Integer numberOfDefinedFlows;
    @SerializedName("numberOfDefinedFormulas")
    private Integer numberOfDefinedFormulas;
    @SerializedName("version")
    private String version;
    @SerializedName("formulas")
    private List<InsightsFormula> formulas;
    
    public InsightsMetadataItem() {
        this.insightUID = null;
        this.insightId = null;
        this.domain = null;
        this.domainName = null;
        this.insightName = null;
        this.description = null;
        this.category = null;
        this.unit = null;
        this.type = null;
        this.format = null;
        this.restName = null;
        this.isDerived = null;
        this.toBeExported = null;
        this.isSensitive = null;
        this.isStandard = null;
        this.state = null;
        this.physicalTarget = null;
        this.numberOfDefinedFlows = null;
        this.numberOfDefinedFormulas = null;
        this.version = null;
        this.formulas = new ArrayList<InsightsFormula>();
    }
    
    public InsightsMetadataItem insightUID(final Long insightUID) {
        this.insightUID = insightUID;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Unique identifier representing an insight.")
    public Long getInsightUID() {
        return this.insightUID;
    }
    
    public void setInsightUID(final Long insightUID) {
        this.insightUID = insightUID;
    }
    
    public InsightsMetadataItem insightId(final String insightId) {
        this.insightId = insightId;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "Identifier representing an insight.")
    public String getInsightId() {
        return this.insightId;
    }
    
    public void setInsightId(final String insightId) {
        this.insightId = insightId;
    }
    
    public InsightsMetadataItem domain(final String domain) {
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
    
    public InsightsMetadataItem domainName(final String domainName) {
        this.domainName = domainName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Domain name.")
    public String getDomainName() {
        return this.domainName;
    }
    
    public void setDomainName(final String domainName) {
        this.domainName = domainName;
    }
    
    public InsightsMetadataItem insightName(final String insightName) {
        this.insightName = insightName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Insight name.")
    public String getInsightName() {
        return this.insightName;
    }
    
    public void setInsightName(final String insightName) {
        this.insightName = insightName;
    }
    
    public InsightsMetadataItem description(final String description) {
        this.description = description;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Long description of insight.")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public InsightsMetadataItem category(final String category) {
        this.category = category;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "category (Counter,KPI,KQI,Observation).")
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(final String category) {
        this.category = category;
    }
    
    public InsightsMetadataItem unit(final String unit) {
        this.unit = unit;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "unit.")
    public String getUnit() {
        return this.unit;
    }
    
    public void setUnit(final String unit) {
        this.unit = unit;
    }
    
    public InsightsMetadataItem type(final String type) {
        this.type = type;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "type.")
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public InsightsMetadataItem format(final String format) {
        this.format = format;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "format.")
    public String getFormat() {
        return this.format;
    }
    
    public void setFormat(final String format) {
        this.format = format;
    }
    
    public InsightsMetadataItem restName(final String restName) {
        this.restName = restName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "ID present in the measurements NBI.")
    public String getRestName() {
        return this.restName;
    }
    
    public void setRestName(final String restName) {
        this.restName = restName;
    }
    
    public InsightsMetadataItem isDerived(final Boolean isDerived) {
        this.isDerived = isDerived;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "derived flag.")
    public Boolean getIsDerived() {
        return this.isDerived;
    }
    
    public void setIsDerived(final Boolean isDerived) {
        this.isDerived = isDerived;
    }
    
    public InsightsMetadataItem toBeExported(final Boolean toBeExported) {
        this.toBeExported = toBeExported;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "flag specifying if is to be exported.")
    public Boolean getToBeExported() {
        return this.toBeExported;
    }
    
    public void setToBeExported(final Boolean toBeExported) {
        this.toBeExported = toBeExported;
    }
    
    public InsightsMetadataItem isSensitive(final Boolean isSensitive) {
        this.isSensitive = isSensitive;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "isSensitive flag (true if should not be visible by unauthorized users).")
    public Boolean getIsSensitive() {
        return this.isSensitive;
    }
    
    public void setIsSensitive(final Boolean isSensitive) {
        this.isSensitive = isSensitive;
    }
    
    public InsightsMetadataItem isStandard(final Boolean isStandard) {
        this.isStandard = isStandard;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "isStandard flag (true if provided from product).")
    public Boolean getIsStandard() {
        return this.isStandard;
    }
    
    public void setIsStandard(final Boolean isStandard) {
        this.isStandard = isStandard;
    }
    
    public InsightsMetadataItem state(final String state) {
        this.state = state;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "insights' publishing state.")
    public String getState() {
        return this.state;
    }
    
    public void setState(final String state) {
        this.state = state;
    }
    
    public InsightsMetadataItem physicalTarget(final String physicalTarget) {
        this.physicalTarget = physicalTarget;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "physical storage of insight.")
    public String getPhysicalTarget() {
        return this.physicalTarget;
    }
    
    public void setPhysicalTarget(final String physicalTarget) {
        this.physicalTarget = physicalTarget;
    }
    
    public InsightsMetadataItem numberOfDefinedFlows(final Integer numberOfDefinedFlows) {
        this.numberOfDefinedFlows = numberOfDefinedFlows;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "number of defined flows.")
    public Integer getNumberOfDefinedFlows() {
        return this.numberOfDefinedFlows;
    }
    
    public void setNumberOfDefinedFlows(final Integer numberOfDefinedFlows) {
        this.numberOfDefinedFlows = numberOfDefinedFlows;
    }
    
    public InsightsMetadataItem numberOfDefinedFormulas(final Integer numberOfDefinedFormulas) {
        this.numberOfDefinedFormulas = numberOfDefinedFormulas;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "number of defined formulas.")
    public Integer getNumberOfDefinedFormulas() {
        return this.numberOfDefinedFormulas;
    }
    
    public void setNumberOfDefinedFormulas(final Integer numberOfDefinedFormulas) {
        this.numberOfDefinedFormulas = numberOfDefinedFormulas;
    }
    
    public InsightsMetadataItem version(final String version) {
        this.version = version;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "insights' version.")
    public String getVersion() {
        return this.version;
    }
    
    public void setVersion(final String version) {
        this.version = version;
    }
    
    public InsightsMetadataItem formulas(final List<InsightsFormula> formulas) {
        this.formulas = formulas;
        return this;
    }
    
    public InsightsMetadataItem addFormulasItem(final InsightsFormula formulasItem) {
        this.formulas.add(formulasItem);
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public List<InsightsFormula> getFormulas() {
        return this.formulas;
    }
    
    public void setFormulas(final List<InsightsFormula> formulas) {
        this.formulas = formulas;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final InsightsMetadataItem insightsMetadataItem = (InsightsMetadataItem)o;
        return Objects.equals(this.insightUID, insightsMetadataItem.insightUID) && Objects.equals(this.insightId, insightsMetadataItem.insightId) && Objects.equals(this.domain, insightsMetadataItem.domain) && Objects.equals(this.domainName, insightsMetadataItem.domainName) && Objects.equals(this.insightName, insightsMetadataItem.insightName) && Objects.equals(this.description, insightsMetadataItem.description) && Objects.equals(this.category, insightsMetadataItem.category) && Objects.equals(this.unit, insightsMetadataItem.unit) && Objects.equals(this.type, insightsMetadataItem.type) && Objects.equals(this.format, insightsMetadataItem.format) && Objects.equals(this.restName, insightsMetadataItem.restName) && Objects.equals(this.isDerived, insightsMetadataItem.isDerived) && Objects.equals(this.toBeExported, insightsMetadataItem.toBeExported) && Objects.equals(this.isSensitive, insightsMetadataItem.isSensitive) && Objects.equals(this.isStandard, insightsMetadataItem.isStandard) && Objects.equals(this.state, insightsMetadataItem.state) && Objects.equals(this.physicalTarget, insightsMetadataItem.physicalTarget) && Objects.equals(this.numberOfDefinedFlows, insightsMetadataItem.numberOfDefinedFlows) && Objects.equals(this.numberOfDefinedFormulas, insightsMetadataItem.numberOfDefinedFormulas) && Objects.equals(this.version, insightsMetadataItem.version) && Objects.equals(this.formulas, insightsMetadataItem.formulas);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.insightUID, this.insightId, this.domain, this.domainName, this.insightName, this.description, this.category, this.unit, this.type, this.format, this.restName, this.isDerived, this.toBeExported, this.isSensitive, this.isStandard, this.state, this.physicalTarget, this.numberOfDefinedFlows, this.numberOfDefinedFormulas, this.version, this.formulas);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class InsightsMetadataItem {\n");
        sb.append("    insightUID: ").append(this.toIndentedString(this.insightUID)).append("\n");
        sb.append("    insightId: ").append(this.toIndentedString(this.insightId)).append("\n");
        sb.append("    domain: ").append(this.toIndentedString(this.domain)).append("\n");
        sb.append("    domainName: ").append(this.toIndentedString(this.domainName)).append("\n");
        sb.append("    insightName: ").append(this.toIndentedString(this.insightName)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    category: ").append(this.toIndentedString(this.category)).append("\n");
        sb.append("    unit: ").append(this.toIndentedString(this.unit)).append("\n");
        sb.append("    type: ").append(this.toIndentedString(this.type)).append("\n");
        sb.append("    format: ").append(this.toIndentedString(this.format)).append("\n");
        sb.append("    restName: ").append(this.toIndentedString(this.restName)).append("\n");
        sb.append("    isDerived: ").append(this.toIndentedString(this.isDerived)).append("\n");
        sb.append("    toBeExported: ").append(this.toIndentedString(this.toBeExported)).append("\n");
        sb.append("    isSensitive: ").append(this.toIndentedString(this.isSensitive)).append("\n");
        sb.append("    isStandard: ").append(this.toIndentedString(this.isStandard)).append("\n");
        sb.append("    state: ").append(this.toIndentedString(this.state)).append("\n");
        sb.append("    physicalTarget: ").append(this.toIndentedString(this.physicalTarget)).append("\n");
        sb.append("    numberOfDefinedFlows: ").append(this.toIndentedString(this.numberOfDefinedFlows)).append("\n");
        sb.append("    numberOfDefinedFormulas: ").append(this.toIndentedString(this.numberOfDefinedFormulas)).append("\n");
        sb.append("    version: ").append(this.toIndentedString(this.version)).append("\n");
        sb.append("    formulas: ").append(this.toIndentedString(this.formulas)).append("\n");
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
