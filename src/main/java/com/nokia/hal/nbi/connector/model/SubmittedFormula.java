// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "submittedFormula information.")
public class SubmittedFormula
{
    @SerializedName("insightUID")
    private String insightUID;
    @SerializedName("insightId")
    private String insightId;
    @SerializedName("domain")
    private String domain;
    @SerializedName("insightName")
    private String insightName;
    @SerializedName("attributeUID")
    private String attributeUID;
    @SerializedName("attributeName")
    private String attributeName;
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
    @SerializedName("flow")
    private String flow;
    @SerializedName("filter")
    private String filter;
    @SerializedName("monitoredPointType")
    private String monitoredPointType;
    @SerializedName("expression")
    private String expression;
    @SerializedName("translatedExpression")
    private String translatedExpression;
    @SerializedName("physicalTarget")
    private String physicalTarget;
    @SerializedName("isDerived")
    private Boolean isDerived;
    @SerializedName("toBeExported")
    private Boolean toBeExported;
    @SerializedName("isSensitive")
    private Boolean isSensitive;
    @SerializedName("isStandard")
    private Boolean isStandard;
    @SerializedName("isEditable")
    private Boolean isEditable;
    @SerializedName("colOrder")
    private Integer colOrder;
    @SerializedName("lastUpdateTS")
    private Long lastUpdateTS;
    @SerializedName("submissionTS")
    private Long submissionTS;
    
    public SubmittedFormula() {
        this.insightUID = null;
        this.insightId = null;
        this.domain = null;
        this.insightName = null;
        this.attributeUID = null;
        this.attributeName = null;
        this.description = null;
        this.category = null;
        this.unit = null;
        this.type = null;
        this.format = null;
        this.restName = null;
        this.flow = null;
        this.filter = null;
        this.monitoredPointType = null;
        this.expression = null;
        this.translatedExpression = null;
        this.physicalTarget = null;
        this.isDerived = null;
        this.toBeExported = null;
        this.isSensitive = null;
        this.isStandard = null;
        this.isEditable = null;
        this.colOrder = null;
        this.lastUpdateTS = null;
        this.submissionTS = null;
    }
    
    public SubmittedFormula insightUID(final String insightUID) {
        this.insightUID = insightUID;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Unique identifier representing an insight.")
    public String getInsightUID() {
        return this.insightUID;
    }
    
    public void setInsightUID(final String insightUID) {
        this.insightUID = insightUID;
    }
    
    public SubmittedFormula insightId(final String insightId) {
        this.insightId = insightId;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Identifier representing an insight.")
    public String getInsightId() {
        return this.insightId;
    }
    
    public void setInsightId(final String insightId) {
        this.insightId = insightId;
    }
    
    public SubmittedFormula domain(final String domain) {
        this.domain = domain;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Unique identifier representing a domain.")
    public String getDomain() {
        return this.domain;
    }
    
    public void setDomain(final String domain) {
        this.domain = domain;
    }
    
    public SubmittedFormula insightName(final String insightName) {
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
    
    public SubmittedFormula attributeUID(final String attributeUID) {
        this.attributeUID = attributeUID;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "unique identified representing an attribute (for attributes formula).")
    public String getAttributeUID() {
        return this.attributeUID;
    }
    
    public void setAttributeUID(final String attributeUID) {
        this.attributeUID = attributeUID;
    }
    
    public SubmittedFormula attributeName(final String attributeName) {
        this.attributeName = attributeName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "attributeName.")
    public String getAttributeName() {
        return this.attributeName;
    }
    
    public void setAttributeName(final String attributeName) {
        this.attributeName = attributeName;
    }
    
    public SubmittedFormula description(final String description) {
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
    
    public SubmittedFormula category(final String category) {
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
    
    public SubmittedFormula unit(final String unit) {
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
    
    public SubmittedFormula type(final String type) {
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
    
    public SubmittedFormula format(final String format) {
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
    
    public SubmittedFormula restName(final String restName) {
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
    
    public SubmittedFormula flow(final String flow) {
        this.flow = flow;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public String getFlow() {
        return this.flow;
    }
    
    public void setFlow(final String flow) {
        this.flow = flow;
    }
    
    public SubmittedFormula filter(final String filter) {
        this.filter = filter;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public String getFilter() {
        return this.filter;
    }
    
    public void setFilter(final String filter) {
        this.filter = filter;
    }
    
    public SubmittedFormula monitoredPointType(final String monitoredPointType) {
        this.monitoredPointType = monitoredPointType;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public String getMonitoredPointType() {
        return this.monitoredPointType;
    }
    
    public void setMonitoredPointType(final String monitoredPointType) {
        this.monitoredPointType = monitoredPointType;
    }
    
    public SubmittedFormula expression(final String expression) {
        this.expression = expression;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public String getExpression() {
        return this.expression;
    }
    
    public void setExpression(final String expression) {
        this.expression = expression;
    }
    
    public SubmittedFormula translatedExpression(final String translatedExpression) {
        this.translatedExpression = translatedExpression;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public String getTranslatedExpression() {
        return this.translatedExpression;
    }
    
    public void setTranslatedExpression(final String translatedExpression) {
        this.translatedExpression = translatedExpression;
    }
    
    public SubmittedFormula physicalTarget(final String physicalTarget) {
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
    
    public SubmittedFormula isDerived(final Boolean isDerived) {
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
    
    public SubmittedFormula toBeExported(final Boolean toBeExported) {
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
    
    public SubmittedFormula isSensitive(final Boolean isSensitive) {
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
    
    public SubmittedFormula isStandard(final Boolean isStandard) {
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
    
    public SubmittedFormula isEditable(final Boolean isEditable) {
        this.isEditable = isEditable;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "isEditable flag (true if should not be editable).")
    public Boolean getIsEditable() {
        return this.isEditable;
    }
    
    public void setIsEditable(final Boolean isEditable) {
        this.isEditable = isEditable;
    }
    
    public SubmittedFormula colOrder(final Integer colOrder) {
        this.colOrder = colOrder;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "relative position on target schema")
    public Integer getColOrder() {
        return this.colOrder;
    }
    
    public void setColOrder(final Integer colOrder) {
        this.colOrder = colOrder;
    }
    
    public SubmittedFormula lastUpdateTS(final Long lastUpdateTS) {
        this.lastUpdateTS = lastUpdateTS;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "last time formula has been changed (via editor or import).")
    public Long getLastUpdateTS() {
        return this.lastUpdateTS;
    }
    
    public void setLastUpdateTS(final Long lastUpdateTS) {
        this.lastUpdateTS = lastUpdateTS;
    }
    
    public SubmittedFormula submissionTS(final Long submissionTS) {
        this.submissionTS = submissionTS;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "last time this metadata was submitted ()submit button).")
    public Long getSubmissionTS() {
        return this.submissionTS;
    }
    
    public void setSubmissionTS(final Long submissionTS) {
        this.submissionTS = submissionTS;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SubmittedFormula submittedFormula = (SubmittedFormula)o;
        return Objects.equals(this.insightUID, submittedFormula.insightUID) && Objects.equals(this.insightId, submittedFormula.insightId) && Objects.equals(this.domain, submittedFormula.domain) && Objects.equals(this.insightName, submittedFormula.insightName) && Objects.equals(this.attributeUID, submittedFormula.attributeUID) && Objects.equals(this.attributeName, submittedFormula.attributeName) && Objects.equals(this.description, submittedFormula.description) && Objects.equals(this.category, submittedFormula.category) && Objects.equals(this.unit, submittedFormula.unit) && Objects.equals(this.type, submittedFormula.type) && Objects.equals(this.format, submittedFormula.format) && Objects.equals(this.restName, submittedFormula.restName) && Objects.equals(this.flow, submittedFormula.flow) && Objects.equals(this.filter, submittedFormula.filter) && Objects.equals(this.monitoredPointType, submittedFormula.monitoredPointType) && Objects.equals(this.expression, submittedFormula.expression) && Objects.equals(this.translatedExpression, submittedFormula.translatedExpression) && Objects.equals(this.physicalTarget, submittedFormula.physicalTarget) && Objects.equals(this.isDerived, submittedFormula.isDerived) && Objects.equals(this.toBeExported, submittedFormula.toBeExported) && Objects.equals(this.isSensitive, submittedFormula.isSensitive) && Objects.equals(this.isStandard, submittedFormula.isStandard) && Objects.equals(this.isEditable, submittedFormula.isEditable) && Objects.equals(this.colOrder, submittedFormula.colOrder) && Objects.equals(this.lastUpdateTS, submittedFormula.lastUpdateTS) && Objects.equals(this.submissionTS, submittedFormula.submissionTS);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.insightUID, this.insightId, this.domain, this.insightName, this.attributeUID, this.attributeName, this.description, this.category, this.unit, this.type, this.format, this.restName, this.flow, this.filter, this.monitoredPointType, this.expression, this.translatedExpression, this.physicalTarget, this.isDerived, this.toBeExported, this.isSensitive, this.isStandard, this.isEditable, this.colOrder, this.lastUpdateTS, this.submissionTS);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class SubmittedFormula {\n");
        sb.append("    insightUID: ").append(this.toIndentedString(this.insightUID)).append("\n");
        sb.append("    insightId: ").append(this.toIndentedString(this.insightId)).append("\n");
        sb.append("    domain: ").append(this.toIndentedString(this.domain)).append("\n");
        sb.append("    insightName: ").append(this.toIndentedString(this.insightName)).append("\n");
        sb.append("    attributeUID: ").append(this.toIndentedString(this.attributeUID)).append("\n");
        sb.append("    attributeName: ").append(this.toIndentedString(this.attributeName)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    category: ").append(this.toIndentedString(this.category)).append("\n");
        sb.append("    unit: ").append(this.toIndentedString(this.unit)).append("\n");
        sb.append("    type: ").append(this.toIndentedString(this.type)).append("\n");
        sb.append("    format: ").append(this.toIndentedString(this.format)).append("\n");
        sb.append("    restName: ").append(this.toIndentedString(this.restName)).append("\n");
        sb.append("    flow: ").append(this.toIndentedString(this.flow)).append("\n");
        sb.append("    filter: ").append(this.toIndentedString(this.filter)).append("\n");
        sb.append("    monitoredPointType: ").append(this.toIndentedString(this.monitoredPointType)).append("\n");
        sb.append("    expression: ").append(this.toIndentedString(this.expression)).append("\n");
        sb.append("    translatedExpression: ").append(this.toIndentedString(this.translatedExpression)).append("\n");
        sb.append("    physicalTarget: ").append(this.toIndentedString(this.physicalTarget)).append("\n");
        sb.append("    isDerived: ").append(this.toIndentedString(this.isDerived)).append("\n");
        sb.append("    toBeExported: ").append(this.toIndentedString(this.toBeExported)).append("\n");
        sb.append("    isSensitive: ").append(this.toIndentedString(this.isSensitive)).append("\n");
        sb.append("    isStandard: ").append(this.toIndentedString(this.isStandard)).append("\n");
        sb.append("    isEditable: ").append(this.toIndentedString(this.isEditable)).append("\n");
        sb.append("    colOrder: ").append(this.toIndentedString(this.colOrder)).append("\n");
        sb.append("    lastUpdateTS: ").append(this.toIndentedString(this.lastUpdateTS)).append("\n");
        sb.append("    submissionTS: ").append(this.toIndentedString(this.submissionTS)).append("\n");
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
