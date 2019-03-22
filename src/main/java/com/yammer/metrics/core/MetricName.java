// 
// Decompiled by Procyon v0.5.30
// 

package com.yammer.metrics.core;

import java.lang.reflect.Method;
import javax.management.ObjectName;

public class MetricName implements Comparable<MetricName>
{
    private final String group;
    private final String type;
    private final String name;
    private final String scope;
    private final String mBeanName;
    
    public MetricName(final Class<?> klass, final String name) {
        this(klass, name, null);
    }
    
    public MetricName(final String group, final String type, final String name) {
        this(group, type, name, null);
    }
    
    public MetricName(final Class<?> klass, final String name, final String scope) {
        this((klass.getPackage() == null) ? "" : klass.getPackage().getName(), klass.getSimpleName().replaceAll("\\$$", ""), name, scope);
    }
    
    public MetricName(final String group, final String type, final String name, final String scope) {
        this(group, type, name, scope, createMBeanName(group, type, name, scope));
    }
    
    public MetricName(final String group, final String type, final String name, final String scope, final String mBeanName) {
        if (group == null || type == null) {
            throw new IllegalArgumentException("Both group and type need to be specified");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name needs to be specified");
        }
        this.group = group;
        this.type = type;
        this.name = name;
        this.scope = scope;
        this.mBeanName = mBeanName;
    }
    
    public String getGroup() {
        return this.group;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getScope() {
        return this.scope;
    }
    
    public boolean hasScope() {
        return this.scope != null;
    }
    
    public String getMBeanName() {
        return this.mBeanName;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final MetricName that = (MetricName)o;
        return this.mBeanName.equals(that.mBeanName);
    }
    
    @Override
    public int hashCode() {
        return this.mBeanName.hashCode();
    }
    
    @Override
    public String toString() {
        return this.mBeanName;
    }
    
    @Override
    public int compareTo(final MetricName o) {
        return this.mBeanName.compareTo(o.mBeanName);
    }
    
    private static String createMBeanName(final String group, final String type, final String name, final String scope) {
        final StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append(ObjectName.quote(group));
        nameBuilder.append(":type=");
        nameBuilder.append(ObjectName.quote(type));
        if (scope != null) {
            nameBuilder.append(",scope=");
            nameBuilder.append(ObjectName.quote(scope));
        }
        if (name.length() > 0) {
            nameBuilder.append(",name=");
            nameBuilder.append(ObjectName.quote(name));
        }
        return nameBuilder.toString();
    }
    
    public static String chooseGroup(String group, final Class<?> klass) {
        if (group == null || group.isEmpty()) {
            group = ((klass.getPackage() == null) ? "" : klass.getPackage().getName());
        }
        return group;
    }
    
    public static String chooseType(String type, final Class<?> klass) {
        if (type == null || type.isEmpty()) {
            type = klass.getSimpleName().replaceAll("\\$$", "");
        }
        return type;
    }
    
    public static String chooseName(String name, final Method method) {
        if (name == null || name.isEmpty()) {
            name = method.getName();
        }
        return name;
    }
}
