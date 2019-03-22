// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.reporting;

import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.stats.Snapshot;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.core.Counter;
import com.yammer.metrics.core.Metered;
import java.util.Iterator;
import java.util.Set;
import com.yammer.metrics.core.Metric;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.Metrics;
import java.util.concurrent.TimeUnit;
import com.yammer.metrics.core.Clock;
import java.io.PrintStream;
import com.yammer.metrics.core.MetricName;
import java.util.Map;
import java.io.File;
import com.yammer.metrics.core.MetricPredicate;
import com.yammer.metrics.core.MetricProcessor;

public class CsvReporter extends AbstractPollingReporter implements MetricProcessor<Context>
{
    private final MetricPredicate predicate;
    private final File outputDir;
    private final Map<MetricName, PrintStream> streamMap;
    private final Clock clock;
    private long startTime;
    
    public static void enable(final File outputDir, final long period, final TimeUnit unit) {
        enable(Metrics.defaultRegistry(), outputDir, period, unit);
    }
    
    public static void enable(final MetricsRegistry metricsRegistry, final File outputDir, final long period, final TimeUnit unit) {
        final CsvReporter reporter = new CsvReporter(metricsRegistry, outputDir);
        reporter.start(period, unit);
    }
    
    public CsvReporter(final MetricsRegistry metricsRegistry, final File outputDir) {
        this(metricsRegistry, MetricPredicate.ALL, outputDir);
    }
    
    public CsvReporter(final MetricsRegistry metricsRegistry, final MetricPredicate predicate, final File outputDir) {
        this(metricsRegistry, predicate, outputDir, Clock.defaultClock());
    }
    
    public CsvReporter(final MetricsRegistry metricsRegistry, final MetricPredicate predicate, final File outputDir, final Clock clock) {
        super(metricsRegistry, "csv-reporter");
        if (outputDir.exists() && !outputDir.isDirectory()) {
            throw new IllegalArgumentException(outputDir + " is not a directory");
        }
        this.outputDir = outputDir;
        this.predicate = predicate;
        this.streamMap = new HashMap<MetricName, PrintStream>();
        this.startTime = 0L;
        this.clock = clock;
    }
    
    protected PrintStream createStreamForMetric(final MetricName metricName) throws IOException {
        final File newFile = new File(this.outputDir, metricName.getName() + ".csv");
        if (newFile.createNewFile()) {
            return new PrintStream(new FileOutputStream(newFile));
        }
        throw new IOException("Unable to create " + newFile);
    }
    
    @Override
    public void run() {
        final long time = TimeUnit.MILLISECONDS.toSeconds(this.clock.time() - this.startTime);
        final Set<Map.Entry<MetricName, Metric>> metrics = this.getMetricsRegistry().allMetrics().entrySet();
        try {
            for (final Map.Entry<MetricName, Metric> entry : metrics) {
                final MetricName metricName = entry.getKey();
                final Metric metric = entry.getValue();
                if (this.predicate.matches(metricName, metric)) {
                    final Context context = new Context() {
                        @Override
                        public PrintStream getStream(final String header) throws IOException {
                            final PrintStream stream = CsvReporter.this.getPrintStream(metricName, header);
                            stream.print(time);
                            stream.print(',');
                            return stream;
                        }
                    };
                    metric.processWith(this, entry.getKey(), context);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void processMeter(final MetricName name, final Metered meter, final Context context) throws IOException {
        final PrintStream stream = context.getStream("# time,count,1 min rate,mean rate,5 min rate,15 min rate");
        stream.append(new StringBuilder().append(meter.count()).append(',').append(meter.oneMinuteRate()).append(',').append(meter.meanRate()).append(',').append(meter.fiveMinuteRate()).append(',').append(meter.fifteenMinuteRate()).toString()).println();
        stream.flush();
    }
    
    @Override
    public void processCounter(final MetricName name, final Counter counter, final Context context) throws IOException {
        final PrintStream stream = context.getStream("# time,count");
        stream.println(counter.count());
        stream.flush();
    }
    
    @Override
    public void processHistogram(final MetricName name, final Histogram histogram, final Context context) throws IOException {
        final PrintStream stream = context.getStream("# time,min,max,mean,median,stddev,95%,99%,99.9%");
        final Snapshot snapshot = histogram.getSnapshot();
        stream.append(new StringBuilder().append(histogram.min()).append(',').append(histogram.max()).append(',').append(histogram.mean()).append(',').append(snapshot.getMedian()).append(',').append(histogram.stdDev()).append(',').append(snapshot.get95thPercentile()).append(',').append(snapshot.get99thPercentile()).append(',').append(snapshot.get999thPercentile()).toString()).println();
        stream.println();
        stream.flush();
    }
    
    @Override
    public void processTimer(final MetricName name, final Timer timer, final Context context) throws IOException {
        final PrintStream stream = context.getStream("# time,min,max,mean,median,stddev,95%,99%,99.9%");
        final Snapshot snapshot = timer.getSnapshot();
        stream.append(new StringBuilder().append(timer.min()).append(',').append(timer.max()).append(',').append(timer.mean()).append(',').append(snapshot.getMedian()).append(',').append(timer.stdDev()).append(',').append(snapshot.get95thPercentile()).append(',').append(snapshot.get99thPercentile()).append(',').append(snapshot.get999thPercentile()).toString()).println();
        stream.flush();
    }
    
    @Override
    public void processGauge(final MetricName name, final Gauge<?> gauge, final Context context) throws IOException {
        final PrintStream stream = context.getStream("# time,value");
        stream.println(gauge.value());
        stream.flush();
    }
    
    @Override
    public void start(final long period, final TimeUnit unit) {
        this.startTime = this.clock.time();
        super.start(period, unit);
    }
    
    @Override
    public void shutdown() {
        try {
            super.shutdown();
        }
        finally {
            for (final PrintStream out : this.streamMap.values()) {
                out.close();
            }
        }
    }
    
    private PrintStream getPrintStream(final MetricName metricName, final String header) throws IOException {
        PrintStream stream;
        synchronized (this.streamMap) {
            stream = this.streamMap.get(metricName);
            if (stream == null) {
                stream = this.createStreamForMetric(metricName);
                this.streamMap.put(metricName, stream);
                stream.println(header);
            }
        }
        return stream;
    }
    
    public interface Context
    {
        PrintStream getStream(final String p0) throws IOException;
    }
}
