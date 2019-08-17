import java.util.TreeMap;

public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private double ratio;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 7;
        nextLast = 0;
        ratio = 0.25;
    }

    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[other.items.length];
        System.arraycopy(other.items, 0, items, 0, other.items.length);
        size = other.size;
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        ratio = 0.25;
    }

    @Override
    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;

        if (isFull()) {
            this.resize(size * 2);
        }
    }

    @Override
    public void addLast(T item) {
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;

        if (isFull()) {
            this.resize(size * 2);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int index = plusOne(nextFirst);
        T x = items[index];
        items[index] = null;
        nextFirst = index;
        size -= 1;
        if (isWaste()) {
            resize(items.length / 2);
        }
        return x;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int index = minusOne(nextLast);
        T x = items[index];
        items[index] = null;
        nextLast = index;
        size -= 1;
        if (isWaste()) {
            resize(items.length / 2);
        }
        return x;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return  null;
        }
        return items[plusOne(index + nextFirst)];
    }

    private void resize(int capacity) {

        T[] newItems = (T []) new Object[capacity];
        int firstSize = items.length - nextFirst - 1;
        int start = plusOne(nextFirst);
        int secondSize = nextLast;

        if (isFull()) {
            if (nextFirst == nextLast - 1) {
                System.arraycopy(items, start, newItems, 0, firstSize);
                System.arraycopy(items, 0, newItems, firstSize, secondSize);
            } else {
                System.arraycopy(items, start, newItems, 0, size);
            }
        }

        if (isWaste()) {
            if (nextLast > nextFirst) {
                System.arraycopy(items, start, newItems, 0, size);
            } else {
                System.arraycopy(items, start, newItems, 0, firstSize);
                System.arraycopy(items, 0, newItems, firstSize, secondSize);
            }
        }

        nextFirst = capacity - 1;
        nextLast = size;
        items = newItems;
    }

    public Object[] items() {
        return items;
    }

    public int nextFirst() {
        return nextFirst;
    }

    public int nextLast() {
        return nextLast;
    }

    public int itemsLength() {
        return items.length;
    }

    private int minusOne(int index) {
        return (index + items.length - 1) % items.length;
    }

    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    private boolean isFull() {
        return size == items.length;
    }

    private boolean isWaste() {
        return items.length >= 16 && (float) size / items.length < ratio;
    }
}
