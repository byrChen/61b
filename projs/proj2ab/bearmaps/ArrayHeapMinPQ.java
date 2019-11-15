package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private class PNode<T> {
        T item;
        double priority;

        PNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }

    private ArrayList<PNode<T>> minHeap;
    private int size;
    private HashMap<T, Integer> items;

    public ArrayHeapMinPQ() {
        minHeap = new ArrayList<>();
        minHeap.add(null);
        items = new HashMap<>();
        size = 0;
    }


    @Override
    public void add(T item, double priority) {
        if (item == null || contains(item)) {
            throw new IllegalArgumentException("Items can't be null or duplicated.");
        }
        minHeap.add(new PNode(item, priority));
        size += 1;
        items.put(item, size);
        swim(size);
    }

    private void swim(int index) {
        if (index == 1) return;
        if (lessThanParent(index)) {
            swap(index, index / 2);
            swim(index / 2);
        }
    }

    private boolean lessThanParent(int child) {
        if (child == 1) return false;
        return minHeap.get(child).priority < minHeap.get(child/2).priority;
    }

    private void swap(int x, int y) {
        items.put(minHeap.get(x).item, y);
        items.put(minHeap.get(y).item, x);
        PNode<T> temp = minHeap.get(x);
        minHeap.set(x, minHeap.get(y));
        minHeap.set(y, temp);
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size == 0) return null;
        return minHeap.get(1).item;
    }

    @Override
    public T removeSmallest() {
        if (size == 0) throw new IllegalArgumentException("Queue is empty.");
        T min = minHeap.get(1).item;
        minHeap.set(1, minHeap.get(size));
        items.put(minHeap.get(1).item, 1);
        sink(1);
        minHeap.remove(size);
        size -= 1;
        items.remove(min);
        return min;
    }

    private void sink(int index) {
        if (2 * index > size) return;
        int child = smallerChild(index);
        if (lessThanParent(child)) {
            swap(index, child);
            sink(child);
        }
    }

    private int smallerChild(int parent) {
        int left = 2 * parent;
        int right = 2 * parent + 1;
        if (left == size) return left;
        return minHeap.get(left).priority <= minHeap.get(right).priority ? left : right;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) return;
        int index = items.get(item);
        minHeap.set(index, new PNode<T>(item, priority));
        if (lessThanParent(index)) swim(index);
        else sink(index);
    }
}
