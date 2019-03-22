// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.util;

import java.util.ArrayList;
import com.alu.motive.ae.client.exception.AEIssue;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "aeIssues")
public class AEIssueResourceDTO
{
    private List<AEIssue> issues;
    
    public AEIssueResourceDTO() {
        this.issues = new ArrayList<AEIssue>();
    }
    
    public List<AEIssue> getIssues() {
        return this.issues;
    }
    
    public void setIssues(final List<AEIssue> issues) {
        this.issues = issues;
    }
}
