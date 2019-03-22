// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client;

public class InputFileInfo
{
    private JobFileInfo jobFileInfo;
    private String hdfsFilePath;
    
    public InputFileInfo(final JobFileInfo jobFileInfo) {
        this.jobFileInfo = jobFileInfo;
    }
    
    public InputFileInfo(final String hdfsFilePath) {
        this.hdfsFilePath = hdfsFilePath;
    }
    
    public JobFileInfo getJobFileInfo() {
        return this.jobFileInfo;
    }
    
    public String getHdfsFilePath() {
        return this.hdfsFilePath;
    }
    
    public boolean isLocalFile() {
        return this.jobFileInfo != null;
    }
}
