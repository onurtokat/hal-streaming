// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.api.core;

import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectParam {
    String value() default "";
}
