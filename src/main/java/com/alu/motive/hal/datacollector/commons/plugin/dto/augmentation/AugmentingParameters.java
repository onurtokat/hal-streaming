// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation;

import java.util.Iterator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

public class AugmentingParameters
{
    private LinkedList<AugmentingParameter> parameters;
    
    public LinkedList<AugmentingParameter> getParameters() {
        return this.parameters;
    }
    
    public void setParameters(final LinkedList<AugmentingParameter> parameters) {
        this.parameters = parameters;
        this.sort();
    }
    
    public void sort() {
        if (this.parameters != null) {
            Collections.sort(this.parameters);
        }
    }
    
    public void appendParameter(final AugmentingParameter param) {
        if (this.parameters == null) {
            this.parameters = new LinkedList<AugmentingParameter>();
        }
        this.parameters.add(param);
    }
    
    public int size() {
        if (this.parameters == null) {
            return 0;
        }
        return this.parameters.size();
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    public boolean contains(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Null is not an accepted augmenting parameter name.");
        }
        for (final AugmentingParameter parameter : this.parameters) {
            if (name.equals(parameter.getName())) {
                return true;
            }
        }
        return false;
    }
}
