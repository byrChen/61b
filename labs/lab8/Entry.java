/* public class Entry<K, V> {
    private K key;
    private V val;
    private Entry next;

    public Entry(K key, V val, Entry next) {
        this.key = key;
        this.val = val;
        this.next = next;
    }

    public V get(K key) {
        Entry<K, V> e = this;
        while (e != null) {
            if (e.key.equals(key)) return e.val;
            e = e.next;
        }
        return null;
    }

    public void update(K key, V val) {
        Entry<K, V> e = this;
        while (e != null) {
            if (e.key.equals(key)) {
                e.val = val;
                break;
            }
            e = e.next;
        }
    }

    public void add(K key, V val) {
        Entry<K, V> e = this;
        while (e != null) {
            if (e.next == null) {
                e.next = new Entry(key, val, null);
                break;
            }
            e = e.next;
        }
    }
} */

