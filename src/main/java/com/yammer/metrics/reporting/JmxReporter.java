// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.reporting;

import java.util.concurrent.TimeUnit;
import org.slf4j.LoggerFactory;
import javax.management.InstanceNotFoundException;
import javax.management.OperationsException;
import javax.management.MBeanRegistrationException;
import java.util.Iterator;
import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.core.Counter;
import com.yammer.metrics.core.Metered;
import com.yammer.metrics.core.Metric;
import java.lang.management.ManagementFactory;
import java.util.concurrent.ConcurrentHashMap;
import com.yammer.metrics.core.MetricsRegistry;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import com.yammer.metrics.core.MetricName;
import java.util.Map;
import org.slf4j.Logger;
import com.yammer.metrics.core.MetricProcessor;
import com.yammer.metrics.core.MetricsRegistryListener;

public class JmxReporter extends AbstractReporter implements MetricsRegistryListener, MetricProcessor<Context>
{
    private static final Logger LOGGER;
    private final Map<MetricName, ObjectName> registeredBeans;
    private final MBeanServer server;
    private static JmxReporter INSTANCE;
    
    public static void startDefault(final MetricsRegistry registry) {
        (JmxReporter.INSTANCE = new JmxReporter(registry)).start();
    }
    
    public static JmxReporter getDefault() {
        return JmxReporter.INSTANCE;
    }
    
    public static void shutdownDefault() {
        if (JmxReporter.INSTANCE != null) {
            JmxReporter.INSTANCE.shutdown();
        }
    }
    
    public JmxReporter(final MetricsRegistry registry) {
        super(registry);
        this.registeredBeans = new ConcurrentHashMap<MetricName, ObjectName>(100);
        this.server = ManagementFactory.getPlatformMBeanServer();
    }
    
    @Override
    public void onMetricAdded(final MetricName name, final Metric metric) {
        if (metric != null) {
            try {
                metric.processWith(this, name, new Context(name, new ObjectName(name.getMBeanName())));
            }
            catch (Exception e) {
                JmxReporter.LOGGER.warn("Error processing {}", name, e);
            }
        }
    }
    
    @Override
    public void onMetricRemoved(final MetricName name) {
        final ObjectName objectName = this.registeredBeans.remove(name);
        if (objectName != null) {
            this.unregisterBean(objectName);
        }
    }
    
    @Override
    public void processMeter(final MetricName name, final Metered meter, final Context context) throws Exception {
        this.registerBean(context.getMetricName(), new Meter(meter, context.getObjectName()), context.getObjectName());
    }
    
    @Override
    public void processCounter(final MetricName name, final com.yammer.metrics.core.Counter counter, final Context context) throws Exception {
        this.registerBean(context.getMetricName(), new Counter(counter, context.getObjectName()), context.getObjectName());
    }
    
    @Override
    public void processHistogram(final MetricName name, final com.yammer.metrics.core.Histogram histogram, final Context context) throws Exception {
        this.registerBean(context.getMetricName(), new Histogram(histogram, context.getObjectName()), context.getObjectName());
    }
    
    @Override
    public void processTimer(final MetricName name, final com.yammer.metrics.core.Timer timer, final Context context) throws Exception {
        this.registerBean(context.getMetricName(), new Timer(timer, context.getObjectName()), context.getObjectName());
    }
    
    @Override
    public void processGauge(final MetricName name, final com.yammer.metrics.core.Gauge<?> gauge, final Context context) throws Exception {
        this.registerBean(context.getMetricName(), new Gauge((com.yammer.metrics.core.Gauge)gauge, context.getObjectName()), context.getObjectName());
    }
    
    @Override
    public void shutdown() {
        this.getMetricsRegistry().removeListener(this);
        for (final ObjectName name : this.registeredBeans.values()) {
            this.unregisterBean(name);
        }
        this.registeredBeans.clear();
    }
    
    public final void start() {
        this.getMetricsRegistry().addListener(this);
    }
    
    private void registerBean(final MetricName name, final MetricMBean bean, final ObjectName objectName) throws MBeanRegistrationException, OperationsException {
        if (this.server.isRegistered(objectName)) {
            this.server.unregisterMBean(objectName);
        }
        this.server.registerMBean(bean, objectName);
        this.registeredBeans.put(name, objectName);
    }
    
    private void unregisterBean(final ObjectName name) {
        try {
            this.server.unregisterMBean(name);
        }
        catch (InstanceNotFoundException e) {
            JmxReporter.LOGGER.trace("Error unregistering {}", name, e);
        }
        catch (MBeanRegistrationException e2) {
            JmxReporter.LOGGER.debug("Error unregistering {}", name, e2);
        }
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(JmxReporter.class);
    }
    
    private abstract static class AbstractBean implements MetricMBean
    {
        private final ObjectName objectName;
        
        protected AbstractBean(final ObjectName objectName) {
            this.objectName = objectName;
        }
        
        @Override
        public ObjectName objectName() {
            return this.objectName;
        }
    }
    
    private static class Gauge extends AbstractBean implements GaugeMBean
    {
        private final com.yammer.metrics.core.Gauge<?> metric;
        
        private Gauge(final com.yammer.metrics.core.Gauge<?> metric, final ObjectName objectName) {
            super(objectName);
            this.metric = metric;
        }
        
        @Override
        public Object getValue() {
            return this.metric.value();
        }
    }
    
    private static class Counter extends AbstractBean implements CounterMBean
    {
        private final com.yammer.metrics.core.Counter metric;
        
        private Counter(final com.yammer.metrics.core.Counter metric, final ObjectName objectName) {
            super(objectName);
            this.metric = metric;
        }
        
        @Override
        public long getCount() {
            return this.metric.count();
        }
    }
    
    private static class Meter extends AbstractBean implements MeterMBean
    {
        private final Metered metric;
        
        private Meter(final Metered metric, final ObjectName objectName) {
            super(objectName);
            this.metric = metric;
        }
        
        @Override
        public long getCount() {
            return this.metric.count();
        }
        
        @Override
        public String getEventType() {
            return this.metric.eventType();
        }
        
        @Override
        public TimeUnit getRateUnit() {
            return this.metric.rateUnit();
        }
        
        @Override
        public double getMeanRate() {
            return this.metric.meanRate();
        }
        
        @Override
        public double getOneMinuteRate() {
            return this.metric.oneMinuteRate();
        }
        
        @Override
        public double getFiveMinuteRate() {
            return this.metric.fiveMinuteRate();
        }
        
        @Override
        public double getFifteenMinuteRate() {
            return this.metric.fifteenMinuteRate();
        }
    }
    
    private static class Histogram implements HistogramMBean
    {
        private final ObjectName objectName;
        private final com.yammer.metrics.core.Histogram metric;
        
        private Histogram(final com.yammer.metrics.core.Histogram metric, final ObjectName objectName) {
            this.metric = metric;
            this.objectName = objectName;
        }
        
        @Override
        public ObjectName objectName() {
            return this.objectName;
        }
        
        @Override
        public double get50thPercentile() {
            return this.metric.getSnapshot().getMedian();
        }
        
        @Override
        public long getCount() {
            return this.metric.count();
        }
        
        @Override
        public double getMin() {
            return this.metric.min();
        }
        
        @Override
        public double getMax() {
            return this.metric.max();
        }
        
        @Override
        public double getMean() {
            return this.metric.mean();
        }
        
        @Override
        public double getStdDev() {
            return this.metric.stdDev();
        }
        
        @Override
        public double get75thPercentile() {
            return this.metric.getSnapshot().get75thPercentile();
        }
        
        @Override
        public double get95thPercentile() {
            return this.metric.getSnapshot().get95thPercentile();
        }
        
        @Override
        public double get98thPercentile() {
            return this.metric.getSnapshot().get98thPercentile();
        }
        
        @Override
        public double get99thPercentile() {
            return this.metric.getSnapshot().get99thPercentile();
        }
        
        @Override
        public double get999thPercentile() {
            return this.metric.getSnapshot().get999thPercentile();
        }
        
        @Override
        public double[] values() {
            return this.metric.getSnapshot().getValues();
        }
    }
    
    static class Timer extends Meter implements TimerMBean
    {
        private final com.yammer.metrics.core.Timer metric;
        
        private Timer(final com.yammer.metrics.core.Timer metric, final ObjectName objectName) {
            super((Metered)metric, objectName);
            this.metric = metric;
        }
        
        @Override
        public double get50thPercentile() {
            return this.metric.getSnapshot().getMedian();
        }
        
        @Override
        public TimeUnit getLatencyUnit() {
            return this.metric.durationUnit();
        }
        
        @Override
        public double getMin() {
            return this.metric.min();
        }
        
        @Override
        public double getMax() {
            return this.metric.max();
        }
        
        @Override
        public double getMean() {
            return this.metric.mean();
        }
        
        @Override
        public double getStdDev() {
            return this.metric.stdDev();
        }
        
        @Override
        public double get75thPercentile() {
            return this.metric.getSnapshot().get75thPercentile();
        }
        
        @Override
        public double get95thPercentile() {
            return this.metric.getSnapshot().get95thPercentile();
        }
        
        @Override
        public double get98thPercentile() {
            return this.metric.getSnapshot().get98thPercentile();
        }
        
        @Override
        public double get99thPercentile() {
            return this.metric.getSnapshot().get99thPercentile();
        }
        
        @Override
        public double get999thPercentile() {
            return this.metric.getSnapshot().get999thPercentile();
        }
        
        @Override
        public double[] values() {
            return this.metric.getSnapshot().getValues();
        }
    }
    
    static final class Context
    {
        private final MetricName metricName;
        private final ObjectName objectName;
        
        public Context(final MetricName metricName, final ObjectName objectName) {
            this.metricName = metricName;
            this.objectName = objectName;
        }
        
        MetricName getMetricName() {
            return this.metricName;
        }
        
        ObjectName getObjectName() {
            return this.objectName;
        }
    }
    
    public interface MetricMBean
    {
        ObjectName objectName();
    }
    
    public interface MeterMBean extends MetricMBean
    {
        long getCount();
        
        String getEventType();
        
        TimeUnit getRateUnit();
        
        double getMeanRate();
        
        double getOneMinuteRate();
        
        double getFiveMinuteRate();
        
        double getFifteenMinuteRate();
    }
    
    public interface TimerMBean extends MeterMBean, HistogramMBean
    {
        TimeUnit getLatencyUnit();
    }
    
    public interface HistogramMBean extends MetricMBean
    {
        long getCount();
        
        double getMin();
        
        double getMax();
        
        double getMean();
        
        double getStdDev();
        
        double get50thPercentile();
        
        double get75thPercentile();
        
        double get95thPercentile();
        
        double get98thPercentile();
        
        double get99thPercentile();
        
        double get999thPercentile();
        
        double[] values();
    }
    
    public interface CounterMBean extends MetricMBean
    {
        long getCount();
    }
    
    public interface GaugeMBean extends MetricMBean
    {
        Object getValue();
    }
}
