// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

import java.util.Set;

@Deprecated
public class ShortNumberUtil
{
    public Set<String> getSupportedRegions() {
        return ShortNumberInfo.getInstance().getSupportedRegions();
    }
    
    public boolean connectsToEmergencyNumber(final String number, final String regionCode) {
        return ShortNumberInfo.getInstance().connectsToEmergencyNumber(number, regionCode);
    }
    
    public boolean isEmergencyNumber(final String number, final String regionCode) {
        return ShortNumberInfo.getInstance().isEmergencyNumber(number, regionCode);
    }
    
    public enum ShortNumberCost
    {
        TOLL_FREE, 
        STANDARD_RATE, 
        PREMIUM_RATE, 
        UNKNOWN_COST;
    }
}
