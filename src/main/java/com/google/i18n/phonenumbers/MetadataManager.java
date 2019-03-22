// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.io.InputStream;
import java.util.Set;
import java.util.Map;
import java.util.logging.Logger;

class MetadataManager
{
    private static final String ALTERNATE_FORMATS_FILE_PREFIX = "/com/google/i18n/phonenumbers/data/PhoneNumberAlternateFormatsProto";
    private static final String SHORT_NUMBER_METADATA_FILE_PREFIX = "/com/google/i18n/phonenumbers/data/ShortNumberMetadataProto";
    private static final Logger LOGGER;
    private static final Map<Integer, Phonemetadata.PhoneMetadata> callingCodeToAlternateFormatsMap;
    private static final Map<String, Phonemetadata.PhoneMetadata> regionCodeToShortNumberMetadataMap;
    private static final Set<Integer> countryCodeSet;
    private static final Set<String> regionCodeSet;
    
    private static void close(final InputStream in) {
        if (in != null) {
            try {
                in.close();
            }
            catch (IOException e) {
                MetadataManager.LOGGER.log(Level.WARNING, e.toString());
            }
        }
    }
    
    private static void loadAlternateFormatsMetadataFromFile(final int countryCallingCode) {
        final Class<PhoneNumberMatcher> clazz = PhoneNumberMatcher.class;
        final String value = String.valueOf(String.valueOf("/com/google/i18n/phonenumbers/data/PhoneNumberAlternateFormatsProto_"));
        final InputStream source = clazz.getResourceAsStream(new StringBuilder(11 + value.length()).append(value).append(countryCallingCode).toString());
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(source);
            final Phonemetadata.PhoneMetadataCollection alternateFormats = new Phonemetadata.PhoneMetadataCollection();
            alternateFormats.readExternal(in);
            for (final Phonemetadata.PhoneMetadata metadata : alternateFormats.getMetadataList()) {
                MetadataManager.callingCodeToAlternateFormatsMap.put(metadata.getCountryCode(), metadata);
            }
        }
        catch (IOException e) {
            MetadataManager.LOGGER.log(Level.WARNING, e.toString());
        }
        finally {
            close(in);
        }
    }
    
    static Phonemetadata.PhoneMetadata getAlternateFormatsForCountry(final int countryCallingCode) {
        if (!MetadataManager.countryCodeSet.contains(countryCallingCode)) {
            return null;
        }
        synchronized (MetadataManager.callingCodeToAlternateFormatsMap) {
            if (!MetadataManager.callingCodeToAlternateFormatsMap.containsKey(countryCallingCode)) {
                loadAlternateFormatsMetadataFromFile(countryCallingCode);
            }
        }
        return MetadataManager.callingCodeToAlternateFormatsMap.get(countryCallingCode);
    }
    
    private static void loadShortNumberMetadataFromFile(final String regionCode) {
        final Class<PhoneNumberMatcher> clazz = PhoneNumberMatcher.class;
        final String value = String.valueOf("/com/google/i18n/phonenumbers/data/ShortNumberMetadataProto_");
        final String value2 = String.valueOf(regionCode);
        final InputStream source = clazz.getResourceAsStream((value2.length() != 0) ? value.concat(value2) : new String(value));
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(source);
            final Phonemetadata.PhoneMetadataCollection shortNumberMetadata = new Phonemetadata.PhoneMetadataCollection();
            shortNumberMetadata.readExternal(in);
            for (final Phonemetadata.PhoneMetadata metadata : shortNumberMetadata.getMetadataList()) {
                MetadataManager.regionCodeToShortNumberMetadataMap.put(regionCode, metadata);
            }
        }
        catch (IOException e) {
            MetadataManager.LOGGER.log(Level.WARNING, e.toString());
        }
        finally {
            close(in);
        }
    }
    
    static Set<String> getShortNumberMetadataSupportedRegions() {
        return MetadataManager.regionCodeSet;
    }
    
    static Phonemetadata.PhoneMetadata getShortNumberMetadataForRegion(final String regionCode) {
        if (!MetadataManager.regionCodeSet.contains(regionCode)) {
            return null;
        }
        synchronized (MetadataManager.regionCodeToShortNumberMetadataMap) {
            if (!MetadataManager.regionCodeToShortNumberMetadataMap.containsKey(regionCode)) {
                loadShortNumberMetadataFromFile(regionCode);
            }
        }
        return MetadataManager.regionCodeToShortNumberMetadataMap.get(regionCode);
    }
    
    static {
        LOGGER = Logger.getLogger(MetadataManager.class.getName());
        callingCodeToAlternateFormatsMap = Collections.synchronizedMap(new HashMap<Integer, Phonemetadata.PhoneMetadata>());
        regionCodeToShortNumberMetadataMap = Collections.synchronizedMap(new HashMap<String, Phonemetadata.PhoneMetadata>());
        countryCodeSet = AlternateFormatsCountryCodeSet.getCountryCodeSet();
        regionCodeSet = ShortNumbersRegionCodeSet.getRegionCodeSet();
    }
}
