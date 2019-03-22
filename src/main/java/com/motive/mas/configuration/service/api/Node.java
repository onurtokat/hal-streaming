// 
// Decompiled by Procyon v0.5.30
// 

package com.motive.mas.configuration.service.api;

public class Node
{
    private final String path;
    private final byte[] data;
    
    public Node(final String path, final byte[] data) {
        this.path = path;
        this.data = data;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public byte[] getData() {
        return this.data;
    }
}
