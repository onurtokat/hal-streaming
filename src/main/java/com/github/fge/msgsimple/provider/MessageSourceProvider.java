// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple.provider;

import com.github.fge.msgsimple.source.MessageSource;
import java.util.Locale;

public interface MessageSourceProvider
{
    MessageSource getMessageSource(final Locale p0);
}
