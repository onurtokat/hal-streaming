// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.format;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import java.util.EnumSet;

public interface FormatAttribute
{
    EnumSet<NodeType> supportedTypes();
    
    void validate(final ProcessingReport p0, final MessageBundle p1, final FullData p2) throws ProcessingException;
}
