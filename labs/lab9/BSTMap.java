import java.security.Key;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public BSTMap() {}

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(root, key);
//        return getNode(root, key).val;
    }

    public V get(Node n, K key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if      (cmp < 0) return get(n.left, key);
        else if (cmp > 0) return get(n.right, key);
        else              return n.val;
    }

    @Override
    public int size() {
        return size(root);
    }

    public int size(Node n) {
        if (n == null) return 0;
        return n.size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, value);
    }

    public Node put(Node n, K key, V val) {
        if (n == null) return new Node(key, val, 1);
        int cmp = key.compareTo(n.key);
        if      (cmp < 0) n.left  = put(n.left,  key, val);
        else if (cmp > 0) n.right = put(n.right, key, val);
        else              n.val   = val;
        n.size = updateSize(n);
        return n;
    }

    @Override
    public Set<K> keySet() {
        return keySet(root);
    }

    public Set<K> keySet(Node n) {
        if (n == null) return Collections.emptySet();
        Set<K> s = new HashSet<>();
        s.addAll(keySet(n.left));
        s.add(n.key);
        s.addAll(keySet(n.right));
        return s;
    }

    public void printInOrder() {
        for (K k : keySet(root)) {
            System.out.println(k);
        }
    }

    @Override
    public V remove(K key) {
        return remove(key, root);
    }

    public V remove(K key, Node n) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        if (containsKey(key)) {
            V result = get(key);
            root = updateBST(key, n);
            updateSize(n);
            return result;
        }
        return null;
    }

    public Node updateBST(K key, Node n) {
        int cmp = key.compareTo(n.key);
        if (cmp < 0)        n.left = updateBST(key, n.left);
        else if (cmp > 0)   n.right = updateBST(key, n.right);
        else {
            if      (size(n) == 1)      n = null;
            else if (n.left == null)    n = n.right;
            else if (n.right == null)   n = n.left;
            else {
                Node max = maxNode(n.left);
                n.left = updateBST(max.key, n.left);
                n.key = max.key;
                n.val = max.val;
            }
        }
        return n;
    }

    private int updateSize(Node n) {
        if (n == null) return 0;
        n.size = 1 + updateSize(n.left) + updateSize(n.right);
        return n.size;
    }

    private Node maxNode(Node n) {
//        if (n == null || n.right == null) return n;
        if (n.right == null) return n;
        return maxNode(n.right);
    }

    public V removeV(K key, Node n) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        if (containsKey(key)) {
            if (n.size == -1) return remove(key, n.right);
            int cmp = key.compareTo(n.key);
            if      (cmp > 0) return remove(key, n.right);
            else if (cmp < 0) return remove(key, n.left);
            else {
                V result = n.val;

                if (n == root) {
                    Node tempP = new Node(n.key, n.val, -1);
                    tempP.right = root;
                    root = tempP;
                    V tempV = remove(key, root);
                    root = root.right;
                } else {

                    Node p = getParent(n, root);

                    if (size(n) == 1) {
                        if (p.left == n) p.left = null;
                        else             p.right = null;
                    } else if (n.left == null) {
                        if (p.left == n) p.left = n.right;
                        else             p.right = n.right;
                    } else if (n.right == null) {
                        if (p.left == n) p.left = n.left;
                        else             p.right = n.left;
                    } else if (n.left.right == null) {
                        n.left.right = n.right;
                        if (p.left == n) p.left = n.left;
                        else             p.right = n.left;
                    } else if (n.right.left == null) {
                        n.right.left = n.left;
                        if (p.left == n) p.left = n.right;
                        else             p.right = n.right;
                    } else {
                        Node max = maxNode(n.left);
                        Node maxP = getParent(max, root);
                        maxP.right = max.left;
                        max.left = maxP;
                        max.right = n.right;
                        if (p.left == n) p.left = max;
                        else             p.right = max;
                    }
                }

                updateSize(root);
                return result;
            }
        }
        return null;
    }

    private Node getParent(Node son, Node parent) {
        if (son == parent.left || son == parent.right) return parent;
        K compKey = parent.key;
        Node max = maxNode(parent.left);
        if (max != null) {
            compKey = max.key;
        }
        int cmp = son.key.compareTo(compKey);
        if (cmp > 0) return getParent(son, parent.right);
        else return getParent(son, parent.left);
    }

    @Override
    public V remove(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        if (get(root, key) == value) return remove(key);
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet(root).iterator();
    }

//    private class BSTMapIter implements Iterator<K> {
//
//        @Override
//        public boolean hasNext() {
//            return false;
//        }
//
//        @Override
//        public K next() {
//            return null;
//        }
//    }
}
