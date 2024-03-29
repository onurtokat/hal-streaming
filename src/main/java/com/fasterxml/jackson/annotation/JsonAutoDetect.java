// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.annotation;

import java.lang.reflect.Modifier;
import java.lang.reflect.Member;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonAutoDetect {
    Visibility getterVisibility() default Visibility.DEFAULT;
    
    Visibility isGetterVisibility() default Visibility.DEFAULT;
    
    Visibility setterVisibility() default Visibility.DEFAULT;
    
    Visibility creatorVisibility() default Visibility.DEFAULT;
    
    Visibility fieldVisibility() default Visibility.DEFAULT;
    
    public enum Visibility
    {
        ANY, 
        NON_PRIVATE, 
        PROTECTED_AND_PUBLIC, 
        PUBLIC_ONLY, 
        NONE, 
        DEFAULT;
        
        public boolean isVisible(final Member m) {
            switch (this) {
                case ANY: {
                    return true;
                }
                case NONE: {
                    return false;
                }
                case NON_PRIVATE: {
                    return !Modifier.isPrivate(m.getModifiers());
                }
                case PROTECTED_AND_PUBLIC: {
                    if (Modifier.isProtected(m.getModifiers())) {
                        return true;
                    }
                    return Modifier.isPublic(m.getModifiers());
                }
                case PUBLIC_ONLY: {
                    return Modifier.isPublic(m.getModifiers());
                }
                default: {
                    return false;
                }
            }
        }
    }
}
