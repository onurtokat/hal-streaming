// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.core.spi.component;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public interface ComponentContext
{
    AccessibleObject getAccesibleObject();
    
    Annotation[] getAnnotations();
}
