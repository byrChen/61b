public class ArrayDeque<T> {

    private T[] Items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private double ratio;


    public ArrayDeque() {
        size = 0;
        Items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        ratio = 0.25;
    }

    public ArrayDeque(ArrayDeque other) {
        Items = (T[]) new Object[other.Items.length];
        System.arraycopy(other.Items, 0, Items, 0, other.Items.length);
        size = other.size;
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        ratio = 0.25;
    }


    public void addFirst(T item) {
        if (isFull()) {
            this.resize(size * 2);
        }
        Items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(T item) {
        if (isFull()) {
            this.resize(size * 2);
        }
        Items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int index = plusOne(nextFirst); index != minusOne(nextLast); index = plusOne(index)) {
            System.out.print(Items[index] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int index = plusOne(nextFirst);
        T x = Items[index];
        Items[index] = null;
        nextFirst = plusOne(nextFirst);
        size -= 1;
        if (isWaste()) {
            resize(Items.length / 2);
        }
        return x;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int index = minusOne(nextFirst);
        T x = Items[index];
        Items[index] = null;
        nextLast = minusOne(nextLast);
        size -= 1;
        if (isWaste()) {
            resize(Items.length / 2);
        }
        return x;
    }

    public T get(int index) {
        return Items[index];
    }

    public void resize(int capacity) {
        T[] newItems = (T []) new Object[capacity];
        if (nextFirst >= nextLast) {
            int firstEnd = minusOne(nextLast);
            int secondStart = plusOne(nextFirst);
            System.arraycopy(Items, 0, newItems, 0, firstEnd + 1);
            System.arraycopy(Items, secondStart, newItems, firstEnd + 1, Items.length - secondStart);
        } else {
            System.arraycopy(Items, 0, newItems, 0, size);
        }
        nextFirst = capacity - 1;
        nextLast = size;
        Items = newItems;
    }

    public int minusOne(int Index) {
        return (Index + Items.length - 1) % Items.length;
    }

    public int plusOne(int Index) {
        return (Index + 1) % Items.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == Items.length;
    }

    public boolean isWaste() {
        return Items.length >= 16 && size / Items.length < ratio;
    }

}
