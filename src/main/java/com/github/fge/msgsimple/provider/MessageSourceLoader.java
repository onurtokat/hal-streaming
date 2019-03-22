// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple.provider;

import java.io.IOException;
import com.github.fge.msgsimple.source.MessageSource;
import java.util.Locale;

public interface MessageSourceLoader
{
    MessageSource load(final Locale p0) throws IOException;
}
