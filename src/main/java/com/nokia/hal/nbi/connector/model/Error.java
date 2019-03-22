// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class Error
{
    @SerializedName("timestamp_ms")
    private Long timestampMs;
    @SerializedName("status")
    private Integer status;
    @SerializedName("error")
    private String error;
    @SerializedName("exception")
    private String exception;
    @SerializedName("message")
    private String message;
    
    public Error() {
        this.timestampMs = null;
        this.status = null;
        this.error = null;
        this.exception = null;
        this.message = null;
    }
    
    public Error timestampMs(final Long timestampMs) {
        this.timestampMs = timestampMs;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "")
    public Long getTimestampMs() {
        return this.timestampMs;
    }
    
    public void setTimestampMs(final Long timestampMs) {
        this.timestampMs = timestampMs;
    }
    
    public Error status(final Integer status) {
        this.status = status;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "")
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(final Integer status) {
        this.status = status;
    }
    
    public Error error(final String error) {
        this.error = error;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "")
    public String getError() {
        return this.error;
    }
    
    public void setError(final String error) {
        this.error = error;
    }
    
    public Error exception(final String exception) {
        this.exception = exception;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public String getException() {
        return this.exception;
    }
    
    public void setException(final String exception) {
        this.exception = exception;
    }
    
    public Error message(final String message) {
        this.message = message;
        return this;
    }
    
    @ApiModelProperty(example = "null", value = "")
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Error error = (Error)o;
        return Objects.equals(this.timestampMs, error.timestampMs) && Objects.equals(this.status, error.status) && Objects.equals(this.error, error.error) && Objects.equals(this.exception, error.exception) && Objects.equals(this.message, error.message);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.timestampMs, this.status, this.error, this.exception, this.message);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class Error {\n");
        sb.append("    timestampMs: ").append(this.toIndentedString(this.timestampMs)).append("\n");
        sb.append("    status: ").append(this.toIndentedString(this.status)).append("\n");
        sb.append("    error: ").append(this.toIndentedString(this.error)).append("\n");
        sb.append("    exception: ").append(this.toIndentedString(this.exception)).append("\n");
        sb.append("    message: ").append(this.toIndentedString(this.message)).append("\n");
        sb.append("}");
        return sb.toString();
    }
    
    private String toIndentedString(final Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
