// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.connectorconfiguration;

import com.motive.mas.util.PasswordEncryptionDecryptionUtil;
import com.nokia.hal.nbi.connector.ConnectorConfig;

public class EncryptedConnectorConfig extends ConnectorConfig
{
    @Override
    public ConnectorConfig withPassword(final String password) {
        return this.withEncryptedPassword(password);
    }
    
    public ConnectorConfig withEncryptedPassword(final String password) {
        this.password = PasswordEncryptionDecryptionUtil.decrypt(password);
        return this;
    }
}
