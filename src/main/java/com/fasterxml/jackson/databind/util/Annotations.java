// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.util;

import java.lang.annotation.Annotation;

public interface Annotations
{
     <A extends Annotation> A get(final Class<A> p0);
    
    int size();
}
