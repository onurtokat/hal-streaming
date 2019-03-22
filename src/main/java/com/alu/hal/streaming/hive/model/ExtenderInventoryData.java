// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import scala.Tuple2;
import java.io.Serializable;

public class ExtenderInventoryData implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String MACAddressOfDevice;
    private String AssocDevice_isExtender;
    
    public ExtenderInventoryData(final String mACAddressOfDevice, final String assocDevice_isExtender) {
        this.MACAddressOfDevice = mACAddressOfDevice;
        this.AssocDevice_isExtender = assocDevice_isExtender;
    }
    
    public void setMACAddressOfDevice(final String mACAddressOfDevice) {
        this.MACAddressOfDevice = mACAddressOfDevice;
    }
    
    public void setAssocDevice_isExtender(final String assocDevice_isExtender) {
        this.AssocDevice_isExtender = assocDevice_isExtender;
    }
    
    public String getMACAddressOfDevice() {
        return this.MACAddressOfDevice;
    }
    
    public String getAssocDevice_isExtender() {
        return this.AssocDevice_isExtender;
    }
    
    public static Boolean isExtender(final Tuple2<String, ExtenderInventoryData> extenderTuple) {
        return extenderTuple._2().getAssocDevice_isExtender().equals(1);
    }
    
    @Override
    public String toString() {
        return "ExtenderInventoryData [MACAddressOfDevice=" + this.MACAddressOfDevice + ", AssocDevice_isExtender=" + this.AssocDevice_isExtender + "]";
    }
}
