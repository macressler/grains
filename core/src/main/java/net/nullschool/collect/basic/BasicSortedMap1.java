/*
 * Copyright 2013 Cameron Beccario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.nullschool.collect.basic;

import net.nullschool.collect.*;
import net.nullschool.reflect.PublicInterfaceRef;

import java.util.*;

import static net.nullschool.collect.basic.BasicTools.*;
import static net.nullschool.collect.basic.BasicCollections.*;

/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
@PublicInterfaceRef(BasicConstSortedMap.class)
final class BasicSortedMap1<K, V> extends BasicConstSortedMap<K, V> {

    private final K k0;
    private final V v0;

    @SuppressWarnings("unchecked")
    BasicSortedMap1(Comparator<? super K> comparator, Object k0, Object v0) {
        super(comparator);
        this.k0 = (K)k0;
        this.v0 = (V)v0;
    }

    @Override public int size() {
        return 1;
    }

    @Override public boolean isEmpty() {
        return false;
    }

    @Override public boolean containsKey(Object key) {
        @SuppressWarnings("unchecked") K k = (K)key;
        return compare(k, k0) == 0;
    }

    @Override public boolean containsValue(Object value) {
        return Objects.equals(value, v0);
    }

    @Override K getKey(int index) {
        if (index == 0) {
            return k0;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override V getValue(int index) {
        if (index == 0) {
            return v0;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override public V get(Object key) {
        @SuppressWarnings("unchecked") K k = (K)key;
        return compare(k, k0) == 0 ? v0 : null;
    }

    @Override public K firstKey() {
        return k0;
    }

    @Override public K lastKey() {
        return k0;
    }

    @Override public ConstSortedSet<K> keySet() {
        return sortedSetOf(comparator, k0);
    }

    @Override public ConstCollection<V> values() {
        return listOf(v0);
    }

    @Override public ConstSet<Entry<K, V>> entrySet() {
        MapIterator<K, V> iter = iterator();
        iter.next();
        return setOf(iter.entry());
    }

    @Override public ConstSortedMap<K, V> with(K key, V value) {
        int cmp = compare(key, k0);
        if (cmp == 0) {
            if (Objects.equals(value, v0)) {
                return this;
            }
            return new BasicSortedMap1<>(comparator, k0, value);
        }
        return cmp < 0 ?
            new BasicSortedMapN<K, V>(comparator, new Object[] {key, k0}, new Object[] {value, v0}) :
            new BasicSortedMapN<K, V>(comparator, new Object[] {k0, key}, new Object[] {v0, value});
    }

    @Override public ConstSortedMap<K, V> withAll(Map<? extends K, ? extends V> map) {
        if (map.isEmpty()) {
            return this;
        }
        MapColumns mc = copy(map);
        return condenseToSortedMap(
            comparator,
            unionInto(new Object[] {k0}, new Object[] {v0}, mc.keys, mc.values, comparator));
    }

    @Override public ConstSortedMap<K, V> without(Object key) {
        return !containsKey(key) ? this : BasicCollections.<K, V>emptySortedMap(comparator);
    }

    @Override public ConstSortedMap<K, V> withoutAll(Collection<?> keys) {
        // Just like AbstractCollection.removeAll, use the specified collection's "contains" method
        // to test for equality rather than this map's comparator.
        return !keys.contains(k0) ? this : BasicCollections.<K, V>emptySortedMap(comparator);
    }

    @Override public ConstSortedMap<K, V> headMap(K toKey) {
        return compare(k0, toKey) < 0 ? this : BasicCollections.<K, V>emptySortedMap(comparator);
    }

    @Override public ConstSortedMap<K, V> tailMap(K fromKey) {
        return compare(fromKey, k0) <= 0 ? this : BasicCollections.<K, V>emptySortedMap(comparator);
    }

    @Override public ConstSortedMap<K, V> subMap(K fromKey, K toKey) {
        if (compare(fromKey, toKey) > 0) {
            throw new IllegalArgumentException("fromKey cannot be greater than toKey");
        }
        int from = compare(fromKey, k0);
        int to = compare(k0, toKey);
        return from <= 0 && to < 0 ? this : BasicCollections.<K, V>emptySortedMap(comparator);
    }

    @Override public int hashCode() {
        return AbstractEntry.hashCode(k0, v0);
    }
}
