// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.spi.container;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceFilters {
    Class<? extends ResourceFilter>[] value();
}
