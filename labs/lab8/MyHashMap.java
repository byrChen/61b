import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int initialSize;
    private double loadFactor;
    private Entry<K, V>[] buckets;
    private int size;
    private Set<K> keys;

    private static class Entry<K, V> {
        K key;
        V val;
        Entry<K, V> next;

        Entry(K key, V val, Entry<K, V> next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }

        V get(K key) {
            Entry<K, V> e = this;
            while (e != null) {
                if (e.key.equals(key)) return e.val;
                e = e.next;
            }
            return null;
        }

        void update(K key, V val) {
            Entry<K, V> e = this;
            while (e != null) {
                if (e.key.equals(key)) {
                    e.val = val;
                    break;
                }
                e = e.next;
            }
        }

        void add(K key, V val) {
            Entry<K, V> e = this;
            while (true) {
                if (e.next == null) {
                    e.next = new Entry<>(key, val, null);
                    break;
                }
                e = e.next;
            }
        }

        V remove(K key) {
            V r = this.get(key);
            Entry<K, V> e = this;
            while (true) {
                if (e.next.key.equals(key)) {
                    e.next = e.next.next;
                    break;
                }
                e = e.next;
            }
            return r;
        }
    }

    public MyHashMap() {
        this(16, 0.75);
    }
    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        buckets = new Entry[initialSize];
        size = 0;
        keys = new HashSet<>();
    }

    @Override
    public void clear() {
        keys.removeAll(keys);
        size = 0;
        Arrays.fill(buckets, null);
    }

    @Override
    public boolean containsKey(K key) {
//        return keys.contains(key);
        return keySet().contains(key);
    }

    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException();
        int index = (key.hashCode() & 0x7FFFFFFF) % initialSize;
        if (buckets[index] == null) return null;
        return buckets[index].get(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException();
        if ((float) size / initialSize > loadFactor) resize();
        int index = (key.hashCode() & 0x7FFFFFFF) % initialSize;
        if (containsKey(key)) {
            buckets[index].update(key, value);
        } else {
            if (buckets[index] == null) {
                buckets[index] = new Entry<K, V>(key, value, null);
            } else {
                buckets[index].add(key, value);
            }
            size += 1;
            keys.add(key);
        }
    }

    private void resize() {
        MyHashMap<K, V> m = new MyHashMap<>(2 * initialSize, loadFactor);
        for (int i = 0; i < buckets.length; i++) {
            Entry<K, V> e = buckets[i];
            while (e != null) {
                m.put(e.key, e.val);
                e = e.next;
            }
        }
        this.buckets = m.buckets;
        initialSize = 2 * initialSize;
    }

    public Entry<K, V>[] getBuckets() {
        return buckets;
    }

    @Override
    public Set<K> keySet() {
//        return keys;
        Set<K> k = new HashSet<>();
        for (int i = 0; i < buckets.length; i++) {
            Entry<K, V> e = buckets[i];
            while (e != null) {
                k.add(e.key);
                e = e.next;
            }
        }
        return k;
    }

    @Override
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException();
        int index = (key.hashCode() & 0x7FFFFFFF) % initialSize;
        if (containsKey(key)) {
            V r;
            if (buckets[index].key.equals(key)) {
                r = buckets[index].val;
                buckets[index] = buckets[index].next;
            } else {
                r = buckets[index].remove(key);
            }
            size -= 1;
            keys.remove(key);
            return r;
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) return remove(key);
        return null;
    }

    @Override
    public Iterator<K> iterator() {
//        return keys;
        return keySet().iterator();
    }
}
