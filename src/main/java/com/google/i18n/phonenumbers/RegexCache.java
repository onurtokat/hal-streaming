// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class RegexCache
{
    private LRUCache<String, Pattern> cache;
    
    public RegexCache(final int size) {
        this.cache = new LRUCache<String, Pattern>(size);
    }
    
    public Pattern getPatternForRegex(final String regex) {
        Pattern pattern = this.cache.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            this.cache.put(regex, pattern);
        }
        return pattern;
    }
    
    boolean containsRegex(final String regex) {
        return this.cache.containsKey(regex);
    }
    
    private static class LRUCache<K, V>
    {
        private LinkedHashMap<K, V> map;
        private int size;
        
        public LRUCache(final int size) {
            this.size = size;
            this.map = new LinkedHashMap<K, V>(size * 4 / 3 + 1, 0.75f, true) {
                protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
                    return this.size() > LRUCache.this.size;
                }
            };
        }
        
        public synchronized V get(final K key) {
            return this.map.get(key);
        }
        
        public synchronized void put(final K key, final V value) {
            this.map.put(key, value);
        }
        
        public synchronized boolean containsKey(final K key) {
            return this.map.containsKey(key);
        }
    }
}
