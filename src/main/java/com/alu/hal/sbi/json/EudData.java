// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.sbi.json;

import org.apache.avro.data.RecordBuilderBase;
import org.apache.avro.data.RecordBuilder;
import org.apache.avro.specific.SpecificRecordBuilderBase;
import org.apache.avro.AvroRuntimeException;
import java.util.Map;
import java.util.List;
import org.apache.avro.Schema;
import org.apache.avro.specific.AvroGenerated;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;

@AvroGenerated
public class EudData extends SpecificRecordBase implements SpecificRecord
{
    public static final Schema SCHEMA$;
    private long receptionTimestamp;
    private CharSequence homeNetworkId;
    private long timestamp_ms;
    private double linkSpeed_Mbps;
    private CharSequence connectedSsid;
    private CharSequence connectedBssid;
    private int connectedFreq_MHz;
    private SpontaneousConnection spontaneousConnectionInfo;
    private List<ManagedDeviceDetail> managedDeviceDetails;
    private ClientDetail clientDetails;
    private Map<CharSequence, CharSequence> customInfo;
    private List<ConnectivityInfo> connectivityInfos;
    private List<Network> networks;
    
    public static Schema getClassSchema() {
        return EudData.SCHEMA$;
    }
    
    public EudData() {
    }
    
    public EudData(final Long receptionTimestamp, final CharSequence homeNetworkId, final Long timestamp_ms, final Double linkSpeed_Mbps, final CharSequence connectedSsid, final CharSequence connectedBssid, final Integer connectedFreq_MHz, final SpontaneousConnection spontaneousConnectionInfo, final List<ManagedDeviceDetail> managedDeviceDetails, final ClientDetail clientDetails, final Map<CharSequence, CharSequence> customInfo, final List<ConnectivityInfo> connectivityInfos, final List<Network> networks) {
        this.receptionTimestamp = receptionTimestamp;
        this.homeNetworkId = homeNetworkId;
        this.timestamp_ms = timestamp_ms;
        this.linkSpeed_Mbps = linkSpeed_Mbps;
        this.connectedSsid = connectedSsid;
        this.connectedBssid = connectedBssid;
        this.connectedFreq_MHz = connectedFreq_MHz;
        this.spontaneousConnectionInfo = spontaneousConnectionInfo;
        this.managedDeviceDetails = managedDeviceDetails;
        this.clientDetails = clientDetails;
        this.customInfo = customInfo;
        this.connectivityInfos = connectivityInfos;
        this.networks = networks;
    }
    
    @Override
    public Schema getSchema() {
        return EudData.SCHEMA$;
    }
    
    @Override
    public Object get(final int field$) {
        switch (field$) {
            case 0: {
                return this.receptionTimestamp;
            }
            case 1: {
                return this.homeNetworkId;
            }
            case 2: {
                return this.timestamp_ms;
            }
            case 3: {
                return this.linkSpeed_Mbps;
            }
            case 4: {
                return this.connectedSsid;
            }
            case 5: {
                return this.connectedBssid;
            }
            case 6: {
                return this.connectedFreq_MHz;
            }
            case 7: {
                return this.spontaneousConnectionInfo;
            }
            case 8: {
                return this.managedDeviceDetails;
            }
            case 9: {
                return this.clientDetails;
            }
            case 10: {
                return this.customInfo;
            }
            case 11: {
                return this.connectivityInfos;
            }
            case 12: {
                return this.networks;
            }
            default: {
                throw new AvroRuntimeException("Bad index");
            }
        }
    }
    
    @Override
    public void put(final int field$, final Object value$) {
        switch (field$) {
            case 0: {
                this.receptionTimestamp = (long)value$;
                break;
            }
            case 1: {
                this.homeNetworkId = (CharSequence)value$;
                break;
            }
            case 2: {
                this.timestamp_ms = (long)value$;
                break;
            }
            case 3: {
                this.linkSpeed_Mbps = (double)value$;
                break;
            }
            case 4: {
                this.connectedSsid = (CharSequence)value$;
                break;
            }
            case 5: {
                this.connectedBssid = (CharSequence)value$;
                break;
            }
            case 6: {
                this.connectedFreq_MHz = (int)value$;
                break;
            }
            case 7: {
                this.spontaneousConnectionInfo = (SpontaneousConnection)value$;
                break;
            }
            case 8: {
                this.managedDeviceDetails = (List<ManagedDeviceDetail>)value$;
                break;
            }
            case 9: {
                this.clientDetails = (ClientDetail)value$;
                break;
            }
            case 10: {
                this.customInfo = (Map<CharSequence, CharSequence>)value$;
                break;
            }
            case 11: {
                this.connectivityInfos = (List<ConnectivityInfo>)value$;
                break;
            }
            case 12: {
                this.networks = (List<Network>)value$;
                break;
            }
            default: {
                throw new AvroRuntimeException("Bad index");
            }
        }
    }
    
    public Long getReceptionTimestamp() {
        return this.receptionTimestamp;
    }
    
    public void setReceptionTimestamp(final Long value) {
        this.receptionTimestamp = value;
    }
    
    public CharSequence getHomeNetworkId() {
        return this.homeNetworkId;
    }
    
    public void setHomeNetworkId(final CharSequence value) {
        this.homeNetworkId = value;
    }
    
    public Long getTimestampMs() {
        return this.timestamp_ms;
    }
    
    public void setTimestampMs(final Long value) {
        this.timestamp_ms = value;
    }
    
    public Double getLinkSpeedMbps() {
        return this.linkSpeed_Mbps;
    }
    
    public void setLinkSpeedMbps(final Double value) {
        this.linkSpeed_Mbps = value;
    }
    
    public CharSequence getConnectedSsid() {
        return this.connectedSsid;
    }
    
    public void setConnectedSsid(final CharSequence value) {
        this.connectedSsid = value;
    }
    
    public CharSequence getConnectedBssid() {
        return this.connectedBssid;
    }
    
    public void setConnectedBssid(final CharSequence value) {
        this.connectedBssid = value;
    }
    
    public Integer getConnectedFreqMHz() {
        return this.connectedFreq_MHz;
    }
    
    public void setConnectedFreqMHz(final Integer value) {
        this.connectedFreq_MHz = value;
    }
    
    public SpontaneousConnection getSpontaneousConnectionInfo() {
        return this.spontaneousConnectionInfo;
    }
    
    public void setSpontaneousConnectionInfo(final SpontaneousConnection value) {
        this.spontaneousConnectionInfo = value;
    }
    
    public List<ManagedDeviceDetail> getManagedDeviceDetails() {
        return this.managedDeviceDetails;
    }
    
    public void setManagedDeviceDetails(final List<ManagedDeviceDetail> value) {
        this.managedDeviceDetails = value;
    }
    
    public ClientDetail getClientDetails() {
        return this.clientDetails;
    }
    
    public void setClientDetails(final ClientDetail value) {
        this.clientDetails = value;
    }
    
    public Map<CharSequence, CharSequence> getCustomInfo() {
        return this.customInfo;
    }
    
    public void setCustomInfo(final Map<CharSequence, CharSequence> value) {
        this.customInfo = value;
    }
    
    public List<ConnectivityInfo> getConnectivityInfos() {
        return this.connectivityInfos;
    }
    
    public void setConnectivityInfos(final List<ConnectivityInfo> value) {
        this.connectivityInfos = value;
    }
    
    public List<Network> getNetworks() {
        return this.networks;
    }
    
    public void setNetworks(final List<Network> value) {
        this.networks = value;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Builder newBuilder(final Builder other) {
        return new Builder(other);
    }
    
    public static Builder newBuilder(final EudData other) {
        return new Builder(other);
    }
    
    static {
        SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"EudData\",\"namespace\":\"com.alu.hal.sbi.json\",\"fields\":[{\"name\":\"receptionTimestamp\",\"type\":\"long\"},{\"name\":\"homeNetworkId\",\"type\":\"string\"},{\"name\":\"timestamp_ms\",\"type\":\"long\"},{\"name\":\"linkSpeed_Mbps\",\"type\":\"double\"},{\"name\":\"connectedSsid\",\"type\":\"string\"},{\"name\":\"connectedBssid\",\"type\":\"string\"},{\"name\":\"connectedFreq_MHz\",\"type\":\"int\"},{\"name\":\"spontaneousConnectionInfo\",\"type\":{\"type\":\"record\",\"name\":\"SpontaneousConnection\",\"fields\":[{\"name\":\"spontaneousConnectedSsid\",\"type\":\"string\"},{\"name\":\"spontaneousConnectedBssid\",\"type\":\"string\"}]}},{\"name\":\"managedDeviceDetails\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ManagedDeviceDetail\",\"fields\":[{\"name\":\"isGateway\",\"type\":\"boolean\"},{\"name\":\"radios\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Radio\",\"fields\":[{\"name\":\"ssid\",\"type\":\"string\"},{\"name\":\"bssid\",\"type\":\"string\"},{\"name\":\"band_GHz\",\"type\":{\"type\":\"enum\",\"name\":\"Band\",\"symbols\":[\"BAND24\",\"BAND50\"]}}]}}}]}}},{\"name\":\"clientDetails\",\"type\":{\"type\":\"record\",\"name\":\"ClientDetail\",\"fields\":[{\"name\":\"mac\",\"type\":\"string\"},{\"name\":\"platform\",\"type\":\"string\"},{\"name\":\"osVersion\",\"type\":\"string\"},{\"name\":\"hostname\",\"type\":\"string\"}]}},{\"name\":\"customInfo\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"connectivityInfos\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ConnectivityInfo\",\"fields\":[{\"name\":\"ssid\",\"type\":\"string\"},{\"name\":\"bssid\",\"type\":\"string\"},{\"name\":\"connectivityTestsPassed\",\"type\":\"boolean\"}]}}},{\"name\":\"networks\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Network\",\"fields\":[{\"name\":\"ssid\",\"type\":\"string\"},{\"name\":\"bssid\",\"type\":\"string\"},{\"name\":\"rssi_dBm\",\"type\":{\"type\":\"array\",\"items\":\"double\"}},{\"name\":\"channel\",\"type\":\"int\"},{\"name\":\"freq_MHz\",\"type\":\"int\"}]}}}]}");
    }
    
    public static class Builder extends SpecificRecordBuilderBase<EudData> implements RecordBuilder<EudData>
    {
        private long receptionTimestamp;
        private CharSequence homeNetworkId;
        private long timestamp_ms;
        private double linkSpeed_Mbps;
        private CharSequence connectedSsid;
        private CharSequence connectedBssid;
        private int connectedFreq_MHz;
        private SpontaneousConnection spontaneousConnectionInfo;
        private List<ManagedDeviceDetail> managedDeviceDetails;
        private ClientDetail clientDetails;
        private Map<CharSequence, CharSequence> customInfo;
        private List<ConnectivityInfo> connectivityInfos;
        private List<Network> networks;
        
        private Builder() {
            super(EudData.SCHEMA$);
        }
        
        private Builder(final Builder other) {
            super(other);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.receptionTimestamp)) {
                this.receptionTimestamp = this.data().deepCopy(this.fields()[0].schema(), other.receptionTimestamp);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.homeNetworkId)) {
                this.homeNetworkId = this.data().deepCopy(this.fields()[1].schema(), other.homeNetworkId);
                this.fieldSetFlags()[1] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[2], other.timestamp_ms)) {
                this.timestamp_ms = this.data().deepCopy(this.fields()[2].schema(), other.timestamp_ms);
                this.fieldSetFlags()[2] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[3], other.linkSpeed_Mbps)) {
                this.linkSpeed_Mbps = this.data().deepCopy(this.fields()[3].schema(), other.linkSpeed_Mbps);
                this.fieldSetFlags()[3] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[4], other.connectedSsid)) {
                this.connectedSsid = this.data().deepCopy(this.fields()[4].schema(), other.connectedSsid);
                this.fieldSetFlags()[4] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[5], other.connectedBssid)) {
                this.connectedBssid = this.data().deepCopy(this.fields()[5].schema(), other.connectedBssid);
                this.fieldSetFlags()[5] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[6], other.connectedFreq_MHz)) {
                this.connectedFreq_MHz = this.data().deepCopy(this.fields()[6].schema(), other.connectedFreq_MHz);
                this.fieldSetFlags()[6] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[7], other.spontaneousConnectionInfo)) {
                this.spontaneousConnectionInfo = this.data().deepCopy(this.fields()[7].schema(), other.spontaneousConnectionInfo);
                this.fieldSetFlags()[7] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[8], other.managedDeviceDetails)) {
                this.managedDeviceDetails = this.data().deepCopy(this.fields()[8].schema(), other.managedDeviceDetails);
                this.fieldSetFlags()[8] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[9], other.clientDetails)) {
                this.clientDetails = this.data().deepCopy(this.fields()[9].schema(), other.clientDetails);
                this.fieldSetFlags()[9] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[10], other.customInfo)) {
                this.customInfo = this.data().deepCopy(this.fields()[10].schema(), other.customInfo);
                this.fieldSetFlags()[10] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[11], other.connectivityInfos)) {
                this.connectivityInfos = this.data().deepCopy(this.fields()[11].schema(), other.connectivityInfos);
                this.fieldSetFlags()[11] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[12], other.networks)) {
                this.networks = this.data().deepCopy(this.fields()[12].schema(), other.networks);
                this.fieldSetFlags()[12] = true;
            }
        }
        
        private Builder(final EudData other) {
            super(EudData.SCHEMA$);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.receptionTimestamp)) {
                this.receptionTimestamp = this.data().deepCopy(this.fields()[0].schema(), other.receptionTimestamp);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.homeNetworkId)) {
                this.homeNetworkId = this.data().deepCopy(this.fields()[1].schema(), other.homeNetworkId);
                this.fieldSetFlags()[1] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[2], other.timestamp_ms)) {
                this.timestamp_ms = this.data().deepCopy(this.fields()[2].schema(), other.timestamp_ms);
                this.fieldSetFlags()[2] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[3], other.linkSpeed_Mbps)) {
                this.linkSpeed_Mbps = this.data().deepCopy(this.fields()[3].schema(), other.linkSpeed_Mbps);
                this.fieldSetFlags()[3] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[4], other.connectedSsid)) {
                this.connectedSsid = this.data().deepCopy(this.fields()[4].schema(), other.connectedSsid);
                this.fieldSetFlags()[4] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[5], other.connectedBssid)) {
                this.connectedBssid = this.data().deepCopy(this.fields()[5].schema(), other.connectedBssid);
                this.fieldSetFlags()[5] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[6], other.connectedFreq_MHz)) {
                this.connectedFreq_MHz = this.data().deepCopy(this.fields()[6].schema(), other.connectedFreq_MHz);
                this.fieldSetFlags()[6] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[7], other.spontaneousConnectionInfo)) {
                this.spontaneousConnectionInfo = this.data().deepCopy(this.fields()[7].schema(), other.spontaneousConnectionInfo);
                this.fieldSetFlags()[7] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[8], other.managedDeviceDetails)) {
                this.managedDeviceDetails = this.data().deepCopy(this.fields()[8].schema(), other.managedDeviceDetails);
                this.fieldSetFlags()[8] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[9], other.clientDetails)) {
                this.clientDetails = this.data().deepCopy(this.fields()[9].schema(), other.clientDetails);
                this.fieldSetFlags()[9] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[10], other.customInfo)) {
                this.customInfo = this.data().deepCopy(this.fields()[10].schema(), other.customInfo);
                this.fieldSetFlags()[10] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[11], other.connectivityInfos)) {
                this.connectivityInfos = this.data().deepCopy(this.fields()[11].schema(), other.connectivityInfos);
                this.fieldSetFlags()[11] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[12], other.networks)) {
                this.networks = this.data().deepCopy(this.fields()[12].schema(), other.networks);
                this.fieldSetFlags()[12] = true;
            }
        }
        
        public Long getReceptionTimestamp() {
            return this.receptionTimestamp;
        }
        
        public Builder setReceptionTimestamp(final long value) {
            this.validate(this.fields()[0], value);
            this.receptionTimestamp = value;
            this.fieldSetFlags()[0] = true;
            return this;
        }
        
        public boolean hasReceptionTimestamp() {
            return this.fieldSetFlags()[0];
        }
        
        public Builder clearReceptionTimestamp() {
            this.fieldSetFlags()[0] = false;
            return this;
        }
        
        public CharSequence getHomeNetworkId() {
            return this.homeNetworkId;
        }
        
        public Builder setHomeNetworkId(final CharSequence value) {
            this.validate(this.fields()[1], value);
            this.homeNetworkId = value;
            this.fieldSetFlags()[1] = true;
            return this;
        }
        
        public boolean hasHomeNetworkId() {
            return this.fieldSetFlags()[1];
        }
        
        public Builder clearHomeNetworkId() {
            this.homeNetworkId = null;
            this.fieldSetFlags()[1] = false;
            return this;
        }
        
        public Long getTimestampMs() {
            return this.timestamp_ms;
        }
        
        public Builder setTimestampMs(final long value) {
            this.validate(this.fields()[2], value);
            this.timestamp_ms = value;
            this.fieldSetFlags()[2] = true;
            return this;
        }
        
        public boolean hasTimestampMs() {
            return this.fieldSetFlags()[2];
        }
        
        public Builder clearTimestampMs() {
            this.fieldSetFlags()[2] = false;
            return this;
        }
        
        public Double getLinkSpeedMbps() {
            return this.linkSpeed_Mbps;
        }
        
        public Builder setLinkSpeedMbps(final double value) {
            this.validate(this.fields()[3], value);
            this.linkSpeed_Mbps = value;
            this.fieldSetFlags()[3] = true;
            return this;
        }
        
        public boolean hasLinkSpeedMbps() {
            return this.fieldSetFlags()[3];
        }
        
        public Builder clearLinkSpeedMbps() {
            this.fieldSetFlags()[3] = false;
            return this;
        }
        
        public CharSequence getConnectedSsid() {
            return this.connectedSsid;
        }
        
        public Builder setConnectedSsid(final CharSequence value) {
            this.validate(this.fields()[4], value);
            this.connectedSsid = value;
            this.fieldSetFlags()[4] = true;
            return this;
        }
        
        public boolean hasConnectedSsid() {
            return this.fieldSetFlags()[4];
        }
        
        public Builder clearConnectedSsid() {
            this.connectedSsid = null;
            this.fieldSetFlags()[4] = false;
            return this;
        }
        
        public CharSequence getConnectedBssid() {
            return this.connectedBssid;
        }
        
        public Builder setConnectedBssid(final CharSequence value) {
            this.validate(this.fields()[5], value);
            this.connectedBssid = value;
            this.fieldSetFlags()[5] = true;
            return this;
        }
        
        public boolean hasConnectedBssid() {
            return this.fieldSetFlags()[5];
        }
        
        public Builder clearConnectedBssid() {
            this.connectedBssid = null;
            this.fieldSetFlags()[5] = false;
            return this;
        }
        
        public Integer getConnectedFreqMHz() {
            return this.connectedFreq_MHz;
        }
        
        public Builder setConnectedFreqMHz(final int value) {
            this.validate(this.fields()[6], value);
            this.connectedFreq_MHz = value;
            this.fieldSetFlags()[6] = true;
            return this;
        }
        
        public boolean hasConnectedFreqMHz() {
            return this.fieldSetFlags()[6];
        }
        
        public Builder clearConnectedFreqMHz() {
            this.fieldSetFlags()[6] = false;
            return this;
        }
        
        public SpontaneousConnection getSpontaneousConnectionInfo() {
            return this.spontaneousConnectionInfo;
        }
        
        public Builder setSpontaneousConnectionInfo(final SpontaneousConnection value) {
            this.validate(this.fields()[7], value);
            this.spontaneousConnectionInfo = value;
            this.fieldSetFlags()[7] = true;
            return this;
        }
        
        public boolean hasSpontaneousConnectionInfo() {
            return this.fieldSetFlags()[7];
        }
        
        public Builder clearSpontaneousConnectionInfo() {
            this.spontaneousConnectionInfo = null;
            this.fieldSetFlags()[7] = false;
            return this;
        }
        
        public List<ManagedDeviceDetail> getManagedDeviceDetails() {
            return this.managedDeviceDetails;
        }
        
        public Builder setManagedDeviceDetails(final List<ManagedDeviceDetail> value) {
            this.validate(this.fields()[8], value);
            this.managedDeviceDetails = value;
            this.fieldSetFlags()[8] = true;
            return this;
        }
        
        public boolean hasManagedDeviceDetails() {
            return this.fieldSetFlags()[8];
        }
        
        public Builder clearManagedDeviceDetails() {
            this.managedDeviceDetails = null;
            this.fieldSetFlags()[8] = false;
            return this;
        }
        
        public ClientDetail getClientDetails() {
            return this.clientDetails;
        }
        
        public Builder setClientDetails(final ClientDetail value) {
            this.validate(this.fields()[9], value);
            this.clientDetails = value;
            this.fieldSetFlags()[9] = true;
            return this;
        }
        
        public boolean hasClientDetails() {
            return this.fieldSetFlags()[9];
        }
        
        public Builder clearClientDetails() {
            this.clientDetails = null;
            this.fieldSetFlags()[9] = false;
            return this;
        }
        
        public Map<CharSequence, CharSequence> getCustomInfo() {
            return this.customInfo;
        }
        
        public Builder setCustomInfo(final Map<CharSequence, CharSequence> value) {
            this.validate(this.fields()[10], value);
            this.customInfo = value;
            this.fieldSetFlags()[10] = true;
            return this;
        }
        
        public boolean hasCustomInfo() {
            return this.fieldSetFlags()[10];
        }
        
        public Builder clearCustomInfo() {
            this.customInfo = null;
            this.fieldSetFlags()[10] = false;
            return this;
        }
        
        public List<ConnectivityInfo> getConnectivityInfos() {
            return this.connectivityInfos;
        }
        
        public Builder setConnectivityInfos(final List<ConnectivityInfo> value) {
            this.validate(this.fields()[11], value);
            this.connectivityInfos = value;
            this.fieldSetFlags()[11] = true;
            return this;
        }
        
        public boolean hasConnectivityInfos() {
            return this.fieldSetFlags()[11];
        }
        
        public Builder clearConnectivityInfos() {
            this.connectivityInfos = null;
            this.fieldSetFlags()[11] = false;
            return this;
        }
        
        public List<Network> getNetworks() {
            return this.networks;
        }
        
        public Builder setNetworks(final List<Network> value) {
            this.validate(this.fields()[12], value);
            this.networks = value;
            this.fieldSetFlags()[12] = true;
            return this;
        }
        
        public boolean hasNetworks() {
            return this.fieldSetFlags()[12];
        }
        
        public Builder clearNetworks() {
            this.networks = null;
            this.fieldSetFlags()[12] = false;
            return this;
        }
        
        @Override
        public EudData build() {
            try {
                final EudData record = new EudData();
                record.receptionTimestamp = (long)(this.fieldSetFlags()[0] ? this.receptionTimestamp : this.defaultValue(this.fields()[0]));
                record.homeNetworkId = (CharSequence)(this.fieldSetFlags()[1] ? this.homeNetworkId : this.defaultValue(this.fields()[1]));
                record.timestamp_ms = (long)(this.fieldSetFlags()[2] ? this.timestamp_ms : this.defaultValue(this.fields()[2]));
                record.linkSpeed_Mbps = (double)(this.fieldSetFlags()[3] ? this.linkSpeed_Mbps : this.defaultValue(this.fields()[3]));
                record.connectedSsid = (CharSequence)(this.fieldSetFlags()[4] ? this.connectedSsid : this.defaultValue(this.fields()[4]));
                record.connectedBssid = (CharSequence)(this.fieldSetFlags()[5] ? this.connectedBssid : this.defaultValue(this.fields()[5]));
                record.connectedFreq_MHz = (int)(this.fieldSetFlags()[6] ? this.connectedFreq_MHz : this.defaultValue(this.fields()[6]));
                record.spontaneousConnectionInfo = (SpontaneousConnection)(this.fieldSetFlags()[7] ? this.spontaneousConnectionInfo : this.defaultValue(this.fields()[7]));
                record.managedDeviceDetails = (List<ManagedDeviceDetail>)(this.fieldSetFlags()[8] ? this.managedDeviceDetails : this.defaultValue(this.fields()[8]));
                record.clientDetails = (ClientDetail)(this.fieldSetFlags()[9] ? this.clientDetails : this.defaultValue(this.fields()[9]));
                record.customInfo = (Map<CharSequence, CharSequence>)(this.fieldSetFlags()[10] ? this.customInfo : this.defaultValue(this.fields()[10]));
                record.connectivityInfos = (List<ConnectivityInfo>)(this.fieldSetFlags()[11] ? this.connectivityInfos : this.defaultValue(this.fields()[11]));
                record.networks = (List<Network>)(this.fieldSetFlags()[12] ? this.networks : this.defaultValue(this.fields()[12]));
                return record;
            }
            catch (Exception e) {
                throw new AvroRuntimeException(e);
            }
        }
    }
}
