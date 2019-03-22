// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.util;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Random;

public class ObjectMap<K, V>
{
    private static final int PRIME1 = -1105259343;
    private static final int PRIME2 = -1262997959;
    private static final int PRIME3 = -825114047;
    static Random random;
    public int size;
    K[] keyTable;
    V[] valueTable;
    int capacity;
    int stashSize;
    private float loadFactor;
    private int hashShift;
    private int mask;
    private int threshold;
    private int stashCapacity;
    private int pushIterations;
    private Entries entries;
    private Values values;
    private Keys keys;
    
    public ObjectMap() {
        this(32, 0.8f);
    }
    
    public ObjectMap(final int initialCapacity) {
        this(initialCapacity, 0.8f);
    }
    
    public ObjectMap(final int initialCapacity, final float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity must be >= 0: " + initialCapacity);
        }
        if (this.capacity > 1073741824) {
            throw new IllegalArgumentException("initialCapacity is too large: " + initialCapacity);
        }
        this.capacity = nextPowerOfTwo(initialCapacity);
        if (loadFactor <= 0.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.threshold = (int)(this.capacity * loadFactor);
        this.mask = this.capacity - 1;
        this.hashShift = 31 - Integer.numberOfTrailingZeros(this.capacity);
        this.stashCapacity = Math.max(3, (int)Math.ceil(Math.log(this.capacity)) * 2);
        this.pushIterations = Math.max(Math.min(this.capacity, 8), (int)Math.sqrt(this.capacity) / 8);
        this.keyTable = new Object[this.capacity + this.stashCapacity];
        this.valueTable = new Object[this.keyTable.length];
    }
    
    public V put(final K key, final V value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }
        return this.put_internal(key, value);
    }
    
    private V put_internal(final K key, final V value) {
        final K[] keyTable = (K[])this.keyTable;
        final int hashCode = key.hashCode();
        final int index1 = hashCode & this.mask;
        final K key2 = keyTable[index1];
        if (key.equals(key2)) {
            final V oldValue = (V)this.valueTable[index1];
            this.valueTable[index1] = value;
            return oldValue;
        }
        final int index2 = this.hash2(hashCode);
        final K key3 = keyTable[index2];
        if (key.equals(key3)) {
            final V oldValue2 = (V)this.valueTable[index2];
            this.valueTable[index2] = value;
            return oldValue2;
        }
        final int index3 = this.hash3(hashCode);
        final K key4 = keyTable[index3];
        if (key.equals(key4)) {
            final V oldValue3 = (V)this.valueTable[index3];
            this.valueTable[index3] = value;
            return oldValue3;
        }
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (key.equals(keyTable[i])) {
                final V oldValue4 = (V)this.valueTable[i];
                this.valueTable[i] = value;
                return oldValue4;
            }
        }
        if (key2 == null) {
            keyTable[index1] = key;
            this.valueTable[index1] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return null;
        }
        if (key3 == null) {
            keyTable[index2] = key;
            this.valueTable[index2] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return null;
        }
        if (key4 == null) {
            keyTable[index3] = key;
            this.valueTable[index3] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return null;
        }
        this.push(key, value, index1, key2, index2, key3, index3, key4);
        return null;
    }
    
    public void putAll(final ObjectMap<K, V> map) {
        for (final Entry<K, V> entry : map.entries()) {
            this.put(entry.key, entry.value);
        }
    }
    
    private void putResize(final K key, final V value) {
        final int hashCode = key.hashCode();
        final int index1 = hashCode & this.mask;
        final K key2 = (K)this.keyTable[index1];
        if (key2 == null) {
            this.keyTable[index1] = key;
            this.valueTable[index1] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        final int index2 = this.hash2(hashCode);
        final K key3 = (K)this.keyTable[index2];
        if (key3 == null) {
            this.keyTable[index2] = key;
            this.valueTable[index2] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        final int index3 = this.hash3(hashCode);
        final K key4 = (K)this.keyTable[index3];
        if (key4 == null) {
            this.keyTable[index3] = key;
            this.valueTable[index3] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        this.push(key, value, index1, key2, index2, key3, index3, key4);
    }
    
    private void push(K insertKey, V insertValue, int index1, K key1, int index2, K key2, int index3, K key3) {
        final K[] keyTable = (K[])this.keyTable;
        final V[] valueTable = (V[])this.valueTable;
        final int mask = this.mask;
        int i = 0;
        final int pushIterations = this.pushIterations;
        while (true) {
            K evictedKey = null;
            V evictedValue = null;
            switch (ObjectMap.random.nextInt(3)) {
                case 0: {
                    evictedKey = key1;
                    evictedValue = valueTable[index1];
                    keyTable[index1] = insertKey;
                    valueTable[index1] = insertValue;
                    break;
                }
                case 1: {
                    evictedKey = key2;
                    evictedValue = valueTable[index2];
                    keyTable[index2] = insertKey;
                    valueTable[index2] = insertValue;
                    break;
                }
                default: {
                    evictedKey = key3;
                    evictedValue = valueTable[index3];
                    keyTable[index3] = insertKey;
                    valueTable[index3] = insertValue;
                    break;
                }
            }
            final int hashCode = evictedKey.hashCode();
            index1 = (hashCode & mask);
            key1 = keyTable[index1];
            if (key1 == null) {
                keyTable[index1] = evictedKey;
                valueTable[index1] = evictedValue;
                if (this.size++ >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
                return;
            }
            index2 = this.hash2(hashCode);
            key2 = keyTable[index2];
            if (key2 == null) {
                keyTable[index2] = evictedKey;
                valueTable[index2] = evictedValue;
                if (this.size++ >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
                return;
            }
            index3 = this.hash3(hashCode);
            key3 = keyTable[index3];
            if (key3 == null) {
                keyTable[index3] = evictedKey;
                valueTable[index3] = evictedValue;
                if (this.size++ >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
                return;
            }
            if (++i == pushIterations) {
                this.putStash(evictedKey, evictedValue);
                return;
            }
            insertKey = evictedKey;
            insertValue = evictedValue;
        }
    }
    
    private void putStash(final K key, final V value) {
        if (this.stashSize == this.stashCapacity) {
            this.resize(this.capacity << 1);
            this.put_internal(key, value);
            return;
        }
        final int index = this.capacity + this.stashSize;
        this.keyTable[index] = key;
        this.valueTable[index] = value;
        ++this.stashSize;
        ++this.size;
    }
    
    public V get(final K key) {
        final int hashCode = key.hashCode();
        int index = hashCode & this.mask;
        if (!key.equals(this.keyTable[index])) {
            index = this.hash2(hashCode);
            if (!key.equals(this.keyTable[index])) {
                index = this.hash3(hashCode);
                if (!key.equals(this.keyTable[index])) {
                    return this.getStash(key);
                }
            }
        }
        return (V)this.valueTable[index];
    }
    
    private V getStash(final K key) {
        final K[] keyTable = (K[])this.keyTable;
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (key.equals(keyTable[i])) {
                return (V)this.valueTable[i];
            }
        }
        return null;
    }
    
    public V remove(final K key) {
        final int hashCode = key.hashCode();
        int index = hashCode & this.mask;
        if (key.equals(this.keyTable[index])) {
            this.keyTable[index] = null;
            final V oldValue = (V)this.valueTable[index];
            this.valueTable[index] = null;
            --this.size;
            return oldValue;
        }
        index = this.hash2(hashCode);
        if (key.equals(this.keyTable[index])) {
            this.keyTable[index] = null;
            final V oldValue = (V)this.valueTable[index];
            this.valueTable[index] = null;
            --this.size;
            return oldValue;
        }
        index = this.hash3(hashCode);
        if (key.equals(this.keyTable[index])) {
            this.keyTable[index] = null;
            final V oldValue = (V)this.valueTable[index];
            this.valueTable[index] = null;
            --this.size;
            return oldValue;
        }
        return this.removeStash(key);
    }
    
    V removeStash(final K key) {
        final K[] keyTable = (K[])this.keyTable;
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (key.equals(keyTable[i])) {
                final V oldValue = (V)this.valueTable[i];
                this.removeStashIndex(i);
                --this.size;
                return oldValue;
            }
        }
        return null;
    }
    
    void removeStashIndex(final int index) {
        --this.stashSize;
        final int lastIndex = this.capacity + this.stashSize;
        if (index < lastIndex) {
            this.keyTable[index] = this.keyTable[lastIndex];
            this.valueTable[index] = this.valueTable[lastIndex];
            this.valueTable[lastIndex] = null;
        }
        else {
            this.valueTable[index] = null;
        }
    }
    
    public void clear() {
        final K[] keyTable = (K[])this.keyTable;
        final V[] valueTable = (V[])this.valueTable;
        int i = this.capacity + this.stashSize;
        while (i-- > 0) {
            keyTable[i] = null;
            valueTable[i] = null;
        }
        this.size = 0;
        this.stashSize = 0;
    }
    
    public boolean containsValue(final Object value, final boolean identity) {
        final V[] valueTable = (V[])this.valueTable;
        if (value == null) {
            final K[] keyTable = (K[])this.keyTable;
            int i = this.capacity + this.stashSize;
            while (i-- > 0) {
                if (keyTable[i] != null && valueTable[i] == null) {
                    return true;
                }
            }
        }
        else if (identity) {
            int j = this.capacity + this.stashSize;
            while (j-- > 0) {
                if (valueTable[j] == value) {
                    return true;
                }
            }
        }
        else {
            int j = this.capacity + this.stashSize;
            while (j-- > 0) {
                if (value.equals(valueTable[j])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean containsKey(final K key) {
        final int hashCode = key.hashCode();
        int index = hashCode & this.mask;
        if (!key.equals(this.keyTable[index])) {
            index = this.hash2(hashCode);
            if (!key.equals(this.keyTable[index])) {
                index = this.hash3(hashCode);
                if (!key.equals(this.keyTable[index])) {
                    return this.containsKeyStash(key);
                }
            }
        }
        return true;
    }
    
    private boolean containsKeyStash(final K key) {
        final K[] keyTable = (K[])this.keyTable;
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (key.equals(keyTable[i])) {
                return true;
            }
        }
        return false;
    }
    
    public K findKey(final Object value, final boolean identity) {
        final V[] valueTable = (V[])this.valueTable;
        if (value == null) {
            final K[] keyTable = (K[])this.keyTable;
            int i = this.capacity + this.stashSize;
            while (i-- > 0) {
                if (keyTable[i] != null && valueTable[i] == null) {
                    return keyTable[i];
                }
            }
        }
        else if (identity) {
            int j = this.capacity + this.stashSize;
            while (j-- > 0) {
                if (valueTable[j] == value) {
                    return (K)this.keyTable[j];
                }
            }
        }
        else {
            int j = this.capacity + this.stashSize;
            while (j-- > 0) {
                if (value.equals(valueTable[j])) {
                    return (K)this.keyTable[j];
                }
            }
        }
        return null;
    }
    
    public void ensureCapacity(final int additionalCapacity) {
        final int sizeNeeded = this.size + additionalCapacity;
        if (sizeNeeded >= this.threshold) {
            this.resize(nextPowerOfTwo((int)(sizeNeeded / this.loadFactor)));
        }
    }
    
    private void resize(final int newSize) {
        final int oldEndIndex = this.capacity + this.stashSize;
        this.capacity = newSize;
        this.threshold = (int)(newSize * this.loadFactor);
        this.mask = newSize - 1;
        this.hashShift = 31 - Integer.numberOfTrailingZeros(newSize);
        this.stashCapacity = Math.max(3, (int)Math.ceil(Math.log(newSize)) * 2);
        this.pushIterations = Math.max(Math.min(newSize, 8), (int)Math.sqrt(newSize) / 8);
        final K[] oldKeyTable = (K[])this.keyTable;
        final V[] oldValueTable = (V[])this.valueTable;
        this.keyTable = new Object[newSize + this.stashCapacity];
        this.valueTable = new Object[newSize + this.stashCapacity];
        this.size = 0;
        this.stashSize = 0;
        for (int i = 0; i < oldEndIndex; ++i) {
            final K key = oldKeyTable[i];
            if (key != null) {
                this.putResize(key, oldValueTable[i]);
            }
        }
    }
    
    private int hash2(long h) {
        h *= -1262997959L;
        return (int)((h ^ h >>> this.hashShift) & this.mask);
    }
    
    private int hash3(long h) {
        h *= -825114047L;
        return (int)((h ^ h >>> this.hashShift) & this.mask);
    }
    
    public String toString() {
        if (this.size == 0) {
            return "{}";
        }
        final StringBuilder buffer = new StringBuilder(32);
        buffer.append('{');
        final K[] keyTable = (K[])this.keyTable;
        final V[] valueTable = (V[])this.valueTable;
        int i = keyTable.length;
        while (i-- > 0) {
            final K key = keyTable[i];
            if (key == null) {
                continue;
            }
            buffer.append(key);
            buffer.append('=');
            buffer.append(valueTable[i]);
            break;
        }
        while (i-- > 0) {
            final K key = keyTable[i];
            if (key == null) {
                continue;
            }
            buffer.append(", ");
            buffer.append(key);
            buffer.append('=');
            buffer.append(valueTable[i]);
        }
        buffer.append('}');
        return buffer.toString();
    }
    
    public Entries<K, V> entries() {
        if (this.entries == null) {
            this.entries = new Entries((ObjectMap<K, V>)this);
        }
        else {
            this.entries.reset();
        }
        return (Entries<K, V>)this.entries;
    }
    
    public Values<V> values() {
        if (this.values == null) {
            this.values = new Values((ObjectMap<?, V>)this);
        }
        else {
            this.values.reset();
        }
        return (Values<V>)this.values;
    }
    
    public Keys<K> keys() {
        if (this.keys == null) {
            this.keys = new Keys((ObjectMap<K, ?>)this);
        }
        else {
            this.keys.reset();
        }
        return (Keys<K>)this.keys;
    }
    
    public static int nextPowerOfTwo(int value) {
        if (value == 0) {
            return 1;
        }
        value = (--value | value >> 1);
        value |= value >> 2;
        value |= value >> 4;
        value |= value >> 8;
        value |= value >> 16;
        return value + 1;
    }
    
    static {
        ObjectMap.random = new Random();
    }
    
    public static class Entry<K, V>
    {
        public K key;
        public V value;
        
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
    
    private static class MapIterator<K, V>
    {
        public boolean hasNext;
        protected final ObjectMap<K, V> map;
        int currentIndex;
        protected int nextIndex;
        
        public MapIterator(final ObjectMap<K, V> map) {
            this.map = map;
            this.reset();
        }
        
        public void reset() {
            this.currentIndex = -1;
            this.nextIndex = -1;
            this.advance();
        }
        
        protected void advance() {
            this.hasNext = false;
            final K[] keyTable = (K[])this.map.keyTable;
            final int n = this.map.capacity + this.map.stashSize;
            while (++this.nextIndex < n) {
                if (keyTable[this.nextIndex] != null) {
                    this.hasNext = true;
                    break;
                }
            }
        }
        
        public void remove() {
            if (this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }
            if (this.currentIndex >= this.map.capacity) {
                this.map.removeStashIndex(this.currentIndex);
            }
            else {
                this.map.keyTable[this.currentIndex] = null;
                this.map.valueTable[this.currentIndex] = null;
            }
            this.currentIndex = -1;
            final ObjectMap<K, V> map = this.map;
            --map.size;
        }
    }
    
    public static class Entries<K, V> extends MapIterator<K, V> implements Iterable<Entry<K, V>>, Iterator<Entry<K, V>>
    {
        protected Entry<K, V> entry;
        
        public Entries(final ObjectMap<K, V> map) {
            super(map);
            this.entry = new Entry<K, V>();
        }
        
        public Entry<K, V> next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            final K[] keyTable = (K[])this.map.keyTable;
            this.entry.key = keyTable[this.nextIndex];
            this.entry.value = (V)this.map.valueTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            this.advance();
            return this.entry;
        }
        
        public boolean hasNext() {
            return this.hasNext;
        }
        
        public Iterator<Entry<K, V>> iterator() {
            return this;
        }
    }
    
    public static class Values<V> extends MapIterator<Object, V> implements Iterable<V>, Iterator<V>
    {
        public Values(final ObjectMap<?, V> map) {
            super(map);
        }
        
        public boolean hasNext() {
            return this.hasNext;
        }
        
        public V next() {
            final V value = (V)this.map.valueTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            this.advance();
            return value;
        }
        
        public Iterator<V> iterator() {
            return this;
        }
        
        public ArrayList<V> toArray() {
            final ArrayList array = new ArrayList(this.map.size);
            while (this.hasNext) {
                array.add(this.next());
            }
            return (ArrayList<V>)array;
        }
        
        public void toArray(final ArrayList<V> array) {
            while (this.hasNext) {
                array.add(this.next());
            }
        }
    }
    
    public static class Keys<K> extends MapIterator<K, Object> implements Iterable<K>, Iterator<K>
    {
        public Keys(final ObjectMap<K, ?> map) {
            super(map);
        }
        
        public boolean hasNext() {
            return this.hasNext;
        }
        
        public K next() {
            final K key = (K)this.map.keyTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            this.advance();
            return key;
        }
        
        public Iterator<K> iterator() {
            return this;
        }
        
        public ArrayList<K> toArray() {
            final ArrayList array = new ArrayList(this.map.size);
            while (this.hasNext) {
                array.add(this.next());
            }
            return (ArrayList<K>)array;
        }
    }
}
