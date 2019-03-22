// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.main.cli;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.processors.syntax.SyntaxValidator;

interface Reporter
{
    RetCode validateSchema(final SyntaxValidator p0, final String p1, final JsonNode p2) throws IOException;
    
    RetCode validateInstance(final JsonSchema p0, final String p1, final JsonNode p2) throws IOException, ProcessingException;
}
