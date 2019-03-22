// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.colmapping;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Map;

public class NBIColumnAdditionalInfoCfgUtils
{
    public Map<String, InsightAdditionalInfo> getMapFromJSONCfg(final String mappingJson) throws IOException, JsonParseException, JsonMappingException {
        final TypeReference<Map<String, InsightAdditionalInfo>> typeRef = new TypeReference<Map<String, InsightAdditionalInfo>>() {};
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, InsightAdditionalInfo> mapping = mapper.readValue(mappingJson, typeRef);
        return mapping;
    }
}
