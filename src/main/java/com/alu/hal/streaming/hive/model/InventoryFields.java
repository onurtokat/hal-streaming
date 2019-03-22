// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import org.apache.log4j.Logger;
import com.alu.hal.streaming.config.DashBoardConfig;
import java.io.Serializable;

public class InventoryFields implements Serializable
{
    private static final long serialVersionUID = 1L;
    private MgdDeviceData invData;
    private ExtenderInventoryData extenderData;
    private static final DashBoardConfig CONFIG;
    private static Logger LOG;
    
    public InventoryFields(final MgdDeviceData invData, final ExtenderInventoryData extenderData) {
        this.invData = invData;
        this.extenderData = extenderData;
    }
    
    public MgdDeviceData getInvData() {
        return this.invData;
    }
    
    public ExtenderInventoryData getExtenderData() {
        return this.extenderData;
    }
    
    public void setInvData(final MgdDeviceData invData) {
        this.invData = invData;
    }
    
    public void setExtenderData(final ExtenderInventoryData extenderData) {
        this.extenderData = extenderData;
    }
    
    @Override
    public String toString() {
        return "InventoryFields [invData=" + this.invData + ", extenderData=" + this.extenderData + "]";
    }
    
    public static Boolean isExtender(final InventoryFields invData) {
        return Integer.valueOf(1).equals(invData.getExtenderData().getAssocDevice_isExtender());
    }
    
    public static InventoryFields parseFromLine(final String invline) {
        final String[] fields = invline.split(InventoryFields.CONFIG.getString(DashBoardConfig.INVENTORY_DATA_SEPARATOR, "\\^"), -1);
        final String deviceId = getFieldFromFile(fields, DashBoardConfig.INVENTORY_DATA_DEVICE_ID_INDEX, 3);
        final String accountId = getFieldFromFile(fields, DashBoardConfig.INVENTORY_DATA_ACCOUNT_ID_INDEX, 9);
        final String MACAdress = getFieldFromFile(fields, DashBoardConfig.INVENTORY_DATA_MAC_ADRESS_OF_DEVICE_INDEX, 31);
        final String autoChannelSupported = getFieldFromFile(fields, DashBoardConfig.INVENTORY_DATA_AUTO_CHANNEL_SUPPORTED_INDEX, 58);
        final String isExtender = getFieldFromFile(fields, DashBoardConfig.INVENTORY_DATA_IS_EXTENDER_INDEX, 62);
        final MgdDeviceData invData = new MgdDeviceData(deviceId, accountId, MACAdress, autoChannelSupported);
        final ExtenderInventoryData extData = new ExtenderInventoryData(MACAdress, isExtender);
        final InventoryFields inv = new InventoryFields(invData, extData);
        return inv;
    }
    
    private static String getFieldFromFile(final String[] fields, final String fieldName, final int defaultPosition) {
        String field = null;
        try {
            field = fields[InventoryFields.CONFIG.getInteger(fieldName, defaultPosition)];
        }
        catch (Exception e) {
            InventoryFields.LOG.warn("unable to get inventory field " + fieldName, e);
        }
        return field;
    }
    
    static {
        CONFIG = DashBoardConfig.instance();
        InventoryFields.LOG = Logger.getLogger(InventoryFields.class);
    }
}
