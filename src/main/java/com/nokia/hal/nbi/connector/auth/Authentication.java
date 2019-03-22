// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.auth;

import java.util.Map;
import com.nokia.hal.nbi.connector.Pair;
import java.util.List;

public interface Authentication
{
    void applyToParams(final List<Pair> p0, final Map<String, String> p1);
}
