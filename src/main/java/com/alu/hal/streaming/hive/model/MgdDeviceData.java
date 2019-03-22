// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.io.Serializable;

public class MgdDeviceData implements Serializable
{
    private String RefDeviceId;
    private String HomeNetworkId;
    private String MACAddress;
    private String Auto_Channel_Supported;
    
    public MgdDeviceData(final String refDeviceId, final String homeNetworkId, final String mACAddress, final String auto_Channel_Supported) {
        this.RefDeviceId = refDeviceId;
        this.HomeNetworkId = homeNetworkId;
        this.MACAddress = mACAddress;
        this.Auto_Channel_Supported = auto_Channel_Supported;
    }
    
    public String getRefDeviceId() {
        return this.RefDeviceId;
    }
    
    public String getHomeNetworkId() {
        return this.HomeNetworkId;
    }
    
    public String getMACAddress() {
        return this.MACAddress;
    }
    
    public String getAuto_Channel_Supported() {
        return this.Auto_Channel_Supported;
    }
    
    public void setRefDeviceId(final String refDeviceId) {
        this.RefDeviceId = refDeviceId;
    }
    
    public void setHomeNetworkId(final String homeNetworkId) {
        this.HomeNetworkId = homeNetworkId;
    }
    
    public void setMACAddress(final String mACAddress) {
        this.MACAddress = mACAddress;
    }
    
    public void setAuto_Channel_Supported(final String auto_Channel_Supported) {
        this.Auto_Channel_Supported = auto_Channel_Supported;
    }
    
    @Override
    public String toString() {
        return "InventoryData [RefDeviceId=" + this.RefDeviceId + ", HomeNetworkId=" + this.HomeNetworkId + ", MACAddress=" + this.MACAddress + ", Auto_Channel_Supported=" + this.Auto_Channel_Supported + "]";
    }
}
