// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.api.provider.jaxb;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlHeader {
    String value();
}
