// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Map;
import com.google.common.base.Objects;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class RegularImmutableTable<R, C, V> extends ImmutableTable<R, C, V>
{
    abstract Cell<R, C, V> getCell(final int p0);
    
    @Override
    final ImmutableSet<Cell<R, C, V>> createCellSet() {
        return this.isEmpty() ? ImmutableSet.of() : new CellSet();
    }
    
    abstract V getValue(final int p0);
    
    @Override
    final ImmutableCollection<V> createValues() {
        return (ImmutableCollection<V>)(this.isEmpty() ? ImmutableList.of() : new Values());
    }
    
    static <R, C, V> RegularImmutableTable<R, C, V> forCells(final List<Cell<R, C, V>> cells, @Nullable final Comparator<? super R> rowComparator, @Nullable final Comparator<? super C> columnComparator) {
        Preconditions.checkNotNull(cells);
        if (rowComparator != null || columnComparator != null) {
            final Comparator<Cell<R, C, V>> comparator = new Comparator<Cell<R, C, V>>() {
                @Override
                public int compare(final Cell<R, C, V> cell1, final Cell<R, C, V> cell2) {
                    final int rowCompare = (rowComparator == null) ? 0 : rowComparator.compare(cell1.getRowKey(), cell2.getRowKey());
                    if (rowCompare != 0) {
                        return rowCompare;
                    }
                    return (columnComparator == null) ? 0 : columnComparator.compare(cell1.getColumnKey(), cell2.getColumnKey());
                }
            };
            Collections.sort(cells, comparator);
        }
        return forCellsInternal(cells, rowComparator, columnComparator);
    }
    
    static <R, C, V> RegularImmutableTable<R, C, V> forCells(final Iterable<Cell<R, C, V>> cells) {
        return forCellsInternal(cells, null, null);
    }
    
    private static final <R, C, V> RegularImmutableTable<R, C, V> forCellsInternal(final Iterable<Cell<R, C, V>> cells, @Nullable final Comparator<? super R> rowComparator, @Nullable final Comparator<? super C> columnComparator) {
        final ImmutableSet.Builder<R> rowSpaceBuilder = ImmutableSet.builder();
        final ImmutableSet.Builder<C> columnSpaceBuilder = ImmutableSet.builder();
        final ImmutableList<Cell<R, C, V>> cellList = ImmutableList.copyOf((Iterable<? extends Cell<R, C, V>>)cells);
        for (final Cell<R, C, V> cell : cellList) {
            rowSpaceBuilder.add(cell.getRowKey());
            columnSpaceBuilder.add(cell.getColumnKey());
        }
        ImmutableSet<R> rowSpace = rowSpaceBuilder.build();
        if (rowComparator != null) {
            final List<R> rowList = (List<R>)Lists.newArrayList((Iterable<?>)rowSpace);
            Collections.sort(rowList, rowComparator);
            rowSpace = ImmutableSet.copyOf((Collection<? extends R>)rowList);
        }
        ImmutableSet<C> columnSpace = columnSpaceBuilder.build();
        if (columnComparator != null) {
            final List<C> columnList = (List<C>)Lists.newArrayList((Iterable<?>)columnSpace);
            Collections.sort(columnList, columnComparator);
            columnSpace = ImmutableSet.copyOf((Collection<? extends C>)columnList);
        }
        return (RegularImmutableTable<R, C, V>)((cellList.size() > rowSpace.size() * columnSpace.size() / 2L) ? new DenseImmutableTable<R, C, V>((ImmutableList<Cell<Object, Object, Object>>)cellList, (ImmutableSet<Object>)rowSpace, (ImmutableSet<Object>)columnSpace) : new SparseImmutableTable<R, C, V>((ImmutableList<Cell<Object, Object, Object>>)cellList, (ImmutableSet<Object>)rowSpace, (ImmutableSet<Object>)columnSpace));
    }
    
    private final class CellSet extends ImmutableSet<Cell<R, C, V>>
    {
        @Override
        public int size() {
            return RegularImmutableTable.this.size();
        }
        
        @Override
        public UnmodifiableIterator<Cell<R, C, V>> iterator() {
            return this.asList().iterator();
        }
        
        @Override
        ImmutableList<Cell<R, C, V>> createAsList() {
            return new ImmutableAsList<Cell<R, C, V>>() {
                @Override
                public Cell<R, C, V> get(final int index) {
                    return RegularImmutableTable.this.getCell(index);
                }
                
                @Override
                ImmutableCollection<Cell<R, C, V>> delegateCollection() {
                    return CellSet.this;
                }
            };
        }
        
        @Override
        public boolean contains(@Nullable final Object object) {
            if (object instanceof Table.Cell) {
                final Cell<?, ?, ?> cell = (Cell<?, ?, ?>)object;
                final Object value = RegularImmutableTable.this.get(cell.getRowKey(), cell.getColumnKey());
                return value != null && value.equals(cell.getValue());
            }
            return false;
        }
        
        @Override
        boolean isPartialView() {
            return false;
        }
    }
    
    private final class Values extends ImmutableList<V>
    {
        @Override
        public int size() {
            return RegularImmutableTable.this.size();
        }
        
        @Override
        public V get(final int index) {
            return RegularImmutableTable.this.getValue(index);
        }
        
        @Override
        boolean isPartialView() {
            return true;
        }
    }
}
