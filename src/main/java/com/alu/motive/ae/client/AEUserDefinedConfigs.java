// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client;

import com.motive.mas.configuration.service.api.ConfigurationService;

public interface AEUserDefinedConfigs
{
    String getHdfsURL();
    
    String getHdfsUserName();
    
    String getThriftServer();
    
    int getThriftServerPort();
    
    String getOozieURL();
    
    String getAeMonitoringURL();
    
    String getJobTrackerServer();
    
    int getJobTrackerServerPort();
    
    String getHiveMetastoreUris();
    
    ConfigurationService getMasConfigurationService();
}
