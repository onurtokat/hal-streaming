// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.nokia.hal.nbi.connector.connectorconfiguration.EncryptedConnectorConfig;

public class ConnectorConfig
{
    protected static final String BASEPATH = "/hal-nbi/metadata/";
    private static final String DEFAULT_PORT = "8081";
    protected String host;
    protected String username;
    protected String password;
    protected Integer timeout;
    private String port;
    
    protected ConnectorConfig() {
        this.timeout = 20;
    }
    
    public static ConnectorConfig getInstance() {
        return new EncryptedConnectorConfig();
    }
    
    public ConnectorConfig setup() {
        if (null != this.port) {
            this.setupConnector(this.host, "/hal-nbi/metadata/", this.port, this.username, this.password);
        }
        else {
            this.setupConnector(this.host, "/hal-nbi/metadata/", "8081", this.username, this.password);
        }
        return this;
    }
    
    public ConnectorConfig withHost(final String host) {
        this.host = host;
        return this;
    }
    
    public ConnectorConfig withPort(final String port) {
        this.port = port;
        return this;
    }
    
    public ConnectorConfig withUsername(final String username) {
        this.username = username;
        return this;
    }
    
    public ConnectorConfig withPassword(final String password) {
        this.password = password;
        return this;
    }
    
    public ConnectorConfig withTimeout(final Integer timeout) {
        this.timeout = timeout;
        return this;
    }
    
    private void setupConnector(String baseHostParam, final String basePathParam, String portParam, String usernameParam, String passwordParam) {
        baseHostParam = getValueFromPrioritizedParameters(baseHostParam, "metadataserver.host");
        usernameParam = getValueFromPrioritizedParameters(usernameParam, "metadataserver.username");
        passwordParam = getValueFromPrioritizedParameters(passwordParam, "metadataserver.password");
        portParam = getValueFromPrioritizedParameters(portParam, "metadataserver.port");
        if (null == SessionId.INSTANCE.sessionId && this.coolDownTimeReached(SessionId.INSTANCE.sessionObtainedDate)) {
            this.createSession(this.getCasSession().obtain(baseHostParam, basePathParam, portParam, usernameParam, passwordParam));
            this.createSessionDate();
            Configuration.getDefaultApiClient().setBasePath(baseHostParam + ":" + portParam + basePathParam);
            Configuration.getDefaultApiClient().setVerifyingSsl(false);
            Configuration.getDefaultApiClient().getHttpClient().setReadTimeout(this.timeout, TimeUnit.SECONDS);
            Configuration.getDefaultApiClient().addDefaultHeader("Cookie", "JSESSIONID=" + SessionId.INSTANCE.sessionId);
        }
    }
    
    boolean coolDownTimeReached(final Date date) {
        if (null == date) {
            return true;
        }
        final Date now = new Date();
        now.setTime(now.getTime() - 2500000L);
        return now.after(date);
    }
    
    CASSession getCasSession() {
        return new CASSession();
    }
    
    private static String getValueFromPrioritizedParameters(final String defaultValue, final String propertyKey) {
        String propertValue;
        if (defaultValue == null) {
            propertValue = System.getProperty(propertyKey);
        }
        else {
            propertValue = defaultValue;
        }
        if (propertValue == null || propertValue.isEmpty()) {
            throw new IllegalArgumentException(String.format("Parameter is not provided with the call, nor with SystemProperty (key: '%s').", propertyKey));
        }
        return propertValue;
    }
    
    public final String getSessionID() {
        if (null == SessionId.INSTANCE.sessionId) {
            throw new RuntimeException("Connector is not yet initialized, please run the 'setup' process in advance.");
        }
        return SessionId.INSTANCE.sessionId;
    }
    
    void createSession(final String s) {
        SessionId.INSTANCE.sessionId = s;
    }
    
    void createSessionDate() {
        SessionId.INSTANCE.sessionObtainedDate = new Date();
    }
    
    public void destroySession() {
        SessionId.INSTANCE.sessionObtainedDate = null;
        SessionId.INSTANCE.sessionId = null;
    }
    
    enum SessionId
    {
        INSTANCE;
        
        private volatile String sessionId;
        private volatile Date sessionObtainedDate;
    }
}
