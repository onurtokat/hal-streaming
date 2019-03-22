// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import com.alu.motive.ae.client.exception.AEException;
import com.alu.motive.ae.client.exception.AEIssueType;

public class InsightAdditionalInfo
{
    private String alias;
    private InsightType type;
    private InsightCategory category;
    private String domain;
    
    public InsightAdditionalInfo() {
        this.alias = "";
        this.type = InsightType.UNKNOWN;
        this.category = InsightCategory.UNKNOWN;
        this.domain = "";
    }
    
    InsightAdditionalInfo(final String alias, final InsightType type, final InsightCategory category, final String domain) {
        if (alias == null || alias.trim().isEmpty()) {
            throw new AEException("Trying to create InsightAdditionalInfo with an empty or null alias", AEIssueType.ERROR);
        }
        this.alias = alias;
        this.type = type;
        this.category = category;
        if (domain != null) {
            this.domain = domain;
        }
        else {
            this.domain = "";
        }
    }
    
    public String getAlias() {
        return this.alias;
    }
    
    public void setAlias(final String insightAlias) {
        this.alias = insightAlias;
    }
    
    public InsightType getType() {
        return this.type;
    }
    
    public void setType(final InsightType insightType) {
        this.type = insightType;
    }
    
    public InsightCategory getCategory() {
        return this.category;
    }
    
    public void setCategory(final InsightCategory insightCategory) {
        this.category = insightCategory;
    }
    
    public String getDomain() {
        return this.domain;
    }
    
    public void setDomain(final String insightDomain) {
        this.domain = insightDomain;
    }
}
