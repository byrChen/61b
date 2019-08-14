import java.io.NotActiveException;

public class LinkedListDeque<T> {

    private Node<T> sentinel;
    private int size;

    public class Node<T> {
        public Node<T> prev;
        public Node<T> next;
        public T item;

        public Node(T ITEM, Node PREV, Node NEXT) {
            item = ITEM;
            prev = PREV;
            next = NEXT;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node(0, null, null);
        size = 0;
    }

    public  LinkedListDeque(LinkedListDeque other) {
        Node<T> p = other.sentinel;
        LinkedListDeque<T> copyLLD = new LinkedListDeque<>();
        while (p.next != null) {
            copyLLD.addLast(p.next.item);
            p = p.next;
        }
        sentinel = copyLLD.sentinel;
    }

    public T getRecursive(int index) {
        LinkedListDeque<T> copyLLD = new LinkedListDeque<>(this);
        Node<T> p = copyLLD.sentinel;
        if (p == null) {
            return null;
        }
        if (index == 0) {
            return p.next.item;
        }
        copyLLD.removeFirst();
        return copyLLD.getRecursive(index - 1);
    }

    public void addFirst(T item) {
        Node<T> node = new Node<>(item, null, null);
        if (isEmpty()) {
            size = size + 1;
            sentinel.next = node;
            node.prev = sentinel;
            sentinel.prev = node;
            node.next = sentinel;
            return;
        }
        size = size + 1;
        node.next = sentinel.next;
        sentinel.next.prev = node;
        node.prev = sentinel;
        sentinel.next = node;
    }

    public void addLast(T item) {
        Node<T> node = new Node<>(item, null, null);
        if (isEmpty()) {
            sentinel.next = node;
            node.prev = sentinel;
            sentinel.prev = node;
            node.next = sentinel;
            size = size + 1;
            return;
        }
        node.prev = sentinel.prev;
        sentinel.prev.next = node;
        node.next = sentinel;
        sentinel.prev = node;
        size = size + 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = sentinel;
        for (int i = 0; i < size; i++) {
            System.out.print(p.next.item + " ");
            p = p.next;
        }

        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<T> first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        size = size - 1;
        return first.item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node<T> last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        size = size - 1;
        return last.item;
    }

    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        Node<T> p = sentinel;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }
}
