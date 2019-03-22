// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.action.nest.model;

import java.util.Iterator;
import com.alu.motive.ae.client.util.AEVariables;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import com.alu.motive.ae.client.JobFileInfo;
import java.util.List;

public class QueryBuilderResult
{
    private String hqlFileContent;
    private final List<JobFileInfo> jobFileInfo;
    private final Map<String, String> hiveParams;
    
    public QueryBuilderResult(final String hqlFileContent) {
        this.jobFileInfo = new LinkedList<JobFileInfo>();
        this.hiveParams = new LinkedHashMap<String, String>();
        this.hqlFileContent = hqlFileContent;
    }
    
    public QueryBuilderResult(final String query, final Collection<AEVariables> reportedVariables) {
        this.jobFileInfo = new LinkedList<JobFileInfo>();
        this.hiveParams = new LinkedHashMap<String, String>();
        this.hqlFileContent = query;
        for (final AEVariables aeVariables : reportedVariables) {
            this.hiveParams.put(aeVariables.getDisplayName(), aeVariables.getDisplayNameAsOozieVariable());
        }
    }
    
    public String getHqlFileContent() {
        return this.hqlFileContent;
    }
    
    public Map<String, String> getHiveParams() {
        return this.hiveParams;
    }
    
    public void addHiveParameter(final String key, final String value) {
        this.hiveParams.put(key, value);
    }
    
    public void addHiveParameters(final Map<String, String> otherParameters) {
        this.hiveParams.putAll(otherParameters);
    }
    
    public void appendHqlFileContent(final String otherContent) {
        this.hqlFileContent += otherContent;
    }
    
    public void appendResult(final QueryBuilderResult otherResult) {
        this.hqlFileContent += otherResult.hqlFileContent;
        this.hiveParams.putAll(otherResult.hiveParams);
        this.jobFileInfo.addAll(otherResult.getJobFileInfos());
    }
    
    public void addJobFileInfo(final JobFileInfo jobFile) {
        this.jobFileInfo.add(jobFile);
    }
    
    public List<JobFileInfo> getJobFileInfos() {
        return this.jobFileInfo;
    }
}
