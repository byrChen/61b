import java.util.*;

public class MyTrieSet implements TrieSet61B{
    private Node root;

    private static class Node {
        private boolean isKey;
//        private HashMap<Character, Node> map;
        private TreeMap<Character, Node> map;
        Node(boolean b) {
            isKey = b;
//            map = new HashMap<>();
            map = new TreeMap<>();
        }

        Set<Character> keySet() {
            return map.keySet();
        }

        Node getNode(Character c) {
            return map.get(c);
        }

        boolean containsKey(Character c) {
            return map.containsKey(c);
        }

        void clear() {
            map.clear();
        }

        void add(Character c, Node n) {
            map.put(c, n);
        }
    }

    MyTrieSet() {
        root = new Node(false);
    }

    @Override
    public void clear() {
        root.clear();
    }

    @Override
    public boolean contains(String key) {
        if (key == null) return false;
        Node n = root;
        for (int i = 0, l = key.length(); i < l; i++) {
            char c = key.charAt(i);
            if (n.containsKey(c)) {
                n = n.getNode(c);
                continue;
            }
            return false;
        }
        return true;
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.containsKey(c)) {
                curr.add(c, new Node(false));
            }
            curr = curr.getNode(c);
        }
        curr.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> keys = new LinkedList<>();
        if (!contains(prefix)) return null;
        Node n = root;
        for (int i = 0, l = prefix.length(); i < l; i++) {
            char c = prefix.charAt(i);
            n = n.getNode(c);
        }
        colHelp(prefix, keys, n);
        return keys;
    }

    public List<String> collect() {
        List<String> s = new LinkedList<>();
        for (char c : root.keySet()) {
            colHelp(String.valueOf(c), s, root.getNode(c));
        }
        return s;
    }

    private void colHelp(String s, List<String> x, Node n) {
        if (n.isKey) x.add(s);
        for (char c : n.keySet()) {
            colHelp(s + c, x, n.getNode(c));
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
