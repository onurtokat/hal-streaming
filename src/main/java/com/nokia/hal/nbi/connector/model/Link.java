// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.model;

import java.util.Objects;
import io.swagger.annotations.ApiModelProperty;
import com.google.gson.annotations.SerializedName;

public class Link
{
    @SerializedName("rel")
    private String rel;
    @SerializedName("href")
    private String href;
    
    public Link() {
        this.rel = null;
        this.href = null;
    }
    
    public Link rel(final String rel) {
        this.rel = rel;
        return this;
    }
    
    @ApiModelProperty(example = "null", required = true, value = "")
    public String getRel() {
        return this.rel;
    }
    
    public void setRel(final String rel) {
        this.rel = rel;
    }
    
    public Link href(final String href) {
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
        final Link link = (Link)o;
        return Objects.equals(this.rel, link.rel) && Objects.equals(this.href, link.href);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.rel, this.href);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class Link {\n");
        sb.append("    rel: ").append(this.toIndentedString(this.rel)).append("\n");
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
