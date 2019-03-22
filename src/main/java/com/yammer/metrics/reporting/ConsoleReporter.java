// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.reporting;

import com.yammer.metrics.core.Timer;
import com.yammer.metrics.stats.Snapshot;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.core.Metered;
import com.yammer.metrics.core.Counter;
import com.yammer.metrics.core.Gauge;
import java.util.Iterator;
import com.yammer.metrics.core.Metric;
import com.yammer.metrics.core.MetricName;
import java.util.SortedMap;
import java.util.Map;
import java.util.Date;
import java.text.DateFormat;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.Metrics;
import java.util.concurrent.TimeUnit;
import java.util.Locale;
import java.util.TimeZone;
import com.yammer.metrics.core.Clock;
import com.yammer.metrics.core.MetricPredicate;
import java.io.PrintStream;
import com.yammer.metrics.core.MetricProcessor;

public class ConsoleReporter extends AbstractPollingReporter implements MetricProcessor<PrintStream>
{
    private static final int CONSOLE_WIDTH = 80;
    private final PrintStream out;
    private final MetricPredicate predicate;
    private final Clock clock;
    private final TimeZone timeZone;
    private final Locale locale;
    
    public static void enable(final long period, final TimeUnit unit) {
        enable(Metrics.defaultRegistry(), period, unit);
    }
    
    public static void enable(final MetricsRegistry metricsRegistry, final long period, final TimeUnit unit) {
        final ConsoleReporter reporter = new ConsoleReporter(metricsRegistry, System.out, MetricPredicate.ALL);
        reporter.start(period, unit);
    }
    
    public ConsoleReporter(final PrintStream out) {
        this(Metrics.defaultRegistry(), out, MetricPredicate.ALL);
    }
    
    public ConsoleReporter(final MetricsRegistry metricsRegistry, final PrintStream out, final MetricPredicate predicate) {
        this(metricsRegistry, out, predicate, Clock.defaultClock(), TimeZone.getDefault());
    }
    
    public ConsoleReporter(final MetricsRegistry metricsRegistry, final PrintStream out, final MetricPredicate predicate, final Clock clock, final TimeZone timeZone) {
        this(metricsRegistry, out, predicate, clock, timeZone, Locale.getDefault());
    }
    
    public ConsoleReporter(final MetricsRegistry metricsRegistry, final PrintStream out, final MetricPredicate predicate, final Clock clock, final TimeZone timeZone, final Locale locale) {
        super(metricsRegistry, "console-reporter");
        this.out = out;
        this.predicate = predicate;
        this.clock = clock;
        this.timeZone = timeZone;
        this.locale = locale;
    }
    
    @Override
    public void run() {
        try {
            final DateFormat format = DateFormat.getDateTimeInstance(3, 2, this.locale);
            format.setTimeZone(this.timeZone);
            final String dateTime = format.format(new Date(this.clock.time()));
            this.out.print(dateTime);
            this.out.print(' ');
            for (int i = 0; i < 80 - dateTime.length() - 1; ++i) {
                this.out.print('=');
            }
            this.out.println();
            for (final Map.Entry<String, SortedMap<MetricName, Metric>> entry : this.getMetricsRegistry().groupedMetrics(this.predicate).entrySet()) {
                this.out.print(entry.getKey());
                this.out.println(':');
                for (final Map.Entry<MetricName, Metric> subEntry : entry.getValue().entrySet()) {
                    this.out.print("  ");
                    this.out.print(subEntry.getKey().getName());
                    this.out.println(':');
                    subEntry.getValue().processWith(this, subEntry.getKey(), this.out);
                    this.out.println();
                }
                this.out.println();
            }
            this.out.println();
            this.out.flush();
        }
        catch (Exception e) {
            e.printStackTrace(this.out);
        }
    }
    
    @Override
    public void processGauge(final MetricName name, final Gauge<?> gauge, final PrintStream stream) {
        stream.printf(this.locale, "    value = %s\n", gauge.value());
    }
    
    @Override
    public void processCounter(final MetricName name, final Counter counter, final PrintStream stream) {
        stream.printf(this.locale, "    count = %d\n", counter.count());
    }
    
    @Override
    public void processMeter(final MetricName name, final Metered meter, final PrintStream stream) {
        final String unit = this.abbrev(meter.rateUnit());
        stream.printf(this.locale, "             count = %d\n", meter.count());
        stream.printf(this.locale, "         mean rate = %2.2f %s/%s\n", meter.meanRate(), meter.eventType(), unit);
        stream.printf(this.locale, "     1-minute rate = %2.2f %s/%s\n", meter.oneMinuteRate(), meter.eventType(), unit);
        stream.printf(this.locale, "     5-minute rate = %2.2f %s/%s\n", meter.fiveMinuteRate(), meter.eventType(), unit);
        stream.printf(this.locale, "    15-minute rate = %2.2f %s/%s\n", meter.fifteenMinuteRate(), meter.eventType(), unit);
    }
    
    @Override
    public void processHistogram(final MetricName name, final Histogram histogram, final PrintStream stream) {
        final Snapshot snapshot = histogram.getSnapshot();
        stream.printf(this.locale, "               min = %2.2f\n", histogram.min());
        stream.printf(this.locale, "               max = %2.2f\n", histogram.max());
        stream.printf(this.locale, "              mean = %2.2f\n", histogram.mean());
        stream.printf(this.locale, "            stddev = %2.2f\n", histogram.stdDev());
        stream.printf(this.locale, "            median = %2.2f\n", snapshot.getMedian());
        stream.printf(this.locale, "              75%% <= %2.2f\n", snapshot.get75thPercentile());
        stream.printf(this.locale, "              95%% <= %2.2f\n", snapshot.get95thPercentile());
        stream.printf(this.locale, "              98%% <= %2.2f\n", snapshot.get98thPercentile());
        stream.printf(this.locale, "              99%% <= %2.2f\n", snapshot.get99thPercentile());
        stream.printf(this.locale, "            99.9%% <= %2.2f\n", snapshot.get999thPercentile());
    }
    
    @Override
    public void processTimer(final MetricName name, final Timer timer, final PrintStream stream) {
        this.processMeter(name, timer, stream);
        final String durationUnit = this.abbrev(timer.durationUnit());
        final Snapshot snapshot = timer.getSnapshot();
        stream.printf(this.locale, "               min = %2.2f%s\n", timer.min(), durationUnit);
        stream.printf(this.locale, "               max = %2.2f%s\n", timer.max(), durationUnit);
        stream.printf(this.locale, "              mean = %2.2f%s\n", timer.mean(), durationUnit);
        stream.printf(this.locale, "            stddev = %2.2f%s\n", timer.stdDev(), durationUnit);
        stream.printf(this.locale, "            median = %2.2f%s\n", snapshot.getMedian(), durationUnit);
        stream.printf(this.locale, "              75%% <= %2.2f%s\n", snapshot.get75thPercentile(), durationUnit);
        stream.printf(this.locale, "              95%% <= %2.2f%s\n", snapshot.get95thPercentile(), durationUnit);
        stream.printf(this.locale, "              98%% <= %2.2f%s\n", snapshot.get98thPercentile(), durationUnit);
        stream.printf(this.locale, "              99%% <= %2.2f%s\n", snapshot.get99thPercentile(), durationUnit);
        stream.printf(this.locale, "            99.9%% <= %2.2f%s\n", snapshot.get999thPercentile(), durationUnit);
    }
    
    private String abbrev(final TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS: {
                return "ns";
            }
            case MICROSECONDS: {
                return "us";
            }
            case MILLISECONDS: {
                return "ms";
            }
            case SECONDS: {
                return "s";
            }
            case MINUTES: {
                return "m";
            }
            case HOURS: {
                return "h";
            }
            case DAYS: {
                return "d";
            }
            default: {
                throw new IllegalArgumentException("Unrecognized TimeUnit: " + unit);
            }
        }
    }
}
