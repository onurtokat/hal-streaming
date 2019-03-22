// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.exception;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "aeIssue")
public class AEIssue
{
    private String message;
    private AEIssueType type;
    
    public AEIssue() {
    }
    
    public AEIssue(final String message, final AEIssueType type) {
        this.message = message;
        this.type = type;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public AEIssueType getType() {
        return this.type;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public void setType(final AEIssueType type) {
        this.type = type;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.message == null) ? 0 : this.message.hashCode());
        result = 31 * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return this == obj || (obj != null && this.getClass() == obj.getClass() && this.areFieldsEqualWithTheCorrespondingOnesInOtherIssue((AEIssue)obj));
    }
    
    private boolean areFieldsEqualWithTheCorrespondingOnesInOtherIssue(final AEIssue other) {
        if (this.message == null) {
            if (other.message != null) {
                return false;
            }
        }
        else if (!this.message.equals(other.message)) {
            return false;
        }
        return this.type == other.type;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.type).append(": ");
        sb.append(this.message);
        return sb.toString();
    }
}
