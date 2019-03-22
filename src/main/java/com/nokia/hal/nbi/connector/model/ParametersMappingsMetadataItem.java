// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "Parameter mappings metadata information.")
public class ParametersMappingsMetadataItem
{
    @SerializedName("mappedItemId")
    private String mappedItemId;
    @SerializedName("domain")
    private String domain;
    @SerializedName("mappedItemName")
    private String mappedItemName;
    @SerializedName("dataModel")
    private String dataModel;
    @SerializedName("type")
    private String type;
    @SerializedName("parameterName")
    private String parameterName;
    @SerializedName("alternativeName")
    private String alternativeName;
    @SerializedName("alternativePath")
    private String alternativePath;
    @SerializedName("conversionExpression")
    private String conversionExpression;
    @SerializedName("rebootParameter")
    private String rebootParameter;
    @SerializedName("referenceGroup")
    private String referenceGroup;
    @SerializedName("groupName")
    private String groupName;
    @SerializedName("groupPath")
    private String groupPath;
    @SerializedName("isSensitive")
    private Boolean isSensitive;
    @SerializedName("version")
    private String version;
    
    public ParametersMappingsMetadataItem() {
        this.mappedItemId = null;
        this.domain = null;
        this.mappedItemName = null;
        this.dataModel = null;
        this.type = null;
        this.parameterName = null;
        this.alternativeName = null;
        this.alternativePath = null;
        this.conversionExpression = null;
        this.rebootParameter = null;
        this.referenceGroup = null;
        this.groupName = null;
        this.groupPath = null;
        this.isSensitive = null;
        this.version = null;
    }
    
    public ParametersMappingsMetadataItem mappedItemId(final String mappedItemId) {
        this.mappedItemId = mappedItemId;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Unique identifier representing a mapped item.")
    public String getMappedItemId() {
        return this.mappedItemId;
    }
    
    public void setMappedItemId(final String mappedItemId) {
        this.mappedItemId = mappedItemId;
    }
    
    public ParametersMappingsMetadataItem domain(final String domain) {
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
    
    public ParametersMappingsMetadataItem mappedItemName(final String mappedItemName) {
        this.mappedItemName = mappedItemName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Name of mapped item.")
    public String getMappedItemName() {
        return this.mappedItemName;
    }
    
    public void setMappedItemName(final String mappedItemName) {
        this.mappedItemName = mappedItemName;
    }
    
    public ParametersMappingsMetadataItem dataModel(final String dataModel) {
        this.dataModel = dataModel;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Unique identifier representing a data model.")
    public String getDataModel() {
        return this.dataModel;
    }
    
    public void setDataModel(final String dataModel) {
        this.dataModel = dataModel;
    }
    
    public ParametersMappingsMetadataItem type(final String type) {
        this.type = type;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "Mapping type.")
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public ParametersMappingsMetadataItem parameterName(final String parameterName) {
        this.parameterName = parameterName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "original parameter name.")
    public String getParameterName() {
        return this.parameterName;
    }
    
    public void setParameterName(final String parameterName) {
        this.parameterName = parameterName;
    }
    
    public ParametersMappingsMetadataItem alternativeName(final String alternativeName) {
        this.alternativeName = alternativeName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "alternative parameter name.")
    public String getAlternativeName() {
        return this.alternativeName;
    }
    
    public void setAlternativeName(final String alternativeName) {
        this.alternativeName = alternativeName;
    }
    
    public ParametersMappingsMetadataItem alternativePath(final String alternativePath) {
        this.alternativePath = alternativePath;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "alternative parameter path.")
    public String getAlternativePath() {
        return this.alternativePath;
    }
    
    public void setAlternativePath(final String alternativePath) {
        this.alternativePath = alternativePath;
    }
    
    public ParametersMappingsMetadataItem conversionExpression(final String conversionExpression) {
        this.conversionExpression = conversionExpression;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "mapping expression")
    public String getConversionExpression() {
        return this.conversionExpression;
    }
    
    public void setConversionExpression(final String conversionExpression) {
        this.conversionExpression = conversionExpression;
    }
    
    public ParametersMappingsMetadataItem rebootParameter(final String rebootParameter) {
        this.rebootParameter = rebootParameter;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "reboot parametr Name (for delta computation)")
    public String getRebootParameter() {
        return this.rebootParameter;
    }
    
    public void setRebootParameter(final String rebootParameter) {
        this.rebootParameter = rebootParameter;
    }
    
    public ParametersMappingsMetadataItem referenceGroup(final String referenceGroup) {
        this.referenceGroup = referenceGroup;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "when paramter is refering to other group")
    public String getReferenceGroup() {
        return this.referenceGroup;
    }
    
    public void setReferenceGroup(final String referenceGroup) {
        this.referenceGroup = referenceGroup;
    }
    
    public ParametersMappingsMetadataItem groupName(final String groupName) {
        this.groupName = groupName;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "group Name")
    public String getGroupName() {
        return this.groupName;
    }
    
    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }
    
    public ParametersMappingsMetadataItem groupPath(final String groupPath) {
        this.groupPath = groupPath;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "group Path")
    public String getGroupPath() {
        return this.groupPath;
    }
    
    public void setGroupPath(final String groupPath) {
        this.groupPath = groupPath;
    }
    
    public ParametersMappingsMetadataItem isSensitive(final Boolean isSensitive) {
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
    
    public ParametersMappingsMetadataItem version(final String version) {
        this.version = version;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "mapped item version.")
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
        final ParametersMappingsMetadataItem parametersMappingsMetadataItem = (ParametersMappingsMetadataItem)o;
        return Objects.equals(this.mappedItemId, parametersMappingsMetadataItem.mappedItemId) && Objects.equals(this.domain, parametersMappingsMetadataItem.domain) && Objects.equals(this.mappedItemName, parametersMappingsMetadataItem.mappedItemName) && Objects.equals(this.dataModel, parametersMappingsMetadataItem.dataModel) && Objects.equals(this.type, parametersMappingsMetadataItem.type) && Objects.equals(this.parameterName, parametersMappingsMetadataItem.parameterName) && Objects.equals(this.alternativeName, parametersMappingsMetadataItem.alternativeName) && Objects.equals(this.alternativePath, parametersMappingsMetadataItem.alternativePath) && Objects.equals(this.conversionExpression, parametersMappingsMetadataItem.conversionExpression) && Objects.equals(this.rebootParameter, parametersMappingsMetadataItem.rebootParameter) && Objects.equals(this.referenceGroup, parametersMappingsMetadataItem.referenceGroup) && Objects.equals(this.groupName, parametersMappingsMetadataItem.groupName) && Objects.equals(this.groupPath, parametersMappingsMetadataItem.groupPath) && Objects.equals(this.isSensitive, parametersMappingsMetadataItem.isSensitive) && Objects.equals(this.version, parametersMappingsMetadataItem.version);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.mappedItemId, this.domain, this.mappedItemName, this.dataModel, this.type, this.parameterName, this.alternativeName, this.alternativePath, this.conversionExpression, this.rebootParameter, this.referenceGroup, this.groupName, this.groupPath, this.isSensitive, this.version);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class ParametersMappingsMetadataItem {\n");
        sb.append("    mappedItemId: ").append(this.toIndentedString(this.mappedItemId)).append("\n");
        sb.append("    domain: ").append(this.toIndentedString(this.domain)).append("\n");
        sb.append("    mappedItemName: ").append(this.toIndentedString(this.mappedItemName)).append("\n");
        sb.append("    dataModel: ").append(this.toIndentedString(this.dataModel)).append("\n");
        sb.append("    type: ").append(this.toIndentedString(this.type)).append("\n");
        sb.append("    parameterName: ").append(this.toIndentedString(this.parameterName)).append("\n");
        sb.append("    alternativeName: ").append(this.toIndentedString(this.alternativeName)).append("\n");
        sb.append("    alternativePath: ").append(this.toIndentedString(this.alternativePath)).append("\n");
        sb.append("    conversionExpression: ").append(this.toIndentedString(this.conversionExpression)).append("\n");
        sb.append("    rebootParameter: ").append(this.toIndentedString(this.rebootParameter)).append("\n");
        sb.append("    referenceGroup: ").append(this.toIndentedString(this.referenceGroup)).append("\n");
        sb.append("    groupName: ").append(this.toIndentedString(this.groupName)).append("\n");
        sb.append("    groupPath: ").append(this.toIndentedString(this.groupPath)).append("\n");
        sb.append("    isSensitive: ").append(this.toIndentedString(this.isSensitive)).append("\n");
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
