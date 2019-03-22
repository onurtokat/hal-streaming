// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.eud;

import org.slf4j.LoggerFactory;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.io.IOException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jackson.JsonLoader;
import com.fasterxml.jackson.core.JsonParseException;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import org.slf4j.Logger;

public class JSONValidator
{
    private static final Logger logger;
    private final String acceptedJSONSchema;
    private boolean enabled;
    
    private JSONValidator(final String acceptedJSONSchema, boolean enabled) {
        this.enabled = true;
        this.acceptedJSONSchema = acceptedJSONSchema;
        if (acceptedJSONSchema != null) {
            this.enabled = enabled;
        }
        else {
            JSONValidator.logger.warn("Null json validation schema. Disable JSONValidator");
            enabled = false;
        }
    }
    
    public JSONValidator(final DataCollectorConfigurationService cfgService, final String pluginName) {
        this(cfgService.getDCProperty("plugins/" + pluginName + "/parser/json/schema"), Boolean.valueOf(cfgService.getDCProperty("plugins/" + pluginName + "/parser/json/validator/enabled")));
        JSONValidator.logger.debug("Configured JSONValidator using schema: '{}' and enabling property: '{}'", "plugins/" + pluginName + "/parser/json/schema", "plugins/" + pluginName + "/parser/json/validator/enabled");
    }
    
    public void validate(final String json) throws InvalidJSONException, JSONValidationException {
        if (!this.enabled) {
            JSONValidator.logger.debug(JSONValidator.class.getSimpleName() + " is disabled. Skipping validation.");
            return;
        }
        if (this.acceptedJSONSchema == null || this.acceptedJSONSchema == "") {
            return;
        }
        if (json == null || json.isEmpty()) {
            JSONValidator.logger.warn("The received json is empty or null");
            throw new InvalidJSONException(json, this.acceptedJSONSchema);
        }
        try {
            if (!this.isValid(json)) {
                throw new InvalidJSONException(json, this.acceptedJSONSchema);
            }
        }
        catch (JsonParseException e) {
            throw new InvalidJSONException(json, this.acceptedJSONSchema);
        }
        catch (Exception e2) {
            throw new JSONValidationException();
        }
    }
    
    private boolean isValid(final String json) throws IOException, ProcessingException {
        final JsonNode schemaNode = JsonLoader.fromString(this.acceptedJSONSchema);
        final JsonNode data = JsonLoader.fromString(json);
        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        final JsonSchema schema = factory.getJsonSchema(schemaNode);
        final ProcessingReport report = schema.validate(data);
        return report.isSuccess();
    }
    
    static {
        logger = LoggerFactory.getLogger(JSONValidator.class);
    }
}
