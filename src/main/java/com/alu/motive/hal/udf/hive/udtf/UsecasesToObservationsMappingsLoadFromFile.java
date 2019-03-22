// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udtf;

import org.slf4j.LoggerFactory;
import java.io.Reader;
import java.io.BufferedReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Iterator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.slf4j.Logger;

public class UsecasesToObservationsMappingsLoadFromFile
{
    static final Logger logger;
    static final String observationColPrefix = "OBS";
    static final Integer observationColFirstIndex;
    static final String defaultSeverity = "Good";
    final Map<String, Integer> mappings;
    
    public UsecasesToObservationsMappingsLoadFromFile(final InputStreamReader is) throws IOException {
        this.mappings = load(is);
    }
    
    public String[] getMappings(final Map<String, String> ucToSeverities, final Integer outputSize) {
        final String[] result = new String[(int)outputSize];
        for (final Map.Entry<String, Integer> entry : this.mappings.entrySet()) {
            if (ucToSeverities.containsKey(entry.getKey()) && ucToSeverities.get(entry.getKey()) != null && !ucToSeverities.get(entry.getKey()).isEmpty()) {
                if (entry.getValue() < outputSize) {
                    result[entry.getValue()] = ucToSeverities.get(entry.getKey());
                }
                else {
                    UsecasesToObservationsMappingsLoadFromFile.logger.warn("OutputSize({}) not enough for mapping {}->{}", outputSize, entry.getKey(), entry.getValue());
                }
            }
            else {
                result[entry.getValue()] = "Good";
            }
        }
        return result;
    }
    
    private static Map<String, Integer> load(final InputStreamReader is) throws IOException {
        UsecasesToObservationsMappingsLoadFromFile.logger.debug("load method called");
        final StringBuilder sb = readStreamToString(is);
        final ObjectMapper mapper = new ObjectMapper();
        final TypeReference<Map<String, Integer>> typeRef = new TypeReference<Map<String, Integer>>() {};
        final Map<String, Integer> result = mapper.readValue(sb.toString(), typeRef);
        UsecasesToObservationsMappingsLoadFromFile.logger.info("Successfully loaded mapping.");
        return result;
    }
    
    private static StringBuilder readStreamToString(final InputStreamReader is) {
        final StringBuilder sb = new StringBuilder();
        try {
            final BufferedReader br = new BufferedReader(is);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }
    
    static {
        logger = LoggerFactory.getLogger(UsecasesToObservationsMappingsLoadFromFile.class);
        observationColFirstIndex = 1;
    }
}
