/**
 *
 * @param <T>
 *
 * @author a
 */
public class ArrayDeque<T> {

    /**
     *
     */
    private T[] items;
    /**
     *
     */
    private int size;
    /**
     *
     */
    private int nextFirst;
    /**
     *
     */
    private int nextLast;
    /**
     *
     */
    private double ratio;

    /**
     *
     */
    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        ratio = 0.25;
    }

    /**
     *
     * @param other a
     */
    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[other.items.length];
        System.arraycopy(other.items, 0, items, 0, other.items.length);
        size = other.size;
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        ratio = 0.25;
    }

    /**
     *
     * @param item a
     */
    public void addFirst(T item) {
        if (isFull()) {
            this.resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /**
     *
     * @param item a
     */
    public void addLast(T item) {
        if (isFull()) {
            this.resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    /**
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     *
     */
    public void printDeque() {
        for (int index = plusOne(nextFirst); index != minusOne(nextLast); index = plusOne(index)) {
            System.out.print(items[index] + " ");
        }
        System.out.println();
    }

    /**
     *
     * @return
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int index = plusOne(nextFirst);
        T x = items[index];
        items[index] = null;
        nextFirst = plusOne(nextFirst);
        size -= 1;
        if (isWaste()) {
            resize(items.length / 2);
        }
        return x;
    }

    /**
     *
     * @return
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int index = minusOne(nextFirst);
        T x = items[index];
        items[index] = null;
        nextLast = minusOne(nextLast);
        size -= 1;
        if (isWaste()) {
            resize(items.length / 2);
        }
        return x;
    }

    /**
     *
     * @param index a
     * @return
     */
    public T get(int index) {
        return items[index];
    }

    /**
     *
     * @param capacity a
     */
    public void resize(int capacity) {
        T[] newitems = (T []) new Object[capacity];
        if (nextFirst >= nextLast) {
            int firstEnd = minusOne(nextLast);
            int secondStart = plusOne(nextFirst);
            System.arraycopy(items, 0, newitems, 0, firstEnd + 1);
            System.arraycopy(items, secondStart, newitems, firstEnd + 1, items.length - secondStart);
        } else {
            System.arraycopy(items, 0, newitems, 0, size);
        }
        nextFirst = capacity - 1;
        nextLast = size;
        items = newitems;
    }

    /**
     *
     * @param index a
     * @return
     */
    public int minusOne(int index) {
        return (index + items.length - 1) % items.length;
    }

    /**
     *
     * @param index a
     * @return
     */
    public int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     * @return
     */
    public boolean isFull() {
        return size == items.length;
    }

    /**
     *
     * @return
     */
    public boolean isWaste() {
        return items.length >= 16 && size / items.length < ratio;
    }

}
