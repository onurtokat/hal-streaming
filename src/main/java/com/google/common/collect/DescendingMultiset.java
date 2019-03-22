// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.SortedSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.NavigableSet;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
abstract class DescendingMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E>
{
    private transient Comparator<? super E> comparator;
    private transient NavigableSet<E> elementSet;
    private transient Set<Entry<E>> entrySet;
    
    abstract SortedMultiset<E> forwardMultiset();
    
    @Override
    public Comparator<? super E> comparator() {
        final Comparator<? super E> result = this.comparator;
        if (result == null) {
            return this.comparator = Ordering.from(this.forwardMultiset().comparator()).reverse();
        }
        return result;
    }
    
    @Override
    public NavigableSet<E> elementSet() {
        final NavigableSet<E> result = this.elementSet;
        if (result == null) {
            return this.elementSet = new SortedMultisets.NavigableElementSet<E>(this);
        }
        return result;
    }
    
    @Override
    public Entry<E> pollFirstEntry() {
        return this.forwardMultiset().pollLastEntry();
    }
    
    @Override
    public Entry<E> pollLastEntry() {
        return this.forwardMultiset().pollFirstEntry();
    }
    
    @Override
    public SortedMultiset<E> headMultiset(final E toElement, final BoundType boundType) {
        return this.forwardMultiset().tailMultiset(toElement, boundType).descendingMultiset();
    }
    
    @Override
    public SortedMultiset<E> subMultiset(final E fromElement, final BoundType fromBoundType, final E toElement, final BoundType toBoundType) {
        return this.forwardMultiset().subMultiset(toElement, toBoundType, fromElement, fromBoundType).descendingMultiset();
    }
    
    @Override
    public SortedMultiset<E> tailMultiset(final E fromElement, final BoundType boundType) {
        return this.forwardMultiset().headMultiset(fromElement, boundType).descendingMultiset();
    }
    
    @Override
    protected Multiset<E> delegate() {
        return this.forwardMultiset();
    }
    
    @Override
    public SortedMultiset<E> descendingMultiset() {
        return this.forwardMultiset();
    }
    
    @Override
    public Entry<E> firstEntry() {
        return this.forwardMultiset().lastEntry();
    }
    
    @Override
    public Entry<E> lastEntry() {
        return this.forwardMultiset().firstEntry();
    }
    
    abstract Iterator<Entry<E>> entryIterator();
    
    @Override
    public Set<Entry<E>> entrySet() {
        final Set<Entry<E>> result = this.entrySet;
        return (result == null) ? (this.entrySet = this.createEntrySet()) : result;
    }
    
    Set<Entry<E>> createEntrySet() {
        return (Set<Entry<E>>)new Multisets.EntrySet<E>() {
            @Override
            Multiset<E> multiset() {
                return (Multiset<E>)DescendingMultiset.this;
            }
            
            @Override
            public Iterator<Entry<E>> iterator() {
                return DescendingMultiset.this.entryIterator();
            }
            
            @Override
            public int size() {
                return DescendingMultiset.this.forwardMultiset().entrySet().size();
            }
        };
    }
    
    @Override
    public Iterator<E> iterator() {
        return Multisets.iteratorImpl((Multiset<E>)this);
    }
    
    @Override
    public Object[] toArray() {
        return this.standardToArray();
    }
    
    @Override
    public <T> T[] toArray(final T[] array) {
        return this.standardToArray(array);
    }
    
    @Override
    public String toString() {
        return this.entrySet().toString();
    }
}
