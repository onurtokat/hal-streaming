// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client;

import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class AEWorkflowPart
{
    private List<GeneratedWorkflowElement> elements;
    private List<JobFileInfo> requiredFiles;
    private final String nextElementName;
    
    public AEWorkflowPart(final String nextElementName) {
        this(new ArrayList<GeneratedWorkflowElement>(), new ArrayList<JobFileInfo>(), nextElementName);
    }
    
    public AEWorkflowPart(final List<GeneratedWorkflowElement> elements, final List<JobFileInfo> requiredFiles) {
        this(elements, requiredFiles, null);
    }
    
    public AEWorkflowPart(final GeneratedWorkflowElement... elements) {
        this(Arrays.asList(elements), new ArrayList<JobFileInfo>());
    }
    
    private AEWorkflowPart(final List<GeneratedWorkflowElement> elements, final List<JobFileInfo> requiredFiles, final String nextElementName) {
        this.elements = elements;
        this.requiredFiles = requiredFiles;
        this.nextElementName = nextElementName;
    }
    
    public void addOtherPartBefore(final AEWorkflowPart other) {
        this.setNewElements(other.elements, this.elements);
        this.setNewJobFileInfos(other.requiredFiles, this.requiredFiles);
    }
    
    public void addOtherPartAfter(final AEWorkflowPart other) {
        this.setNewElements(this.elements, other.elements);
        this.setNewJobFileInfos(this.requiredFiles, other.requiredFiles);
    }
    
    private void setNewElements(final List<GeneratedWorkflowElement> e1, final List<GeneratedWorkflowElement> e2) {
        final List<GeneratedWorkflowElement> newElements = new ArrayList<GeneratedWorkflowElement>(e1);
        newElements.addAll(e2);
        this.elements = newElements;
    }
    
    private void setNewJobFileInfos(final List<JobFileInfo> f1, final List<JobFileInfo> f2) {
        final List<JobFileInfo> newFiles = new ArrayList<JobFileInfo>(f1);
        newFiles.addAll(f2);
        this.requiredFiles = newFiles;
    }
    
    public List<GeneratedWorkflowElement> getElements() {
        return this.elements;
    }
    
    public String getFirstElementName() {
        return (this.elements == null || this.elements.isEmpty()) ? this.nextElementName : this.elements.get(0).getName();
    }
    
    public List<JobFileInfo> getRequiredFiles() {
        return this.requiredFiles;
    }
}
