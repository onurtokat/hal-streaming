// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class MonitoredPointTypesFormulas
{
    @SerializedName("formulaUID")
    private Long formulaUID;
    @SerializedName("monitoredPointType")
    private String monitoredPointType;
    @SerializedName("monitoredPointTypeName")
    private String monitoredPointTypeName;
    @SerializedName("isSupported")
    private Boolean isSupported;
    @SerializedName("hasFormula")
    private Boolean hasFormula;
    @SerializedName("expression")
    private String expression;
    @SerializedName("filter")
    private String filter;
    @SerializedName("isSensitive")
    private Boolean isSensitive;
    @SerializedName("isEditable")
    private Boolean isEditable;
    @SerializedName("lastUpdateTS")
    private Long lastUpdateTS;
    @SerializedName("version")
    private String version;
    
    public MonitoredPointTypesFormulas() {
        this.formulaUID = null;
        this.monitoredPointType = null;
        this.monitoredPointTypeName = null;
        this.isSupported = null;
        this.hasFormula = null;
        this.expression = null;
        this.filter = null;
        this.isSensitive = null;
        this.isEditable = null;
        this.lastUpdateTS = null;
        this.version = null;
    }
    
    public MonitoredPointTypesFormulas formulaUID(final Long formulaUID) {
        this.formulaUID = formulaUID;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "unique formula id.")
    public Long getFormulaUID() {
        return this.formulaUID;
    }
    
    public void setFormulaUID(final Long formulaUID) {
        this.formulaUID = formulaUID;
    }
    
    public MonitoredPointTypesFormulas monitoredPointType(final String monitoredPointType) {
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
    
    public MonitoredPointTypesFormulas monitoredPointTypeName(final String monitoredPointTypeName) {
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
    
    public MonitoredPointTypesFormulas isSupported(final Boolean isSupported) {
        this.isSupported = isSupported;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "isSupported flag (true if this is not supported).")
    public Boolean getIsSupported() {
        return this.isSupported;
    }
    
    public void setIsSupported(final Boolean isSupported) {
        this.isSupported = isSupported;
    }
    
    public MonitoredPointTypesFormulas hasFormula(final Boolean hasFormula) {
        this.hasFormula = hasFormula;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "hasFormula flag (true if there isa formula defined for it).")
    public Boolean getHasFormula() {
        return this.hasFormula;
    }
    
    public void setHasFormula(final Boolean hasFormula) {
        this.hasFormula = hasFormula;
    }
    
    public MonitoredPointTypesFormulas expression(final String expression) {
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
    
    public MonitoredPointTypesFormulas filter(final String filter) {
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
    
    public MonitoredPointTypesFormulas isSensitive(final Boolean isSensitive) {
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
    
    public MonitoredPointTypesFormulas isEditable(final Boolean isEditable) {
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
    
    public MonitoredPointTypesFormulas lastUpdateTS(final Long lastUpdateTS) {
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
    
    public MonitoredPointTypesFormulas version(final String version) {
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
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final MonitoredPointTypesFormulas monitoredPointTypesFormulas = (MonitoredPointTypesFormulas)o;
        return Objects.equals(this.formulaUID, monitoredPointTypesFormulas.formulaUID) && Objects.equals(this.monitoredPointType, monitoredPointTypesFormulas.monitoredPointType) && Objects.equals(this.monitoredPointTypeName, monitoredPointTypesFormulas.monitoredPointTypeName) && Objects.equals(this.isSupported, monitoredPointTypesFormulas.isSupported) && Objects.equals(this.hasFormula, monitoredPointTypesFormulas.hasFormula) && Objects.equals(this.expression, monitoredPointTypesFormulas.expression) && Objects.equals(this.filter, monitoredPointTypesFormulas.filter) && Objects.equals(this.isSensitive, monitoredPointTypesFormulas.isSensitive) && Objects.equals(this.isEditable, monitoredPointTypesFormulas.isEditable) && Objects.equals(this.lastUpdateTS, monitoredPointTypesFormulas.lastUpdateTS) && Objects.equals(this.version, monitoredPointTypesFormulas.version);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.formulaUID, this.monitoredPointType, this.monitoredPointTypeName, this.isSupported, this.hasFormula, this.expression, this.filter, this.isSensitive, this.isEditable, this.lastUpdateTS, this.version);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class MonitoredPointTypesFormulas {\n");
        sb.append("    formulaUID: ").append(this.toIndentedString(this.formulaUID)).append("\n");
        sb.append("    monitoredPointType: ").append(this.toIndentedString(this.monitoredPointType)).append("\n");
        sb.append("    monitoredPointTypeName: ").append(this.toIndentedString(this.monitoredPointTypeName)).append("\n");
        sb.append("    isSupported: ").append(this.toIndentedString(this.isSupported)).append("\n");
        sb.append("    hasFormula: ").append(this.toIndentedString(this.hasFormula)).append("\n");
        sb.append("    expression: ").append(this.toIndentedString(this.expression)).append("\n");
        sb.append("    filter: ").append(this.toIndentedString(this.filter)).append("\n");
        sb.append("    isSensitive: ").append(this.toIndentedString(this.isSensitive)).append("\n");
        sb.append("    isEditable: ").append(this.toIndentedString(this.isEditable)).append("\n");
        sb.append("    lastUpdateTS: ").append(this.toIndentedString(this.lastUpdateTS)).append("\n");
        sb.append("    version: ").append(this.toIndentedString(this.version)).append("\n");
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
