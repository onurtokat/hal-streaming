// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Set;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import java.util.NavigableMap;
import java.util.AbstractMap;

abstract class AbstractNavigableMap<K, V> extends AbstractMap<K, V> implements NavigableMap<K, V>
{
    @Nullable
    @Override
    public abstract V get(@Nullable final Object p0);
    
    @Nullable
    @Override
    public Entry<K, V> firstEntry() {
        return Iterators.getNext(this.entryIterator(), (Entry<K, V>)null);
    }
    
    @Nullable
    @Override
    public Entry<K, V> lastEntry() {
        return Iterators.getNext(this.descendingEntryIterator(), (Entry<K, V>)null);
    }
    
    @Nullable
    @Override
    public Entry<K, V> pollFirstEntry() {
        return Iterators.pollNext(this.entryIterator());
    }
    
    @Nullable
    @Override
    public Entry<K, V> pollLastEntry() {
        return Iterators.pollNext(this.descendingEntryIterator());
    }
    
    @Override
    public K firstKey() {
        final Entry<K, V> entry = this.firstEntry();
        if (entry == null) {
            throw new NoSuchElementException();
        }
        return entry.getKey();
    }
    
    @Override
    public K lastKey() {
        final Entry<K, V> entry = this.lastEntry();
        if (entry == null) {
            throw new NoSuchElementException();
        }
        return entry.getKey();
    }
    
    @Nullable
    @Override
    public Entry<K, V> lowerEntry(final K key) {
        return this.headMap(key, false).lastEntry();
    }
    
    @Nullable
    @Override
    public Entry<K, V> floorEntry(final K key) {
        return this.headMap(key, true).lastEntry();
    }
    
    @Nullable
    @Override
    public Entry<K, V> ceilingEntry(final K key) {
        return this.tailMap(key, true).firstEntry();
    }
    
    @Nullable
    @Override
    public Entry<K, V> higherEntry(final K key) {
        return this.tailMap(key, false).firstEntry();
    }
    
    @Override
    public K lowerKey(final K key) {
        return Maps.keyOrNull((Entry<K, ?>)this.lowerEntry((K)key));
    }
    
    @Override
    public K floorKey(final K key) {
        return Maps.keyOrNull((Entry<K, ?>)this.floorEntry((K)key));
    }
    
    @Override
    public K ceilingKey(final K key) {
        return Maps.keyOrNull((Entry<K, ?>)this.ceilingEntry((K)key));
    }
    
    @Override
    public K higherKey(final K key) {
        return Maps.keyOrNull((Entry<K, ?>)this.higherEntry((K)key));
    }
    
    abstract Iterator<Entry<K, V>> entryIterator();
    
    abstract Iterator<Entry<K, V>> descendingEntryIterator();
    
    @Override
    public SortedMap<K, V> subMap(final K fromKey, final K toKey) {
        return (SortedMap<K, V>)this.subMap(fromKey, true, toKey, false);
    }
    
    @Override
    public SortedMap<K, V> headMap(final K toKey) {
        return (SortedMap<K, V>)this.headMap(toKey, false);
    }
    
    @Override
    public SortedMap<K, V> tailMap(final K fromKey) {
        return (SortedMap<K, V>)this.tailMap(fromKey, true);
    }
    
    @Override
    public NavigableSet<K> navigableKeySet() {
        return new Maps.NavigableKeySet<K, Object>(this);
    }
    
    @Override
    public Set<K> keySet() {
        return this.navigableKeySet();
    }
    
    @Override
    public abstract int size();
    
    @Override
    public Set<Entry<K, V>> entrySet() {
        return (Set<Entry<K, V>>)new Maps.EntrySet<K, V>() {
            @Override
            Map<K, V> map() {
                return (Map<K, V>)AbstractNavigableMap.this;
            }
            
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return AbstractNavigableMap.this.entryIterator();
            }
        };
    }
    
    @Override
    public NavigableSet<K> descendingKeySet() {
        return this.descendingMap().navigableKeySet();
    }
    
    @Override
    public NavigableMap<K, V> descendingMap() {
        return new DescendingMap();
    }
    
    private final class DescendingMap extends Maps.DescendingMap<K, V>
    {
        @Override
        NavigableMap<K, V> forward() {
            return (NavigableMap<K, V>)AbstractNavigableMap.this;
        }
        
        @Override
        Iterator<Entry<K, V>> entryIterator() {
            return AbstractNavigableMap.this.descendingEntryIterator();
        }
    }
}
