// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.Collections;
import java.util.logging.Level;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ShortNumberInfo
{
    private static final Logger logger;
    private static final ShortNumberInfo INSTANCE;
    private static final Set<String> REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT;
    private final PhoneNumberUtil phoneUtil;
    
    public static ShortNumberInfo getInstance() {
        return ShortNumberInfo.INSTANCE;
    }
    
    ShortNumberInfo(final PhoneNumberUtil util) {
        this.phoneUtil = util;
    }
    
    public boolean isPossibleShortNumberForRegion(final String shortNumber, final String regionDialingFrom) {
        final Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion(regionDialingFrom);
        if (phoneMetadata == null) {
            return false;
        }
        final Phonemetadata.PhoneNumberDesc generalDesc = phoneMetadata.getGeneralDesc();
        return this.phoneUtil.isNumberPossibleForDesc(shortNumber, generalDesc);
    }
    
    public boolean isPossibleShortNumber(final Phonenumber.PhoneNumber number) {
        final List<String> regionCodes = this.phoneUtil.getRegionCodesForCountryCode(number.getCountryCode());
        final String shortNumber = this.phoneUtil.getNationalSignificantNumber(number);
        for (final String region : regionCodes) {
            final Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion(region);
            if (this.phoneUtil.isNumberPossibleForDesc(shortNumber, phoneMetadata.getGeneralDesc())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isValidShortNumberForRegion(final String shortNumber, final String regionDialingFrom) {
        final Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion(regionDialingFrom);
        if (phoneMetadata == null) {
            return false;
        }
        final Phonemetadata.PhoneNumberDesc generalDesc = phoneMetadata.getGeneralDesc();
        if (!generalDesc.hasNationalNumberPattern() || !this.phoneUtil.isNumberMatchingDesc(shortNumber, generalDesc)) {
            return false;
        }
        final Phonemetadata.PhoneNumberDesc shortNumberDesc = phoneMetadata.getShortCode();
        if (!shortNumberDesc.hasNationalNumberPattern()) {
            final Logger logger = ShortNumberInfo.logger;
            final Level warn\u0131ng = Level.WARNING;
            final String s = "No short code national number pattern found for region: ";
            final String value = String.valueOf(regionDialingFrom);
            logger.log(warn\u0131ng, (value.length() != 0) ? s.concat(value) : new String(s));
            return false;
        }
        return this.phoneUtil.isNumberMatchingDesc(shortNumber, shortNumberDesc);
    }
    
    public boolean isValidShortNumber(final Phonenumber.PhoneNumber number) {
        final List<String> regionCodes = this.phoneUtil.getRegionCodesForCountryCode(number.getCountryCode());
        final String shortNumber = this.phoneUtil.getNationalSignificantNumber(number);
        final String regionCode = this.getRegionCodeForShortNumberFromRegionList(number, regionCodes);
        return (regionCodes.size() > 1 && regionCode != null) || this.isValidShortNumberForRegion(shortNumber, regionCode);
    }
    
    public ShortNumberCost getExpectedCostForRegion(final String shortNumber, final String regionDialingFrom) {
        final Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion(regionDialingFrom);
        if (phoneMetadata == null) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        if (this.phoneUtil.isNumberMatchingDesc(shortNumber, phoneMetadata.getPremiumRate())) {
            return ShortNumberCost.PREMIUM_RATE;
        }
        if (this.phoneUtil.isNumberMatchingDesc(shortNumber, phoneMetadata.getStandardRate())) {
            return ShortNumberCost.STANDARD_RATE;
        }
        if (this.phoneUtil.isNumberMatchingDesc(shortNumber, phoneMetadata.getTollFree())) {
            return ShortNumberCost.TOLL_FREE;
        }
        if (this.isEmergencyNumber(shortNumber, regionDialingFrom)) {
            return ShortNumberCost.TOLL_FREE;
        }
        return ShortNumberCost.UNKNOWN_COST;
    }
    
    public ShortNumberCost getExpectedCost(final Phonenumber.PhoneNumber number) {
        final List<String> regionCodes = this.phoneUtil.getRegionCodesForCountryCode(number.getCountryCode());
        if (regionCodes.size() == 0) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        final String shortNumber = this.phoneUtil.getNationalSignificantNumber(number);
        if (regionCodes.size() == 1) {
            return this.getExpectedCostForRegion(shortNumber, regionCodes.get(0));
        }
        ShortNumberCost cost = ShortNumberCost.TOLL_FREE;
        for (final String regionCode : regionCodes) {
            final ShortNumberCost costForRegion = this.getExpectedCostForRegion(shortNumber, regionCode);
            switch (costForRegion) {
                case PREMIUM_RATE: {
                    return ShortNumberCost.PREMIUM_RATE;
                }
                case UNKNOWN_COST: {
                    cost = ShortNumberCost.UNKNOWN_COST;
                    continue;
                }
                case STANDARD_RATE: {
                    if (cost != ShortNumberCost.UNKNOWN_COST) {
                        cost = ShortNumberCost.STANDARD_RATE;
                        continue;
                    }
                    continue;
                }
                case TOLL_FREE: {
                    continue;
                }
                default: {
                    final Logger logger = ShortNumberInfo.logger;
                    final Level severe = Level.SEVERE;
                    final String value = String.valueOf(String.valueOf(costForRegion));
                    logger.log(severe, new StringBuilder(30 + value.length()).append("Unrecognised cost for region: ").append(value).toString());
                    continue;
                }
            }
        }
        return cost;
    }
    
    private String getRegionCodeForShortNumberFromRegionList(final Phonenumber.PhoneNumber number, final List<String> regionCodes) {
        if (regionCodes.size() == 0) {
            return null;
        }
        if (regionCodes.size() == 1) {
            return regionCodes.get(0);
        }
        final String nationalNumber = this.phoneUtil.getNationalSignificantNumber(number);
        for (final String regionCode : regionCodes) {
            final Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion(regionCode);
            if (phoneMetadata != null && this.phoneUtil.isNumberMatchingDesc(nationalNumber, phoneMetadata.getShortCode())) {
                return regionCode;
            }
        }
        return null;
    }
    
    Set<String> getSupportedRegions() {
        return Collections.unmodifiableSet((Set<? extends String>)MetadataManager.getShortNumberMetadataSupportedRegions());
    }
    
    String getExampleShortNumber(final String regionCode) {
        final Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion(regionCode);
        if (phoneMetadata == null) {
            return "";
        }
        final Phonemetadata.PhoneNumberDesc desc = phoneMetadata.getShortCode();
        if (desc.hasExampleNumber()) {
            return desc.getExampleNumber();
        }
        return "";
    }
    
    String getExampleShortNumberForCost(final String regionCode, final ShortNumberCost cost) {
        final Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion(regionCode);
        if (phoneMetadata == null) {
            return "";
        }
        Phonemetadata.PhoneNumberDesc desc = null;
        switch (cost) {
            case TOLL_FREE: {
                desc = phoneMetadata.getTollFree();
                break;
            }
            case STANDARD_RATE: {
                desc = phoneMetadata.getStandardRate();
                break;
            }
            case PREMIUM_RATE: {
                desc = phoneMetadata.getPremiumRate();
                break;
            }
        }
        if (desc != null && desc.hasExampleNumber()) {
            return desc.getExampleNumber();
        }
        return "";
    }
    
    public boolean connectsToEmergencyNumber(final String number, final String regionCode) {
        return this.matchesEmergencyNumberHelper(number, regionCode, true);
    }
    
    public boolean isEmergencyNumber(final String number, final String regionCode) {
        return this.matchesEmergencyNumberHelper(number, regionCode, false);
    }
    
    private boolean matchesEmergencyNumberHelper(String number, final String regionCode, final boolean allowPrefixMatch) {
        number = PhoneNumberUtil.extractPossibleNumber(number);
        if (PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(number).lookingAt()) {
            return false;
        }
        final Phonemetadata.PhoneMetadata metadata = MetadataManager.getShortNumberMetadataForRegion(regionCode);
        if (metadata == null || !metadata.hasEmergency()) {
            return false;
        }
        final Pattern emergencyNumberPattern = Pattern.compile(metadata.getEmergency().getNationalNumberPattern());
        final String normalizedNumber = PhoneNumberUtil.normalizeDigitsOnly(number);
        return (!allowPrefixMatch || ShortNumberInfo.REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.contains(regionCode)) ? emergencyNumberPattern.matcher(normalizedNumber).matches() : emergencyNumberPattern.matcher(normalizedNumber).lookingAt();
    }
    
    public boolean isCarrierSpecific(final Phonenumber.PhoneNumber number) {
        final List<String> regionCodes = this.phoneUtil.getRegionCodesForCountryCode(number.getCountryCode());
        final String regionCode = this.getRegionCodeForShortNumberFromRegionList(number, regionCodes);
        final String nationalNumber = this.phoneUtil.getNationalSignificantNumber(number);
        final Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion(regionCode);
        return phoneMetadata != null && this.phoneUtil.isNumberMatchingDesc(nationalNumber, phoneMetadata.getCarrierSpecific());
    }
    
    static {
        logger = Logger.getLogger(ShortNumberInfo.class.getName());
        INSTANCE = new ShortNumberInfo(PhoneNumberUtil.getInstance());
        (REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT = new HashSet<String>()).add("BR");
        ShortNumberInfo.REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.add("CL");
        ShortNumberInfo.REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.add("NI");
    }
    
    public enum ShortNumberCost
    {
        TOLL_FREE, 
        STANDARD_RATE, 
        PREMIUM_RATE, 
        UNKNOWN_COST;
    }
}
