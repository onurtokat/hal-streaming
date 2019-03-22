// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonProperty {
    public static final String USE_DEFAULT_NAME = "";
    public static final int INDEX_UNKNOWN = -1;
    
    String value() default "";
    
    boolean required() default false;
    
    int index() default -1;
}
