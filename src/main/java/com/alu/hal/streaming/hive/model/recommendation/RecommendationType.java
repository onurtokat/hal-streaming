// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model.recommendation;

import org.apache.spark.api.java.Optional;
import java.util.HashMap;
import java.io.Serializable;

public class RecommendationType implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static HashMap<String, RecommendationType> allRecommendations;
    private final String name;
    private final String positiveQuery;
    private final String negativeQuery;
    private final int noPositiveObsBeforeActivation;
    private final int noNegativeObsBeforeDeActivation;
    private final long timeout;
    
    public RecommendationType(final String name, final String positiveQuery, final String negativeQuery, final int noPositiveObsBeforeActivation, final int noNegativeeObsBeforeDeActivation, final long timeout) {
        this.name = name;
        this.positiveQuery = positiveQuery;
        this.negativeQuery = negativeQuery;
        this.noPositiveObsBeforeActivation = noPositiveObsBeforeActivation;
        this.noNegativeObsBeforeDeActivation = noNegativeeObsBeforeDeActivation;
        this.timeout = timeout;
        RecommendationType.allRecommendations.put(name, this);
    }
    
    public static Optional<RecommendationType> getRecommendationTypeByName(final String name) {
        return (Optional<RecommendationType>)Optional.fromNullable((Object)RecommendationType.allRecommendations.get(name));
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPositiveQuery() {
        return this.positiveQuery;
    }
    
    public String getNegativeQuery() {
        return this.negativeQuery;
    }
    
    public int getNoPositiveObsBeforeActivation() {
        return this.noPositiveObsBeforeActivation;
    }
    
    public int getNoNegativeObsBeforeDeActivation() {
        return this.noNegativeObsBeforeDeActivation;
    }
    
    public long getTimeout() {
        return this.timeout;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = 31 * result + ((this.negativeQuery == null) ? 0 : this.negativeQuery.hashCode());
        result = 31 * result + this.noNegativeObsBeforeDeActivation;
        result = 31 * result + this.noPositiveObsBeforeActivation;
        result = 31 * result + ((this.positiveQuery == null) ? 0 : this.positiveQuery.hashCode());
        result = 31 * result + (int)(this.timeout ^ this.timeout >>> 32);
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final RecommendationType other = (RecommendationType)obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.negativeQuery == null) {
            if (other.negativeQuery != null) {
                return false;
            }
        }
        else if (!this.negativeQuery.equals(other.negativeQuery)) {
            return false;
        }
        if (this.noNegativeObsBeforeDeActivation != other.noNegativeObsBeforeDeActivation) {
            return false;
        }
        if (this.noPositiveObsBeforeActivation != other.noPositiveObsBeforeActivation) {
            return false;
        }
        if (this.positiveQuery == null) {
            if (other.positiveQuery != null) {
                return false;
            }
        }
        else if (!this.positiveQuery.equals(other.positiveQuery)) {
            return false;
        }
        return this.timeout == other.timeout;
    }
    
    @Override
    public String toString() {
        return "RecommendationType [name=" + this.name + ", noPositiveObsBeforeActivation=" + this.noPositiveObsBeforeActivation + ", noNegativeObsBeforeDeActivation=" + this.noNegativeObsBeforeDeActivation + ", timeout=" + this.timeout + "]";
    }
    
    static {
        RecommendationType.allRecommendations = new HashMap<String, RecommendationType>();
    }
}
