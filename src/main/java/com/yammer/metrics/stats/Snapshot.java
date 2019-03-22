// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.stats;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;

public class Snapshot
{
    private static final double MEDIAN_Q = 0.5;
    private static final double P75_Q = 0.75;
    private static final double P95_Q = 0.95;
    private static final double P98_Q = 0.98;
    private static final double P99_Q = 0.99;
    private static final double P999_Q = 0.999;
    private final double[] values;
    
    public Snapshot(final Collection<Long> values) {
        final Object[] copy = values.toArray();
        this.values = new double[copy.length];
        for (int i = 0; i < copy.length; ++i) {
            this.values[i] = (long)copy[i];
        }
        Arrays.sort(this.values);
    }
    
    public Snapshot(final double[] values) {
        System.arraycopy(values, 0, this.values = new double[values.length], 0, values.length);
        Arrays.sort(this.values);
    }
    
    public double getValue(final double quantile) {
        if (quantile < 0.0 || quantile > 1.0) {
            throw new IllegalArgumentException(quantile + " is not in [0..1]");
        }
        if (this.values.length == 0) {
            return 0.0;
        }
        final double pos = quantile * (this.values.length + 1);
        if (pos < 1.0) {
            return this.values[0];
        }
        if (pos >= this.values.length) {
            return this.values[this.values.length - 1];
        }
        final double lower = this.values[(int)pos - 1];
        final double upper = this.values[(int)pos];
        return lower + (pos - Math.floor(pos)) * (upper - lower);
    }
    
    public int size() {
        return this.values.length;
    }
    
    public double getMedian() {
        return this.getValue(0.5);
    }
    
    public double get75thPercentile() {
        return this.getValue(0.75);
    }
    
    public double get95thPercentile() {
        return this.getValue(0.95);
    }
    
    public double get98thPercentile() {
        return this.getValue(0.98);
    }
    
    public double get99thPercentile() {
        return this.getValue(0.99);
    }
    
    public double get999thPercentile() {
        return this.getValue(0.999);
    }
    
    public double[] getValues() {
        return Arrays.copyOf(this.values, this.values.length);
    }
    
    public void dump(final File output) throws IOException {
        final PrintWriter writer = new PrintWriter(output);
        try {
            for (final double value : this.values) {
                writer.printf("%f\n", value);
            }
        }
        finally {
            writer.close();
        }
    }
}
