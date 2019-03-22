// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.util;

public enum AEOozieVariables
{
    NAME_NODE("nameNode"), 
    JOB_TRACKER("jobTracker"), 
    JOB_DIR("jobDirPath"), 
    JOB_ID("jobId"), 
    JOB_NAME("jobName"), 
    QUEUE_NAME("queueName"), 
    ACTIONS_QUEUE_NAME("actionsQueueName"), 
    LAUNCHERS_QUEUE_NAME("launchersQueueName"), 
    COORD_ID("coordId"), 
    JOB_PRIORITY("jobPriority"), 
    HIVE_METASTORE_URIS("hiveMetastoreUris"), 
    HIVE2_JDBC_URL("hive2JdbcUrl");
    
    private final String label;
    
    private AEOozieVariables(final String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return this.label;
    }
    
    public String toRef() {
        return "${" + this.label + "}";
    }
}
