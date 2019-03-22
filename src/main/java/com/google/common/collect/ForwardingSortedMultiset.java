// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.SortedSet;
import java.util.Collection;
import java.util.Set;
import java.util.Iterator;
import java.util.Comparator;
import java.util.NavigableSet;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;

@Beta
@GwtCompatible(emulated = true)
public abstract class ForwardingSortedMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E>
{
    @Override
    protected abstract SortedMultiset<E> delegate();
    
    @Override
    public NavigableSet<E> elementSet() {
        return (NavigableSet<E>)(NavigableSet)super.elementSet();
    }
    
    @Override
    public Comparator<? super E> comparator() {
        return this.delegate().comparator();
    }
    
    @Override
    public SortedMultiset<E> descendingMultiset() {
        return this.delegate().descendingMultiset();
    }
    
    @Override
    public Entry<E> firstEntry() {
        return this.delegate().firstEntry();
    }
    
    protected Entry<E> standardFirstEntry() {
        final Iterator<Entry<E>> entryIterator = this.entrySet().iterator();
        if (!entryIterator.hasNext()) {
            return null;
        }
        final Entry<E> entry = entryIterator.next();
        return Multisets.immutableEntry(entry.getElement(), entry.getCount());
    }
    
    @Override
    public Entry<E> lastEntry() {
        return this.delegate().lastEntry();
    }
    
    protected Entry<E> standardLastEntry() {
        final Iterator<Entry<E>> entryIterator = (Iterator<Entry<E>>)this.descendingMultiset().entrySet().iterator();
        if (!entryIterator.hasNext()) {
            return null;
        }
        final Entry<E> entry = entryIterator.next();
        return Multisets.immutableEntry(entry.getElement(), entry.getCount());
    }
    
    @Override
    public Entry<E> pollFirstEntry() {
        return this.delegate().pollFirstEntry();
    }
    
    protected Entry<E> standardPollFirstEntry() {
        final Iterator<Entry<E>> entryIterator = this.entrySet().iterator();
        if (!entryIterator.hasNext()) {
            return null;
        }
        Entry<E> entry = entryIterator.next();
        entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        entryIterator.remove();
        return entry;
    }
    
    @Override
    public Entry<E> pollLastEntry() {
        return this.delegate().pollLastEntry();
    }
    
    protected Entry<E> standardPollLastEntry() {
        final Iterator<Entry<E>> entryIterator = (Iterator<Entry<E>>)this.descendingMultiset().entrySet().iterator();
        if (!entryIterator.hasNext()) {
            return null;
        }
        Entry<E> entry = entryIterator.next();
        entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        entryIterator.remove();
        return entry;
    }
    
    @Override
    public SortedMultiset<E> headMultiset(final E upperBound, final BoundType boundType) {
        return this.delegate().headMultiset(upperBound, boundType);
    }
    
    @Override
    public SortedMultiset<E> subMultiset(final E lowerBound, final BoundType lowerBoundType, final E upperBound, final BoundType upperBoundType) {
        return this.delegate().subMultiset(lowerBound, lowerBoundType, upperBound, upperBoundType);
    }
    
    protected SortedMultiset<E> standardSubMultiset(final E lowerBound, final BoundType lowerBoundType, final E upperBound, final BoundType upperBoundType) {
        return this.tailMultiset(lowerBound, lowerBoundType).headMultiset(upperBound, upperBoundType);
    }
    
    @Override
    public SortedMultiset<E> tailMultiset(final E lowerBound, final BoundType boundType) {
        return this.delegate().tailMultiset(lowerBound, boundType);
    }
    
    protected class StandardElementSet extends SortedMultisets.NavigableElementSet<E>
    {
        public StandardElementSet() {
            super(ForwardingSortedMultiset.this);
        }
    }
    
    protected abstract class StandardDescendingMultiset extends DescendingMultiset<E>
    {
        @Override
        SortedMultiset<E> forwardMultiset() {
            return (SortedMultiset<E>)ForwardingSortedMultiset.this;
        }
    }
}
