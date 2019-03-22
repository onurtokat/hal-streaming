// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.exception;

import java.util.Arrays;
import java.util.List;

public class AEException extends RuntimeException
{
    private final List<AEIssue> issues;
    
    public AEException(final List<AEIssue> issues) {
        this.issues = issues;
    }
    
    public AEException(final AEIssue issue) {
        this(Arrays.asList(issue));
    }
    
    public AEException(final String issueMessage, final AEIssueType issueType) {
        this(new AEIssue(issueMessage, issueType));
    }
    
    public List<AEIssue> getIssues() {
        return this.issues;
    }
    
    @Override
    public String getMessage() {
        return String.valueOf(this.issues);
    }
}
