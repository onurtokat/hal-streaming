// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class LinkRef
{
    @SerializedName("href")
    private String href;
    
    public LinkRef() {
        this.href = null;
    }
    
    public LinkRef href(final String href) {
        this.href = href;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "")
    public String getHref() {
        return this.href;
    }
    
    public void setHref(final String href) {
        this.href = href;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final LinkRef linkRef = (LinkRef)o;
        return Objects.equals(this.href, linkRef.href);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.href);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class LinkRef {\n");
        sb.append("    href: ").append(this.toIndentedString(this.href)).append("\n");
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
