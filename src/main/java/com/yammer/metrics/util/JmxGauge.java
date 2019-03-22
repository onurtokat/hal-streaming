// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.util;

import java.lang.management.ManagementFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.MBeanServer;
import com.yammer.metrics.core.Gauge;

public class JmxGauge extends Gauge<Object>
{
    private static final MBeanServer SERVER;
    private final ObjectName objectName;
    private final String attribute;
    
    public JmxGauge(final String objectName, final String attribute) throws MalformedObjectNameException {
        this(new ObjectName(objectName), attribute);
    }
    
    public JmxGauge(final ObjectName objectName, final String attribute) {
        this.objectName = objectName;
        this.attribute = attribute;
    }
    
    @Override
    public Object value() {
        try {
            return JmxGauge.SERVER.getAttribute(this.objectName, this.attribute);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    static {
        SERVER = ManagementFactory.getPlatformMBeanServer();
    }
}
