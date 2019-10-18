package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity should > 0");
        }
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

//    public T[] getrb() {
//        return rb;
//    }
//
//    public int getfirst() {
//        return first;
//    }
//
//    public int getlast() {
//        return last;
//    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        last = (last + 1) % rb.length;
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T x = rb[first];
        rb[first] = null;
        first = (first + 1) % rb.length;
        fillCount -= 1;
        return x;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ARBIterator();
    }

    private class ARBIterator implements Iterator<T> {
        private int index;
        private int remain;

        public ARBIterator() {
            index = first;
            remain = fillCount;
        }

        @Override
        public boolean hasNext() {
            return remain > 0;
        }

        @Override
        public T next() {
            T next = rb[index];
            index = (index + 1) % capacity();
            remain -= 1;
            return next;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayRingBuffer) {
            Iterator oiter = ((ArrayRingBuffer) o).iterator();
            for (T r : rb) {
                if (!r.equals(oiter.next())) return false;
            }
            return true;
        }
        return false;
    }

}
