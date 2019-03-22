// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation;

import com.alu.motive.hal.commons.dto.ParameterDTO;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AugmentingParameter implements Comparable<AugmentingParameter>
{
    private String name;
    private Object value;
    private AugmentingParameterType type;
    private String newParamName;
    private int position;
    
    public AugmentingParameter(final String name, final AugmentingParameterType type, final Object value, final int position) {
        this.name = name;
        this.type = type;
        this.value = ((type == null || type.equals(AugmentingParameterType.STRING)) ? String.valueOf(value) : value);
        this.position = position;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public AugmentingParameterType getType() {
        return this.type;
    }
    
    public int getPosition() {
        return this.position;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(AugmentingParameter.class.getSimpleName());
        sb.append("\n\t\t position = " + this.getPosition());
        sb.append("\n\t\t name = " + this.getName());
        sb.append("\n\t\t value = " + this.getValue());
        sb.append("\n\t\t type = " + this.getType());
        sb.append("\n\t\t newParamName = " + this.getNewParamName());
        sb.append("\n");
        return sb.toString();
    }
    
    @Override
    public int compareTo(final AugmentingParameter o) {
        if (o == null || o.getPosition() < this.getPosition()) {
            return 1;
        }
        if (this.getPosition() == o.getPosition()) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        final AugmentingParameter o = (AugmentingParameter)obj;
        return this.getName().equals(o.getName()) && this.getPosition() == o.getPosition() && this.getType().equals(o.getType()) && this.getValue().equals(o.getValue());
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(this.name).append(this.value).append(this.type).append(this.position).toHashCode();
    }
    
    public ParameterDTO toParameterDTO() {
        final String stringValue = (String)((this.value != null && this.value instanceof String) ? this.value : this.value.toString());
        return new ParameterDTO(this.getName(), stringValue);
    }
    
    public void setNewParamName(final String newParamName) {
        this.newParamName = newParamName;
    }
    
    public String getNewParamName() {
        return (this.newParamName == null || this.newParamName.trim().isEmpty()) ? this.getName() : this.newParamName;
    }
}
