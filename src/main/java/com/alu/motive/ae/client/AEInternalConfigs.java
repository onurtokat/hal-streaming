// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client;

public interface AEInternalConfigs
{
    public static final String JOBS_DIR_HDFS_PATH = "/data/ae/jobs";
    public static final String JOBS_DIR_UPDATE_PATH = "/data/ae/jobs/update";
    
    String getOozieLaunchersQueueName();
    
    String getOozieActionsQueueName();
    
    String getHdfsAeLibDirPath();
}
