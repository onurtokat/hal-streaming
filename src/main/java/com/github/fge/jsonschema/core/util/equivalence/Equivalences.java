// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util.equivalence;

import com.google.common.base.Equivalence;

public final class Equivalences
{
    public static <T> Equivalence<T> equals() {
        return (Equivalence<T>)Equivalence.equals();
    }
    
    public static <T> Equivalence<T> identity() {
        return (Equivalence<T>)Equivalence.identity();
    }
}
