// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "Attribute metadata information.")
public class AttributesMetadataItem
{
    @SerializedName("attributeUID")
    private String attributeUID;
    @SerializedName("attributeId")
    private String attributeId;
    @SerializedName("domain")
    private String domain;
    @SerializedName("monitoredPointType")
    private String monitoredPointType;
    @SerializedName("attributeName")
    private String attributeName;
    @SerializedName("description")
    private String description;
    @SerializedName("unit")
    private String unit;
    @SerializedName("type")
    private String type;
    @SerializedName("format")
    private String format;
    @SerializedName("restName")
    private String restName;
    @SerializedName("isSensitive")
    private Boolean isSensitive;
    @SerializedName("isStandard")
    private Boolean isStandard;
    @SerializedName("toBeExported")
    private Boolean toBeExported;
    @SerializedName("physicalTarget")
    private String physicalTarget;
    @SerializedName("version")
    private String version;
    
    public AttributesMetadataItem() {
        this.attributeUID = null;
        this.attributeId = null;
        this.domain = null;
        this.monitoredPointType = null;
        this.attributeName = null;
        this.description = null;
        this.unit = null;
        this.type = null;
        this.format = null;
        this.restName = null;
        this.isSensitive = null;
        this.isStandard = null;
        this.toBeExported = null;
        this.physicalTarget = null;
        this.version = null;
    }
    
    public AttributesMetadataItem attributeUID(final String attributeUID) {
        this.attributeUID = attributeUID;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Unique identifier representing an attribute.")
    public String getAttributeUID() {
        return this.attributeUID;
    }
    
    public void setAttributeUID(final String attributeUID) {
        this.attributeUID = attributeUID;
    }
    
    public AttributesMetadataItem attributeId(final String attributeId) {
        this.attributeId = attributeId;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "identifier representing an attribute.")
    public String getAttributeId() {
        return this.attributeId;
    }
    
    public void setAttributeId(final String attributeId) {
        this.attributeId = attributeId;
    }
    
    public AttributesMetadataItem domain(final String domain) {
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
    
    public AttributesMetadataItem monitoredPointType(final String monitoredPointType) {
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
    
    public AttributesMetadataItem attributeName(final String attributeName) {
        this.attributeName = attributeName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "attribute name.")
    public String getAttributeName() {
        return this.attributeName;
    }
    
    public void setAttributeName(final String attributeName) {
        this.attributeName = attributeName;
    }
    
    public AttributesMetadataItem description(final String description) {
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
    
    public AttributesMetadataItem unit(final String unit) {
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
    
    public AttributesMetadataItem type(final String type) {
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
    
    public AttributesMetadataItem format(final String format) {
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
    
    public AttributesMetadataItem restName(final String restName) {
        this.restName = restName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "ID present in the attribute NBI.")
    public String getRestName() {
        return this.restName;
    }
    
    public void setRestName(final String restName) {
        this.restName = restName;
    }
    
    public AttributesMetadataItem isSensitive(final Boolean isSensitive) {
        this.isSensitive = isSensitive;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "flag to define if can be visible to unauthorized users.")
    public Boolean getIsSensitive() {
        return this.isSensitive;
    }
    
    public void setIsSensitive(final Boolean isSensitive) {
        this.isSensitive = isSensitive;
    }
    
    public AttributesMetadataItem isStandard(final Boolean isStandard) {
        this.isStandard = isStandard;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "flag to define if its origin is product or if this is a custom attribute")
    public Boolean getIsStandard() {
        return this.isStandard;
    }
    
    public void setIsStandard(final Boolean isStandard) {
        this.isStandard = isStandard;
    }
    
    public AttributesMetadataItem toBeExported(final Boolean toBeExported) {
        this.toBeExported = toBeExported;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "flag to define if this is to be exported")
    public Boolean getToBeExported() {
        return this.toBeExported;
    }
    
    public void setToBeExported(final Boolean toBeExported) {
        this.toBeExported = toBeExported;
    }
    
    public AttributesMetadataItem physicalTarget(final String physicalTarget) {
        this.physicalTarget = physicalTarget;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "physical storage of attribute.")
    public String getPhysicalTarget() {
        return this.physicalTarget;
    }
    
    public void setPhysicalTarget(final String physicalTarget) {
        this.physicalTarget = physicalTarget;
    }
    
    public AttributesMetadataItem version(final String version) {
        this.version = version;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "attributes' version.")
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
        final AttributesMetadataItem attributesMetadataItem = (AttributesMetadataItem)o;
        return Objects.equals(this.attributeUID, attributesMetadataItem.attributeUID) && Objects.equals(this.attributeId, attributesMetadataItem.attributeId) && Objects.equals(this.domain, attributesMetadataItem.domain) && Objects.equals(this.monitoredPointType, attributesMetadataItem.monitoredPointType) && Objects.equals(this.attributeName, attributesMetadataItem.attributeName) && Objects.equals(this.description, attributesMetadataItem.description) && Objects.equals(this.unit, attributesMetadataItem.unit) && Objects.equals(this.type, attributesMetadataItem.type) && Objects.equals(this.format, attributesMetadataItem.format) && Objects.equals(this.restName, attributesMetadataItem.restName) && Objects.equals(this.isSensitive, attributesMetadataItem.isSensitive) && Objects.equals(this.isStandard, attributesMetadataItem.isStandard) && Objects.equals(this.toBeExported, attributesMetadataItem.toBeExported) && Objects.equals(this.physicalTarget, attributesMetadataItem.physicalTarget) && Objects.equals(this.version, attributesMetadataItem.version);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.attributeUID, this.attributeId, this.domain, this.monitoredPointType, this.attributeName, this.description, this.unit, this.type, this.format, this.restName, this.isSensitive, this.isStandard, this.toBeExported, this.physicalTarget, this.version);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class AttributesMetadataItem {\n");
        sb.append("    attributeUID: ").append(this.toIndentedString(this.attributeUID)).append("\n");
        sb.append("    attributeId: ").append(this.toIndentedString(this.attributeId)).append("\n");
        sb.append("    domain: ").append(this.toIndentedString(this.domain)).append("\n");
        sb.append("    monitoredPointType: ").append(this.toIndentedString(this.monitoredPointType)).append("\n");
        sb.append("    attributeName: ").append(this.toIndentedString(this.attributeName)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    unit: ").append(this.toIndentedString(this.unit)).append("\n");
        sb.append("    type: ").append(this.toIndentedString(this.type)).append("\n");
        sb.append("    format: ").append(this.toIndentedString(this.format)).append("\n");
        sb.append("    restName: ").append(this.toIndentedString(this.restName)).append("\n");
        sb.append("    isSensitive: ").append(this.toIndentedString(this.isSensitive)).append("\n");
        sb.append("    isStandard: ").append(this.toIndentedString(this.isStandard)).append("\n");
        sb.append("    toBeExported: ").append(this.toIndentedString(this.toBeExported)).append("\n");
        sb.append("    physicalTarget: ").append(this.toIndentedString(this.physicalTarget)).append("\n");
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
